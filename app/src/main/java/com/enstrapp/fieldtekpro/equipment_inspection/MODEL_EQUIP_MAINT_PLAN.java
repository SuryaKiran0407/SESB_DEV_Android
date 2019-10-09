package com.enstrapp.fieldtekpro.equipment_inspection;

import com.enstrapp.fieldtekpro.Utilities.is_device;
import com.enstrapp.fieldtekpro.Utilities.is_reserv_comp;
import com.enstrapp.fieldtekpro.Utilities.is_reserv_header;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MODEL_EQUIP_MAINT_PLAN
{
    @SerializedName("MUSER")
    @Expose
    private String Muser;
    @SerializedName("DEVICEID")
    @Expose
    private String Deviceid;
    @SerializedName("DEVICESNO")
    @Expose
    private String Devicesno;
    @SerializedName("UDID")
    @Expose
    private String Udid;
    @SerializedName("IV_TRANSMIT_TYPE")
    @Expose
    private String IvTransmitType;
    @SerializedName("IV_COMMIT")
    @Expose
    private String IvCommit;
    @SerializedName("iv_equnr")
    @Expose
    private String iv_equnr;
    @SerializedName("is_device")
    @Expose
    private is_device is_device;



    public com.enstrapp.fieldtekpro.Utilities.is_device getIs_device() {
        return is_device;
    }

    public void setIs_device(com.enstrapp.fieldtekpro.Utilities.is_device is_device) {
        this.is_device = is_device;
    }

    public String getMuser() {
        return Muser;
    }

    public void setMuser(String muser) {
        Muser = muser;
    }

    public String getDeviceid() {
        return Deviceid;
    }

    public void setDeviceid(String deviceid) {
        Deviceid = deviceid;
    }

    public String getDevicesno() {
        return Devicesno;
    }

    public void setDevicesno(String devicesno) {
        Devicesno = devicesno;
    }

    public String getUdid() {
        return Udid;
    }

    public void setUdid(String udid) {
        Udid = udid;
    }

    public String getIvTransmitType() {
        return IvTransmitType;
    }

    public void setIvTransmitType(String ivTransmitType) {
        IvTransmitType = ivTransmitType;
    }

    public String getIvCommit() {
        return IvCommit;
    }

    public void setIvCommit(String ivCommit) {
        IvCommit = ivCommit;
    }

    public String getIv_equnr() {
        return iv_equnr;
    }

    public void setIv_equnr(String iv_equnr) {
        this.iv_equnr = iv_equnr;
    }
}


