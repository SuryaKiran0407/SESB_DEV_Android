package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_BOM_ItReservComp
{
    @SerializedName("BomComponent")
    @Expose
    private String bomComponent;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("ReqmtDate")
    @Expose
    private String reqmtDate;
    @SerializedName("StoreLoc")
    @Expose
    private String storeLoc;

    public String getBomComponent() {
        return bomComponent;
    }

    public void setBomComponent(String bomComponent) {
        this.bomComponent = bomComponent;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReqmtDate() {
        return reqmtDate;
    }

    public void setReqmtDate(String reqmtDate) {
        this.reqmtDate = reqmtDate;
    }

    public String getStoreLoc() {
        return storeLoc;
    }

    public void setStoreLoc(String storeLoc) {
        this.storeLoc = storeLoc;
    }
}