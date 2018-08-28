package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_BOM_Reservation
{
    @SerializedName("Muser")
    @Expose
    private String muser;
    @SerializedName("Deviceid")
    @Expose
    private String deviceid;
    @SerializedName("Devicesno")
    @Expose
    private String devicesno;
    @SerializedName("Udid")
    @Expose
    private String udid;
    @SerializedName("IvTransmitType")
    @Expose
    private String ivTransmitType;
    @SerializedName("IvCommit")
    @Expose
    private Boolean ivCommit;
    @SerializedName("IsReservHeader")
    @Expose
    private List<Model_BOM_IsReservHeader> isReservHeader = null;
    @SerializedName("ItReservComp")
    @Expose
    private List<Model_BOM_ItReservComp> itReservComp = null;
    @SerializedName("EtMessage")
    @Expose
    private List<Object> etMessage = null;

    public String getMuser() {
        return muser;
    }

    public void setMuser(String muser) {
        this.muser = muser;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicesno() {
        return devicesno;
    }

    public void setDevicesno(String devicesno) {
        this.devicesno = devicesno;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getIvTransmitType() {
        return ivTransmitType;
    }

    public void setIvTransmitType(String ivTransmitType) {
        this.ivTransmitType = ivTransmitType;
    }

    public Boolean getIvCommit() {
        return ivCommit;
    }

    public void setIvCommit(Boolean ivCommit) {
        this.ivCommit = ivCommit;
    }

    public List<Model_BOM_IsReservHeader> getIsReservHeader() {
        return isReservHeader;
    }

    public void setIsReservHeader(List<Model_BOM_IsReservHeader> isReservHeader) {
        this.isReservHeader = isReservHeader;
    }

    public List<Model_BOM_ItReservComp> getItReservComp() {
        return itReservComp;
    }

    public void setItReservComp(List<Model_BOM_ItReservComp> itReservComp) {
        this.itReservComp = itReservComp;
    }

    public List<Object> getEtMessage() {
        return etMessage;
    }

    public void setEtMessage(List<Object> etMessage) {
        this.etMessage = etMessage;
    }
}