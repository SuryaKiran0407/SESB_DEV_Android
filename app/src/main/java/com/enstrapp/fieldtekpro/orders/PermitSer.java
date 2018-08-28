package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PermitSer {

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
    @SerializedName("Operation")
    @Expose
    private String operation;
    @SerializedName("IvMchk")
    @Expose
    private ArrayList<IvMchkSer> ivMchk = null;
    @SerializedName("ItWcmWwData")
    @Expose
    private ArrayList<OrdrWcmWwSer> itWcmWwData = null;
    @SerializedName("ItWcmWaData")
    @Expose
    private ArrayList<OrdrWcmWaSer> itWcmWaData = null;
    @SerializedName("ItWcmWaChkReq")
    @Expose
    private ArrayList<OrdrWcmChkRqSer> itWcmWaChkReq = null;
    @SerializedName("ItWcmWdData")
    @Expose
    private ArrayList<OrdrWcmWdSer> itWcmWdData = null;
    @SerializedName("ItWcmWdItemData")
    @Expose
    private ArrayList<OrdrWcmWdItmSer> itWcmWdItemData = null;
    @SerializedName("ItWcmWcagns")
    @Expose
    private ArrayList<OrdrWcmWagnsSer> itWcmWcagns = null;
    @SerializedName("ItOrderStatus")
    @Expose
    private ArrayList<OrdrStatusSer> itOrderStatus = null;
    @SerializedName("ItDocs")
    @Expose
    private ArrayList<OrdrDocSer> itDocs = null;
    @SerializedName("EtMessages")
    @Expose
    private List etMessages = null;
    @SerializedName("EtWcmWwData")
    @Expose
    private List etWcmWwData = null;
    @SerializedName("EtWcmWaData")
    @Expose
    private List etWcmWaData = null;
    @SerializedName("EtWcmWaChkReq")
    @Expose
    private List etWcmWaChkReq = null;
    @SerializedName("EtWcmWdData")
    @Expose
    private ArrayList<EtWcmWdDataSer> etWcmWdData;
    @SerializedName("EtWcmWdItemData")
    @Expose
    private List etWcmWdItemData = null;
    @SerializedName("EtWcmWcagns")
    @Expose
    private List etWcmWcagns = null;
    @SerializedName("EtWcmWdDup")
    @Expose
    private List EtWcmWdDup = null;
    @SerializedName("EtOrderStatus")
    @Expose
    private List EtOrderStatus = null;
    @SerializedName("EtDocs")
    @Expose
    private List EtDocs = null;

    public List getEtWcmWdDup() {
        return EtWcmWdDup;
    }

    public void setEtWcmWdDup(List etWcmWdDup) {
        EtWcmWdDup = etWcmWdDup;
    }

    public List getEtOrderStatus() {
        return EtOrderStatus;
    }

    public void setEtOrderStatus(List etOrderStatus) {
        EtOrderStatus = etOrderStatus;
    }

    public List getEtDocs() {
        return EtDocs;
    }

    public void setEtDocs(List etDocs) {
        EtDocs = etDocs;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public ArrayList<IvMchkSer> getIvMchk() {
        return ivMchk;
    }

    public void setIvMchk(ArrayList<IvMchkSer> ivMchk) {
        this.ivMchk = ivMchk;
    }

    public ArrayList<OrdrWcmWwSer> getItWcmWwData() {
        return itWcmWwData;
    }

    public void setItWcmWwData(ArrayList<OrdrWcmWwSer> itWcmWwData) {
        this.itWcmWwData = itWcmWwData;
    }

    public ArrayList<OrdrWcmWaSer> getItWcmWaData() {
        return itWcmWaData;
    }

    public void setItWcmWaData(ArrayList<OrdrWcmWaSer> itWcmWaData) {
        this.itWcmWaData = itWcmWaData;
    }

    public ArrayList<OrdrWcmChkRqSer> getItWcmWaChkReq() {
        return itWcmWaChkReq;
    }

    public void setItWcmWaChkReq(ArrayList<OrdrWcmChkRqSer> itWcmWaChkReq) {
        this.itWcmWaChkReq = itWcmWaChkReq;
    }

    public ArrayList<OrdrWcmWdSer> getItWcmWdData() {
        return itWcmWdData;
    }

    public void setItWcmWdData(ArrayList<OrdrWcmWdSer> itWcmWdData) {
        this.itWcmWdData = itWcmWdData;
    }

    public ArrayList<OrdrWcmWdItmSer> getItWcmWdItemData() {
        return itWcmWdItemData;
    }

    public void setItWcmWdItemData(ArrayList<OrdrWcmWdItmSer> itWcmWdItemData) {
        this.itWcmWdItemData = itWcmWdItemData;
    }

    public ArrayList<OrdrWcmWagnsSer> getItWcmWcagns() {
        return itWcmWcagns;
    }

    public void setItWcmWcagns(ArrayList<OrdrWcmWagnsSer> itWcmWcagns) {
        this.itWcmWcagns = itWcmWcagns;
    }

    public ArrayList<OrdrStatusSer> getItOrderStatus() {
        return itOrderStatus;
    }

    public void setItOrderStatus(ArrayList<OrdrStatusSer> itOrderStatus) {
        this.itOrderStatus = itOrderStatus;
    }

    public ArrayList<OrdrDocSer> getItDocs() {
        return itDocs;
    }

    public void setItDocs(ArrayList<OrdrDocSer> itDocs) {
        this.itDocs = itDocs;
    }

    public List getEtMessages() {
        return etMessages;
    }

    public void setEtMessages(List etMessages) {
        this.etMessages = etMessages;
    }

    public List getEtWcmWwData() {
        return etWcmWwData;
    }

    public void setEtWcmWwData(List etWcmWwData) {
        this.etWcmWwData = etWcmWwData;
    }

    public List getEtWcmWaData() {
        return etWcmWaData;
    }

    public void setEtWcmWaData(List etWcmWaData) {
        this.etWcmWaData = etWcmWaData;
    }

    public List getEtWcmWaChkReq() {
        return etWcmWaChkReq;
    }

    public void setEtWcmWaChkReq(List etWcmWaChkReq) {
        this.etWcmWaChkReq = etWcmWaChkReq;
    }

    public ArrayList<EtWcmWdDataSer> getEtWcmWdData() {
        return etWcmWdData;
    }

    public void setEtWcmWdData(ArrayList<EtWcmWdDataSer> etWcmWdData) {
        this.etWcmWdData = etWcmWdData;
    }

    public List getEtWcmWdItemData() {
        return etWcmWdItemData;
    }

    public void setEtWcmWdItemData(List etWcmWdItemData) {
        this.etWcmWdItemData = etWcmWdItemData;
    }

    public List getEtWcmWcagns() {
        return etWcmWcagns;
    }

    public void setEtWcmWcagns(List etWcmWcagns) {
        this.etWcmWcagns = etWcmWcagns;
    }
}

