package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MAC_Model1
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
    @SerializedName("ItMatnrPost")
    @Expose
    private List<ItMatnrPost> itMatnrPost = null;

    @SerializedName("EvAvailable")
    @Expose
    private List evAvailable = null;

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

    public List<ItMatnrPost> getItMatnrPost() {
        return itMatnrPost;
    }

    public void setItMatnrPost(List<ItMatnrPost> itMatnrPost) {
        this.itMatnrPost = itMatnrPost;
    }

    public List getEvAvailable() {
        return evAvailable;
    }

    public void setEvAvailable(List evAvailable) {
        this.evAvailable = evAvailable;
    }

}