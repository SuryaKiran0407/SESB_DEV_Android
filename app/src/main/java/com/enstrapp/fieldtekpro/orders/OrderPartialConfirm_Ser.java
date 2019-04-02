package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderPartialConfirm_Ser {

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
    @SerializedName("IvCommit")
    @Expose
    private Boolean IvCommit;
    @SerializedName("Operation")
    @Expose
    private String Operation;
    @SerializedName("WorkCntr")
    @Expose
    private  String WorkCntr;
    @SerializedName("ItConfirmOrder")
    @Expose
    private ArrayList<ItConfirmOrder_Ser> ItConfirmOrder;
    @SerializedName("ItImrg")
    @Expose
    private ArrayList<ItImrg_Ser> ItImrg;
    @SerializedName("ItOrderHeader")
    @Expose
    private ArrayList<OrdrHdrSer> ItOrderHeader;
    @SerializedName("ItOrderOperations")
    @Expose
    private ArrayList<OrdrOprtnSer> ItOrderOperations;
    @SerializedName("ItOrderComponents")
    @Expose
    private ArrayList<OrdrMatrlSer> ItOrderComponents;
    @SerializedName("ItOrderLongtext")
    @Expose
    private ArrayList<OrdrLngTxtSer> ItOrderLongtext;
    @SerializedName("ItOrderPermits")
    @Expose
    private ArrayList<OrdrPermitSer> ItOrderPermits;
    @SerializedName("ItOrderOlist")
    @Expose
    private ArrayList<OrdrOlistSer> ItOrderOlist;
    @SerializedName("ItOrderStatus")
    @Expose
    private ArrayList<OrdrStatusSer> ItOrderStatus;
    @SerializedName("ItWsmOrdSafemeas")
    @Expose
    private ArrayList<OrdrWsmSafeMeasrSer> ItWsmOrdSafemeas;
    @SerializedName("ItWcmWwData")
    @Expose
    private ArrayList<OrdrWcmWwSer> ItWcmWwData;
    @SerializedName("ItWcmWaData")
    @Expose
    private ArrayList<OrdrWcmWaSer> ItWcmWaData;
    @SerializedName("ItWcmWaChkReq")
    @Expose
    private ArrayList<OrdrWcmChkRqSer> ItWcmWaChkReq;
    @SerializedName("ItWcmWdData")
    @Expose
    private ArrayList<OrdrWcmWdSer> ItWcmWdData;
    @SerializedName("ItWcmWdItemData")
    @Expose
    private ArrayList<OrdrWcmWdItmSer> ItWcmWdItemData;
    @SerializedName("ItWcmWcagns")
    @Expose
    private ArrayList<OrdrWcmWagnsSer> ItWcmWcagns;
    @SerializedName("ItDocs")
    @Expose
    private ArrayList<OrdrDocSer> ItDocs;
    @SerializedName("IsGeo")
    @Expose
    private ArrayList<OrdrGeoSer> IsGeo;
    @SerializedName("EsAufnr")
    @Expose
    private List EsAufnr = null;
    @SerializedName("EtOrderHeader")
    @Expose
    private List EtOrderHeader = null;
    @SerializedName("EtOrderOperations")
    @Expose
    private List EtOrderOperations = null;
    @SerializedName("EtOrderLongtext")
    @Expose
    private List EtOrderLongtext = null;
    @SerializedName("EtOrderPermits")
    @Expose
    private List EtOrderPermits = null;
    @SerializedName("EtOrderOlist")
    @Expose
    private List EtOrderOlist = null;
    @SerializedName("EtOrderStatus")
    @Expose
    private List EtOrderStatus = null;
    @SerializedName("EtDocs")
    @Expose
    private List EtDocs = null;
    @SerializedName("EtWsmOrdSafemeas")
    @Expose
    private List EtWsmOrdSafemeas = null;
    @SerializedName("EtWcmWwData")
    @Expose
    private List EtWcmWwData = null;
    @SerializedName("EtWcmWaData")
    @Expose
    private List EtWcmWaData = null;
    @SerializedName("EtWcmWaChkReq")
    @Expose
    private List EtWcmWaChkReq = null;
    @SerializedName("EtWcmWdData")
    @Expose
    private List EtWcmWdData = null;
    @SerializedName("EtWcmWdItemData")
    @Expose
    private List EtWcmWdItemData = null;
    @SerializedName("EtWcmWcagns")
    @Expose
    private List EtWcmWcagns = null;
    @SerializedName("EtOrderComponents")
    @Expose
    private List EtOrderComponents = null;
    @SerializedName("EtMessages")
    @Expose
    private List EtMessages = null;

    public ArrayList<ItImrg_Ser> getItImrg() {
        return ItImrg;
    }

    public void setItImrg(ArrayList<ItImrg_Ser> itImrg) {
        ItImrg = itImrg;
    }

    public ArrayList<ItConfirmOrder_Ser> getItConfirmOrder() {
        return ItConfirmOrder;
    }

    public void setItConfirmOrder(ArrayList<ItConfirmOrder_Ser> itConfirmOrder) {
        ItConfirmOrder = itConfirmOrder;
    }

    public List getEtMessages() {
        return EtMessages;
    }

    public void setEtMessages(List etMessages) {
        EtMessages = etMessages;
    }

    public ArrayList<OrdrOprtnSer> getItOrderOperations() {
        return ItOrderOperations;
    }

    public void setItOrderOperations(ArrayList<OrdrOprtnSer> itOrderOperations) {
        ItOrderOperations = itOrderOperations;
    }

    public ArrayList<OrdrMatrlSer> getItOrderComponents() {
        return ItOrderComponents;
    }

    public void setItOrderComponents(ArrayList<OrdrMatrlSer> itOrderComponents) {
        ItOrderComponents = itOrderComponents;
    }

    public ArrayList<OrdrLngTxtSer> getItOrderLongtext() {
        return ItOrderLongtext;
    }

    public void setItOrderLongtext(ArrayList<OrdrLngTxtSer> itOrderLongtext) {
        ItOrderLongtext = itOrderLongtext;
    }

    public ArrayList<OrdrPermitSer> getItOrderPermits() {
        return ItOrderPermits;
    }

    public void setItOrderPermits(ArrayList<OrdrPermitSer> itOrderPermits) {
        ItOrderPermits = itOrderPermits;
    }

    public ArrayList<OrdrOlistSer> getItOrderOlist() {
        return ItOrderOlist;
    }

    public void setItOrderOlist(ArrayList<OrdrOlistSer> itOrderOlist) {
        ItOrderOlist = itOrderOlist;
    }

    public ArrayList<OrdrStatusSer> getItOrderStatus() {
        return ItOrderStatus;
    }

    public void setItOrderStatus(ArrayList<OrdrStatusSer> itOrderStatus) {
        ItOrderStatus = itOrderStatus;
    }

    public ArrayList<OrdrWsmSafeMeasrSer> getItWsmOrdSafemeas() {
        return ItWsmOrdSafemeas;
    }

    public void setItWsmOrdSafemeas(ArrayList<OrdrWsmSafeMeasrSer> itWsmOrdSafemeas) {
        ItWsmOrdSafemeas = itWsmOrdSafemeas;
    }

    public ArrayList<OrdrWcmWwSer> getItWcmWwData() {
        return ItWcmWwData;
    }

    public void setItWcmWwData(ArrayList<OrdrWcmWwSer> itWcmWwData) {
        ItWcmWwData = itWcmWwData;
    }

    public ArrayList<OrdrWcmWaSer> getItWcmWaData() {
        return ItWcmWaData;
    }

    public void setItWcmWaData(ArrayList<OrdrWcmWaSer> itWcmWaData) {
        ItWcmWaData = itWcmWaData;
    }

    public ArrayList<OrdrWcmChkRqSer> getItWcmWaChkReq() {
        return ItWcmWaChkReq;
    }

    public void setItWcmWaChkReq(ArrayList<OrdrWcmChkRqSer> itWcmWaChkReq) {
        ItWcmWaChkReq = itWcmWaChkReq;
    }

    public ArrayList<OrdrWcmWdSer> getItWcmWdData() {
        return ItWcmWdData;
    }

    public void setItWcmWdData(ArrayList<OrdrWcmWdSer> itWcmWdData) {
        ItWcmWdData = itWcmWdData;
    }

    public ArrayList<OrdrWcmWdItmSer> getItWcmWdItemData() {
        return ItWcmWdItemData;
    }

    public void setItWcmWdItemData(ArrayList<OrdrWcmWdItmSer> itWcmWdItemData) {
        ItWcmWdItemData = itWcmWdItemData;
    }

    public ArrayList<OrdrWcmWagnsSer> getItWcmWcagns() {
        return ItWcmWcagns;
    }

    public void setItWcmWcagns(ArrayList<OrdrWcmWagnsSer> itWcmWcagns) {
        ItWcmWcagns = itWcmWcagns;
    }

    public ArrayList<OrdrDocSer> getItDocs() {
        return ItDocs;
    }

    public void setItDocs(ArrayList<OrdrDocSer> itDocs) {
        ItDocs = itDocs;
    }

    public ArrayList<OrdrGeoSer> getIsGeo() {
        return IsGeo;
    }

    public void setIsGeo(ArrayList<OrdrGeoSer> isGeo) {
        IsGeo = isGeo;
    }

    public List getEtOrderOperations() {
        return EtOrderOperations;
    }

    public void setEtOrderOperations(List etOrderOperations) {
        EtOrderOperations = etOrderOperations;
    }

    public List getEtOrderLongtext() {
        return EtOrderLongtext;
    }

    public void setEtOrderLongtext(List etOrderLongtext) {
        EtOrderLongtext = etOrderLongtext;
    }

    public List getEtOrderPermits() {
        return EtOrderPermits;
    }

    public void setEtOrderPermits(List etOrderPermits) {
        EtOrderPermits = etOrderPermits;
    }

    public List getEtOrderOlist() {
        return EtOrderOlist;
    }

    public void setEtOrderOlist(List etOrderOlist) {
        EtOrderOlist = etOrderOlist;
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

    public List getEtWsmOrdSafemeas() {
        return EtWsmOrdSafemeas;
    }

    public void setEtWsmOrdSafemeas(List etWsmOrdSafemeas) {
        EtWsmOrdSafemeas = etWsmOrdSafemeas;
    }

    public List getEtWcmWwData() {
        return EtWcmWwData;
    }

    public void setEtWcmWwData(List etWcmWwData) {
        EtWcmWwData = etWcmWwData;
    }

    public List getEtWcmWaData() {
        return EtWcmWaData;
    }

    public void setEtWcmWaData(List etWcmWaData) {
        EtWcmWaData = etWcmWaData;
    }

    public List getEtWcmWaChkReq() {
        return EtWcmWaChkReq;
    }

    public void setEtWcmWaChkReq(List etWcmWaChkReq) {
        EtWcmWaChkReq = etWcmWaChkReq;
    }

    public List getEtWcmWdData() {
        return EtWcmWdData;
    }

    public void setEtWcmWdData(List etWcmWdData) {
        EtWcmWdData = etWcmWdData;
    }

    public List getEtWcmWdItemData() {
        return EtWcmWdItemData;
    }

    public void setEtWcmWdItemData(List etWcmWdItemData) {
        EtWcmWdItemData = etWcmWdItemData;
    }

    public List getEtWcmWcagns() {
        return EtWcmWcagns;
    }

    public void setEtWcmWcagns(List etWcmWcagns) {
        EtWcmWcagns = etWcmWcagns;
    }

    public List getEtOrderComponents() {
        return EtOrderComponents;
    }

    public void setEtOrderComponents(List etOrderComponents) {
        EtOrderComponents = etOrderComponents;
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

    public ArrayList<OrdrHdrSer> getItOrderHeader() {
        return ItOrderHeader;
    }

    public void setItOrderHeader(ArrayList<OrdrHdrSer> itOrderHeader) {
        ItOrderHeader = itOrderHeader;
    }

    public List getEsAufnr() {
        return EsAufnr;
    }

    public void setEsAufnr(List esAufnr) {
        EsAufnr = esAufnr;
    }

    public List getEtOrderHeader() {
        return EtOrderHeader;
    }

    public void setEtOrderHeader(List etOrderHeader) {
        EtOrderHeader = etOrderHeader;
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

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

}



