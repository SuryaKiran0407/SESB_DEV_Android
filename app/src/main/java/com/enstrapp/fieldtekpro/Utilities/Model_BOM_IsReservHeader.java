package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_BOM_IsReservHeader
{
    @SerializedName("MovementType")
    @Expose
    private String movementType;
    @SerializedName("Plant")
    @Expose
    private String plant;
    @SerializedName("CostCenter")
    @Expose
    private String costCenter;
    @SerializedName("OrderNo")
    @Expose
    private String orderNo;

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}