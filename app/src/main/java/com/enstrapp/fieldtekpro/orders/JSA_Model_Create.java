package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JSA_Model_Create
{
    public String getIvAufnr() {
        return IvAufnr;
    }

    public void setIvAufnr(String ivAufnr) {
        IvAufnr = ivAufnr;
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

    public ArrayList<JSA_Model_BasicInfo> getIsJSAHdr() {
        return IsJSAHdr;
    }

    public void setIsJSAHdr(ArrayList<JSA_Model_BasicInfo> isJSAHdr) {
        IsJSAHdr = isJSAHdr;
    }

    public ArrayList<JSA_Model_AssessmentTeam> getItJSAPer() {
        return ItJSAPer;
    }

    public void setItJSAPer(ArrayList<JSA_Model_AssessmentTeam> itJSAPer) {
        ItJSAPer = itJSAPer;
    }

    @SerializedName("IvAufnr")
    @Expose
    private String IvAufnr;
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
    @SerializedName("IvTransmitType")
    @Expose
    private String IvTransmitType;
    @SerializedName("IsJSAHdr")
    @Expose
    private ArrayList<JSA_Model_BasicInfo> IsJSAHdr;
    @SerializedName("ItJSAPer")
    @Expose
    private ArrayList<JSA_Model_AssessmentTeam> ItJSAPer;
    @SerializedName("ItJSARisks")
    @Expose
    private ArrayList<JSA_Model_ItJSARisks> ItJSARisks;
    @SerializedName("ItJSAImp")
    @Expose
    private ArrayList<JSA_Model_ItJSAImp> ItJSAImp;
    @SerializedName("ItJSARskCtrl")
    @Expose
    private ArrayList<JSA_Model_ItJSARskCtrl> ItJSARskCtrl;
    @SerializedName("EtJSAHdr")
    @Expose
    private List EtJSAHdr = null;
    @SerializedName("EtJSAPer")
    @Expose
    private List EtJSAPer = null;
    @SerializedName("EtJSARisks")
    @Expose
    private List EtJSARisks = null;
    @SerializedName("EtJSAImp")
    @Expose
    private List EtJSAImp = null;
    @SerializedName("EtJSARskCtrl")
    @Expose
    private List EtJSARskCtrl = null;
    @SerializedName("EtMessages")
    @Expose
    private List EtMessages = null;

    public List getEtMessages() {
        return EtMessages;
    }

    public void setEtMessages(List etMessages) {
        EtMessages = etMessages;
    }

    public ArrayList<JSA_Model_ItJSAImp> getItJSAImp() {
        return ItJSAImp;
    }

    public void setItJSAImp(ArrayList<JSA_Model_ItJSAImp> itJSAImp) {
        ItJSAImp = itJSAImp;
    }

    public ArrayList<JSA_Model_ItJSARskCtrl> getItJSARskCtrl() {
        return ItJSARskCtrl;
    }

    public void setItJSARskCtrl(ArrayList<JSA_Model_ItJSARskCtrl> itJSARskCtrl) {
        ItJSARskCtrl = itJSARskCtrl;
    }

    public List getEtJSAImp() {
        return EtJSAImp;
    }

    public void setEtJSAImp(List etJSAImp) {
        EtJSAImp = etJSAImp;
    }

    public List getEtJSARskCtrl() {
        return EtJSARskCtrl;
    }

    public void setEtJSARskCtrl(List etJSARskCtrl) {
        EtJSARskCtrl = etJSARskCtrl;
    }

    public ArrayList<JSA_Model_ItJSARisks> getItJSARisks() {
        return ItJSARisks;
    }

    public void setItJSARisks(ArrayList<JSA_Model_ItJSARisks> itJSARisks) {
        ItJSARisks = itJSARisks;
    }

    public List getEtJSARisks() {
        return EtJSARisks;
    }

    public void setEtJSARisks(List etJSARisks) {
        EtJSARisks = etJSARisks;
    }

    public List getEtJSAHdr() {
        return EtJSAHdr;
    }

    public void setEtJSAHdr(List etJSAHdr) {
        EtJSAHdr = etJSAHdr;
    }

    public List getEtJSAPer() {
        return EtJSAPer;
    }

    public void setEtJSAPer(List etJSAPer) {
        EtJSAPer = etJSAPer;
    }
}
