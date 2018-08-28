package com.enstrapp.fieldtekpro.notifications;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Task
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
    @SerializedName("ItemdefectShtx")
    @Expose
    private String itemdefectShtx;
    @SerializedName("TaskKey")
    @Expose
    private String taskKey;
    @SerializedName("TaskGrp")
    @Expose
    private String taskGrp;
    @SerializedName("Taskgrptext")
    @Expose
    private String taskgrptext;
    @SerializedName("TaskCod")
    @Expose
    private String taskCod;
    @SerializedName("Taskcodetext")
    @Expose
    private String taskcodetext;
    @SerializedName("TaskShtxt")
    @Expose
    private String taskShtxt;
    @SerializedName("Pster")
    @Expose
    private String pster;
    @SerializedName("Peter")
    @Expose
    private String peter;
    @SerializedName("Pstur")
    @Expose
    private String pstur;
    @SerializedName("Petur")
    @Expose
    private String petur;
    @SerializedName("Parvw")
    @Expose
    private String parvw;
    @SerializedName("Parnr")
    @Expose
    private String parnr;
    @SerializedName("Erlnam")
    @Expose
    private String erlnam;
    @SerializedName("Erldat")
    @Expose
    private String erldat;
    @SerializedName("Erlzeit")
    @Expose
    private String erlzeit;
    @SerializedName("Release")
    @Expose
    private String release;
    @SerializedName("Complete")
    @Expose
    private String complete;
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("UserStatus")
    @Expose
    private String userStatus;
    @SerializedName("SysStatus")
    @Expose
    private String sysStatus;
    @SerializedName("Smsttxt")
    @Expose
    private String smsttxt;
    @SerializedName("Smastxt")
    @Expose
    private String smastxt;
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
    @SerializedName("ItNotfTaskFields")
    @Expose
    private List<Model_CustomInfo> itNotfTaskFields = null;

    public List<Model_CustomInfo> getItNotfTaskFields() {
        return itNotfTaskFields;
    }

    public void setItNotfTaskFields(List<Model_CustomInfo> itNotfTaskFields) {
        this.itNotfTaskFields = itNotfTaskFields;
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

    public String getItemdefectShtx() {
        return itemdefectShtx;
    }

    public void setItemdefectShtx(String itemdefectShtx) {
        this.itemdefectShtx = itemdefectShtx;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskGrp() {
        return taskGrp;
    }

    public void setTaskGrp(String taskGrp) {
        this.taskGrp = taskGrp;
    }

    public String getTaskgrptext() {
        return taskgrptext;
    }

    public void setTaskgrptext(String taskgrptext) {
        this.taskgrptext = taskgrptext;
    }

    public String getTaskCod() {
        return taskCod;
    }

    public void setTaskCod(String taskCod) {
        this.taskCod = taskCod;
    }

    public String getTaskcodetext() {
        return taskcodetext;
    }

    public void setTaskcodetext(String taskcodetext) {
        this.taskcodetext = taskcodetext;
    }

    public String getTaskShtxt() {
        return taskShtxt;
    }

    public void setTaskShtxt(String taskShtxt) {
        this.taskShtxt = taskShtxt;
    }

    public String getPster() {
        return pster;
    }

    public void setPster(String pster) {
        this.pster = pster;
    }

    public String getPeter() {
        return peter;
    }

    public void setPeter(String peter) {
        this.peter = peter;
    }

    public String getPstur() {
        return pstur;
    }

    public void setPstur(String pstur) {
        this.pstur = pstur;
    }

    public String getPetur() {
        return petur;
    }

    public void setPetur(String petur) {
        this.petur = petur;
    }

    public String getParvw() {
        return parvw;
    }

    public void setParvw(String parvw) {
        this.parvw = parvw;
    }

    public String getParnr() {
        return parnr;
    }

    public void setParnr(String parnr) {
        this.parnr = parnr;
    }

    public String getErlnam() {
        return erlnam;
    }

    public void setErlnam(String erlnam) {
        this.erlnam = erlnam;
    }

    public String getErldat() {
        return erldat;
    }

    public void setErldat(String erldat) {
        this.erldat = erldat;
    }

    public String getErlzeit() {
        return erlzeit;
    }

    public void setErlzeit(String erlzeit) {
        this.erlzeit = erlzeit;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus;
    }

    public String getSmsttxt() {
        return smsttxt;
    }

    public void setSmsttxt(String smsttxt) {
        this.smsttxt = smsttxt;
    }

    public String getSmastxt() {
        return smastxt;
    }

    public void setSmastxt(String smastxt) {
        this.smastxt = smastxt;
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