package com.enstrapp.fieldtekpro.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class is_reserv_header {

    @SerializedName("MOVEMENT_TYPE")
    @Expose
    private String mOVEMENTTYPE;
    @SerializedName("PLANT")
    @Expose
    private String pLANT;
    @SerializedName("COST_CENTER")
    @Expose
    private String cOSTCENTER;
    @SerializedName("ORDER_NO")
    @Expose
    private String oRDERNO;
    @SerializedName("FIELDS")
    @Expose
    private String fIELDS;
    @SerializedName("UDID")
    @Expose
    private String uDID;

    public String getMOVEMENTTYPE() {
        return mOVEMENTTYPE;
    }

    public void setMOVEMENTTYPE(String mOVEMENTTYPE) {
        this.mOVEMENTTYPE = mOVEMENTTYPE;
    }

    public String getPLANT() {
        return pLANT;
    }

    public void setPLANT(String pLANT) {
        this.pLANT = pLANT;
    }

    public String getCOSTCENTER() {
        return cOSTCENTER;
    }

    public void setCOSTCENTER(String cOSTCENTER) {
        this.cOSTCENTER = cOSTCENTER;
    }

    public String getORDERNO() {
        return oRDERNO;
    }

    public void setORDERNO(String oRDERNO) {
        this.oRDERNO = oRDERNO;
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


