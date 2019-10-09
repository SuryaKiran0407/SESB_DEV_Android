package com.enstrapp.fieldtekpro.equipment_inspection;


import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Geo_Tag_Details_Model_REST
{
    @SerializedName("is_device")
    @Expose
    private Model_Notif_Create_REST.IsDevice isDevice;
    @SerializedName("iv_transmit_type")
    @Expose
    private String IvTransmitType;
    @SerializedName("iv_commit")
    @Expose
    private Boolean IvCommit;
    @SerializedName("it_geo")
    @Expose
    private List<ItGeoData_REST> it_geo;

    public Model_Notif_Create_REST.IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(Model_Notif_Create_REST.IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public String getIvTransmitType() {
        return IvTransmitType;
    }

    public void setIvTransmitType(String ivTransmitType) {
        IvTransmitType = ivTransmitType;
    }

    public Boolean getIvCommit() {
        return IvCommit;
    }

    public void setIvCommit(Boolean ivCommit) {
        IvCommit = ivCommit;
    }

    public List<ItGeoData_REST> getIt_geo() {
        return it_geo;
    }

    public void setIt_geo(List<ItGeoData_REST> it_geo) {
        this.it_geo = it_geo;
    }
}