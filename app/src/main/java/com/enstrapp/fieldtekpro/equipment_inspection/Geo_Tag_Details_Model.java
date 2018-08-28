package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Geo_Tag_Details_Model
{
    @SerializedName("IvTransmitType")
    @Expose
    private String IvTransmitType;
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
    @SerializedName("ItGeoData")
    @Expose
    private List<ItGeoData> ItGeoData = null;

    public String getIvTransmitType() {
        return IvTransmitType;
    }

    public void setIvTransmitType(String ivTransmitType) {
        IvTransmitType = ivTransmitType;
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

    public List<com.enstrapp.fieldtekpro.equipment_inspection.ItGeoData> getItGeoData() {
        return ItGeoData;
    }

    public void setItGeoData(List<com.enstrapp.fieldtekpro.equipment_inspection.ItGeoData> itGeoData) {
        ItGeoData = itGeoData;
    }
}