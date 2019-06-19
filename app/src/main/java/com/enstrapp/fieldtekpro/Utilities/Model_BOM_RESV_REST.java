package com.enstrapp.fieldtekpro.Utilities;

import com.enstrapp.fieldtekpro.notifications.Model_Notif_Activity;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Attachments;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Causecode;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Header_Custominfo;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Header_REST;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Longtext;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Status;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Task;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model_BOM_RESV_REST
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
    @SerializedName("is_reserv_header")
    @Expose
    private is_reserv_header is_reserv_header;
    @SerializedName("is_device")
    @Expose
    private is_device is_device;
    @SerializedName("it_reserv_comp")
    @Expose
    private ArrayList<is_reserv_comp> it_reserv_comp;


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

    public com.enstrapp.fieldtekpro.Utilities.is_reserv_header getIs_reserv_header() {
        return is_reserv_header;
    }

    public void setIs_reserv_header(com.enstrapp.fieldtekpro.Utilities.is_reserv_header is_reserv_header) {
        this.is_reserv_header = is_reserv_header;
    }

    public ArrayList<is_reserv_comp> getIt_reserv_comp() {
        return it_reserv_comp;
    }

    public void setIt_reserv_comp(ArrayList<is_reserv_comp> it_reserv_comp) {
        this.it_reserv_comp = it_reserv_comp;
    }
}


