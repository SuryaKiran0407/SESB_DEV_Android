package com.enstrapp.fieldtekpro.equipment_inspection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model_INSP_CHK
{
    @SerializedName("Muser")
    @Expose
    private String Muser;
    @SerializedName("Deviceid")
    @Expose
    private String Deviceid;
    @SerializedName("Devicesno")
    @Expose
    private String Devicesno;
    @SerializedName("Udid")
    @Expose
    private String Udid;
    @SerializedName("TransmitType")
    @Expose
    private String IvTransmitType;
    @SerializedName("VCommit")
    @Expose
    private Boolean IvCommit;
    @SerializedName("ItMeaImrg")
    @Expose
    private ArrayList<Model_INSP_Imrg> ItMeaImrg;
    @SerializedName("EtMsg")
    @Expose
    private List EtMsg = null;

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

    public Boolean getIvCommit() {
        return IvCommit;
    }

    public void setIvCommit(Boolean ivCommit) {
        IvCommit = ivCommit;
    }

    public ArrayList<Model_INSP_Imrg> getItMeaImrg() {
        return ItMeaImrg;
    }

    public void setItMeaImrg(ArrayList<Model_INSP_Imrg> itMeaImrg) {
        ItMeaImrg = itMeaImrg;
    }

    public List getEtMsg() {
        return EtMsg;
    }

    public void setEtMsg(List etMsg) {
        EtMsg = etMsg;
    }
}
