package com.enstrapp.fieldtekpro.PermitList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_Permit_Isolation
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
    @SerializedName("IvCommit")
    @Expose
    private Boolean ivCommit;
    @SerializedName("Operation")
    @Expose
    private String operation;
    @SerializedName("ItWcmWcagns")
    @Expose
    private List<Model_Permit_Isolation_ItWcmWcagn> itWcmWcagns = null;
    @SerializedName("EtWcmWcagns")
    @Expose
    private List<Model_Permit_Isolation_ItWcmWcagn> etWcmWcagns = null;
    @SerializedName("EtMessages")
    @Expose
    private List etMessages = null;

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

    public Boolean getIvCommit() {
        return ivCommit;
    }

    public void setIvCommit(Boolean ivCommit) {
        this.ivCommit = ivCommit;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<Model_Permit_Isolation_ItWcmWcagn> getItWcmWcagns() {
        return itWcmWcagns;
    }

    public void setItWcmWcagns(List<Model_Permit_Isolation_ItWcmWcagn> itWcmWcagns) {
        this.itWcmWcagns = itWcmWcagns;
    }

    public List<Model_Permit_Isolation_ItWcmWcagn> getEtWcmWcagns() {
        return etWcmWcagns;
    }

    public void setEtWcmWcagns(List<Model_Permit_Isolation_ItWcmWcagn> etWcmWcagns) {
        this.etWcmWcagns = etWcmWcagns;
    }

    public List getEtMessages() {
        return etMessages;
    }

    public void setEtMessages(List etMessages) {
        this.etMessages = etMessages;
    }
}
