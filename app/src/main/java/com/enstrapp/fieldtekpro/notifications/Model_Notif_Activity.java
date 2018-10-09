package com.enstrapp.fieldtekpro.notifications;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Activity
{
    @SerializedName("Qmnum")
    @Expose
    private String qmnum;
    @SerializedName("ItemKey")
    @Expose
    private String itemKey;
    @SerializedName("ItempartGrp")
    @Expose
    private String itempartGrp;
    @SerializedName("Partgrptext")
    @Expose
    private String partgrptext;
    @SerializedName("ItempartCod")
    @Expose
    private String itempartCod;
    @SerializedName("Partcodetext")
    @Expose
    private String partcodetext;
    @SerializedName("ItemdefectGrp")
    @Expose
    private String itemdefectGrp;
    @SerializedName("Defectgrptext")
    @Expose
    private String defectgrptext;
    @SerializedName("ItemdefectCod")
    @Expose
    private String itemdefectCod;
    @SerializedName("Defectcodetext")
    @Expose
    private String defectcodetext;
    @SerializedName("ItemdefectShtxt")
    @Expose
    private String itemdefectShtxt;
    @SerializedName("CauseKey")
    @Expose
    private String causeKey;
    @SerializedName("ActvKey")
    @Expose
    private String actvKey;
    @SerializedName("ActvGrp")
    @Expose
    private String actvGrp;
    @SerializedName("ActvCod")
    @Expose
    private String actvCod;
    @SerializedName("Actcodetext")
    @Expose
    private String actcodetext;
    @SerializedName("ActvShtxt")
    @Expose
    private String actvShtxt;
    @SerializedName("StartDate")
    @Expose
    private String StartDate;
    @SerializedName("StartTime")
    @Expose
    private String StartTime;
    @SerializedName("EndDate")
    @Expose
    private String EndDate;
    @SerializedName("EndTime")
    @Expose
    private String EndTime;
    @SerializedName("Usr01")
    @Expose
    private String usr01;
    @SerializedName("Usr02")
    @Expose
    private String usr02;
    @SerializedName("Usr03")
    @Expose
    private String usr03;
    @SerializedName("Usr04")
    @Expose
    private String usr04;
    @SerializedName("Usr05")
    @Expose
    private String usr05;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("ItNotifActvsFields")
    @Expose
    private List<Model_CustomInfo> itNotifActvsFields = null;


    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public List<Model_CustomInfo> getItNotifActvsFields() {
        return itNotifActvsFields;
    }

    public void setItNotifActvsFields(List<Model_CustomInfo> itNotifActvsFields) {
        this.itNotifActvsFields = itNotifActvsFields;
    }

    public String getQmnum() {
        return qmnum;
    }

    public void setQmnum(String qmnum) {
        this.qmnum = qmnum;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItempartGrp() {
        return itempartGrp;
    }

    public void setItempartGrp(String itempartGrp) {
        this.itempartGrp = itempartGrp;
    }

    public String getPartgrptext() {
        return partgrptext;
    }

    public void setPartgrptext(String partgrptext) {
        this.partgrptext = partgrptext;
    }

    public String getItempartCod() {
        return itempartCod;
    }

    public void setItempartCod(String itempartCod) {
        this.itempartCod = itempartCod;
    }

    public String getPartcodetext() {
        return partcodetext;
    }

    public void setPartcodetext(String partcodetext) {
        this.partcodetext = partcodetext;
    }

    public String getItemdefectGrp() {
        return itemdefectGrp;
    }

    public void setItemdefectGrp(String itemdefectGrp) {
        this.itemdefectGrp = itemdefectGrp;
    }

    public String getDefectgrptext() {
        return defectgrptext;
    }

    public void setDefectgrptext(String defectgrptext) {
        this.defectgrptext = defectgrptext;
    }

    public String getItemdefectCod() {
        return itemdefectCod;
    }

    public void setItemdefectCod(String itemdefectCod) {
        this.itemdefectCod = itemdefectCod;
    }

    public String getDefectcodetext() {
        return defectcodetext;
    }

    public void setDefectcodetext(String defectcodetext) {
        this.defectcodetext = defectcodetext;
    }

    public String getItemdefectShtxt() {
        return itemdefectShtxt;
    }

    public void setItemdefectShtxt(String itemdefectShtxt) {
        this.itemdefectShtxt = itemdefectShtxt;
    }

    public String getCauseKey() {
        return causeKey;
    }

    public void setCauseKey(String causeKey) {
        this.causeKey = causeKey;
    }

    public String getActvKey() {
        return actvKey;
    }

    public void setActvKey(String actvKey) {
        this.actvKey = actvKey;
    }

    public String getActvGrp() {
        return actvGrp;
    }

    public void setActvGrp(String actvGrp) {
        this.actvGrp = actvGrp;
    }

    public String getActvCod() {
        return actvCod;
    }

    public void setActvCod(String actvCod) {
        this.actvCod = actvCod;
    }

    public String getActcodetext() {
        return actcodetext;
    }

    public void setActcodetext(String actcodetext) {
        this.actcodetext = actcodetext;
    }

    public String getActvShtxt() {
        return actvShtxt;
    }

    public void setActvShtxt(String actvShtxt) {
        this.actvShtxt = actvShtxt;
    }

    public String getUsr01() {
        return usr01;
    }

    public void setUsr01(String usr01) {
        this.usr01 = usr01;
    }

    public String getUsr02() {
        return usr02;
    }

    public void setUsr02(String usr02) {
        this.usr02 = usr02;
    }

    public String getUsr03() {
        return usr03;
    }

    public void setUsr03(String usr03) {
        this.usr03 = usr03;
    }

    public String getUsr04() {
        return usr04;
    }

    public void setUsr04(String usr04) {
        this.usr04 = usr04;
    }

    public String getUsr05() {
        return usr05;
    }

    public void setUsr05(String usr05) {
        this.usr05 = usr05;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}