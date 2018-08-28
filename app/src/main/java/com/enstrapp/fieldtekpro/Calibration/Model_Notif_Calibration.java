package com.enstrapp.fieldtekpro.Calibration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Calibration
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
    @SerializedName("ItQinspData")
    @Expose
    private List<Model_Notif_Calibration_Operations> itQinspData = null;
    @SerializedName("ItQudData")
    @Expose
    private List<Model_Notif_Calibration_UsageDecision> itQudData = null;
    @SerializedName("ItQdefectData")
    @Expose
    private List<Model_Notif_Calibration_Defects> itQdefectData = null;


    @SerializedName("EtQinspData")
    @Expose
    private List EtQinspData = null;
    @SerializedName("EtQdefectData")
    @Expose
    private List EtQdefectData = null;
    @SerializedName("EtQudData")
    @Expose
    private List EtQudData = null;

    @SerializedName("EtMessage")
    @Expose
    private List EtMessage = null;


    public List<Model_Notif_Calibration_Defects> getItQdefectData() {
        return itQdefectData;
    }

    public void setItQdefectData(List<Model_Notif_Calibration_Defects> itQdefectData) {
        this.itQdefectData = itQdefectData;
    }

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

    public List<Model_Notif_Calibration_Operations> getItQinspData() {
        return itQinspData;
    }

    public void setItQinspData(List<Model_Notif_Calibration_Operations> itQinspData) {
        this.itQinspData = itQinspData;
    }

    public List getEtQinspData() {
        return EtQinspData;
    }

    public void setEtQinspData(List etQinspData) {
        EtQinspData = etQinspData;
    }

    public List getEtQdefectData() {
        return EtQdefectData;
    }

    public void setEtQdefectData(List etQdefectData) {
        EtQdefectData = etQdefectData;
    }

    public List getEtQudData() {
        return EtQudData;
    }

    public void setEtQudData(List etQudData) {
        EtQudData = etQudData;
    }

    public List getEtMessage() {
        return EtMessage;
    }

    public void setEtMessage(List etMessage) {
        EtMessage = etMessage;
    }

    public List<Model_Notif_Calibration_UsageDecision> getItQudData() {
        return itQudData;
    }

    public void setItQudData(List<Model_Notif_Calibration_UsageDecision> itQudData) {
        this.itQudData = itQudData;
    }
}