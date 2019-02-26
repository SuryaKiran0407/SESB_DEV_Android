package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notifications_SER {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }


    public class D {
        @SerializedName("results")
        @Expose
        private List<Result> results = null;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }


        /*For Notif Complete*/
        @SerializedName("NotiComplete")
        @Expose
        private NotiComplete NotiComplete;

        public Notifications_SER.NotiComplete getNotiComplete() {
            return NotiComplete;
        }

        public void setNotiComplete(Notifications_SER.NotiComplete notiComplete) {
            NotiComplete = notiComplete;
        }
        /*For Notif Complete*/


        /*For Notif Create*/
        @SerializedName("EvMessage")
        @Expose
        private EvMessage EvMessage;

        public Notifications_SER.EvMessage getEvMessage() {
            return EvMessage;
        }

        public void setEvMessage(Notifications_SER.EvMessage evMessage) {
            EvMessage = evMessage;
        }

        @SerializedName("EtNotifHeader")
        @Expose
        private EtNotifHeader EtNotifHeader;

        public Notifications_SER.EtNotifHeader getEtNotifHeader() {
            return EtNotifHeader;
        }

        public void setEtNotifHeader(Notifications_SER.EtNotifHeader etNotifHeader) {
            EtNotifHeader = etNotifHeader;
        }

        @SerializedName("EtNotifItems")
        @Expose
        private EtNotifItems EtNotifItems;

        public Notifications_SER.EtNotifItems getEtNotifItems() {
            return EtNotifItems;
        }

        public void setEtNotifItems(Notifications_SER.EtNotifItems etNotifItems) {
            EtNotifItems = etNotifItems;
        }

        @SerializedName("EtNotifActvs")
        @Expose
        private EtNotifActvs EtNotifActvs;

        public Notifications_SER.EtNotifActvs getEtNotifActvs() {
            return EtNotifActvs;
        }

        public void setEtNotifActvs(Notifications_SER.EtNotifActvs etNotifActvs) {
            EtNotifActvs = etNotifActvs;
        }

        @SerializedName("EtDocs")
        @Expose
        private EtDocs EtDocs;

        public Notifications_SER.EtDocs getEtDocs() {
            return EtDocs;
        }

        public void setEtDocs(Notifications_SER.EtDocs etDocs) {
            EtDocs = etDocs;
        }

        @SerializedName("EtNotifStatus")
        @Expose
        private EtNotifStatus EtNotifStatus;

        public Notifications_SER.EtNotifStatus getEtNotifStatus() {
            return EtNotifStatus;
        }

        public void setEtNotifStatus(Notifications_SER.EtNotifStatus etNotifStatus) {
            EtNotifStatus = etNotifStatus;
        }

        @SerializedName("EtNotifDup")
        @Expose
        private EtNotifDup EtNotifDup;

        public Notifications_SER.EtNotifDup getEtNotifDup() {
            return EtNotifDup;
        }

        public void setEtNotifDup(Notifications_SER.EtNotifDup etNotifDup) {
            EtNotifDup = etNotifDup;
        }

        @SerializedName("EtNotifLongtext")
        @Expose
        private EtNotifLongtext EtNotifLongtext;

        public Notifications_SER.EtNotifLongtext getEtNotifLongtext() {
            return EtNotifLongtext;
        }

        public void setEtNotifLongtext(Notifications_SER.EtNotifLongtext etNotifLongtext) {
            EtNotifLongtext = etNotifLongtext;
        }

        @SerializedName("EtNotifTasks")
        @Expose
        private EtNotifTasks EtNotifTasks;

        public Notifications_SER.EtNotifTasks getEtNotifTasks() {
            return EtNotifTasks;
        }

        public void setEtNotifTasks(Notifications_SER.EtNotifTasks etNotifTasks) {
            EtNotifTasks = etNotifTasks;
        }
        /*For Notif Create*/
    }


    /*For Parsing EtNotifDup*/
    public class EtNotifDup {
        @SerializedName("results")
        @Expose
        private List<EtNotifDup_Result> results = null;

        public List<EtNotifDup_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifDup_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifDup_Result {
        @SerializedName("Qmart")
        @Expose
        private String qmart;
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;
        @SerializedName("Qmtxt")
        @Expose
        private String qmtxt;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
        @SerializedName("Priok")
        @Expose
        private String priok;
        @SerializedName("Objnr")
        @Expose
        private String objnr;

        public String getQmart() {
            return qmart;
        }

        public void setQmart(String qmart) {
            this.qmart = qmart;
        }

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getQmtxt() {
            return qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            this.qmtxt = qmtxt;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getPriok() {
            return priok;
        }

        public void setPriok(String priok) {
            this.priok = priok;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

    }
    /*For Parsing EtNotifDup_Result*/


    public class NotiComplete {
        @SerializedName("Message")
        @Expose
        private String Message;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }


    public class Result {
        @SerializedName("EvMessage")
        @Expose
        private EvMessage EvMessage;
        @SerializedName("EtMessages")
        @Expose
        private EtMessages EtMessages;
        @SerializedName("EtNotifHeader")
        @Expose
        private EtNotifHeader EtNotifHeader;
        @SerializedName("EtNotifItems")
        @Expose
        private EtNotifItems EtNotifItems;
        @SerializedName("EtNotifActvs")
        @Expose
        private EtNotifActvs EtNotifActvs;
        @SerializedName("EtNotifLongtext")
        @Expose
        private EtNotifLongtext EtNotifLongtext;
        @SerializedName("EtNotifStatus")
        @Expose
        private EtNotifStatus EtNotifStatus;
        @SerializedName("EtDocs")
        @Expose
        private EtDocs EtDocs;
        @SerializedName("EtNotifTasks")
        @Expose
        private EtNotifTasks EtNotifTasks;

        public Notifications_SER.EtMessages getEtMessages() {
            return EtMessages;
        }

        public void setEtMessages(Notifications_SER.EtMessages etMessages) {
            EtMessages = etMessages;
        }

        public Notifications_SER.EtNotifTasks getEtNotifTasks() {
            return EtNotifTasks;
        }

        public void setEtNotifTasks(Notifications_SER.EtNotifTasks etNotifTasks) {
            EtNotifTasks = etNotifTasks;
        }

        public Notifications_SER.EtNotifActvs getEtNotifActvs() {
            return EtNotifActvs;
        }

        public void setEtNotifActvs(Notifications_SER.EtNotifActvs etNotifActvs) {
            EtNotifActvs = etNotifActvs;
        }

        public Notifications_SER.EtNotifHeader getEtNotifHeader() {
            return EtNotifHeader;
        }

        public void setEtNotifHeader(Notifications_SER.EtNotifHeader etNotifHeader) {
            EtNotifHeader = etNotifHeader;
        }

        public Notifications_SER.EvMessage getEvMessage() {
            return EvMessage;
        }

        public void setEvMessage(Notifications_SER.EvMessage evMessage) {
            EvMessage = evMessage;
        }

        public Notifications_SER.EtNotifItems getEtNotifItems() {
            return EtNotifItems;
        }

        public void setEtNotifItems(Notifications_SER.EtNotifItems etNotifItems) {
            EtNotifItems = etNotifItems;
        }

        public Notifications_SER.EtNotifLongtext getEtNotifLongtext() {
            return EtNotifLongtext;
        }

        public void setEtNotifLongtext(Notifications_SER.EtNotifLongtext etNotifLongtext) {
            EtNotifLongtext = etNotifLongtext;
        }

        public Notifications_SER.EtNotifStatus getEtNotifStatus() {
            return EtNotifStatus;
        }

        public void setEtNotifStatus(Notifications_SER.EtNotifStatus etNotifStatus) {
            EtNotifStatus = etNotifStatus;
        }

        public Notifications_SER.EtDocs getEtDocs() {
            return EtDocs;
        }

        public void setEtDocs(Notifications_SER.EtDocs etDocs) {
            EtDocs = etDocs;
        }
    }


    /*For Parsing EtNotifTasks*/
    public class EtNotifTasks {
        @SerializedName("results")
        @Expose
        private List<EtNotifTasks_Result> results = null;

        public List<EtNotifTasks_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifTasks_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifTasks_Result {
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
        @SerializedName("EtNotifTasksFields")
        @Expose
        private CustomFields etCustomFields;

        public CustomFields getEtCustomFields() {
            return etCustomFields;
        }

        public void setEtCustomFields(CustomFields etCustomFields) {
            this.etCustomFields = etCustomFields;
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
    /*For Parsing EtNotifTasks*/


    /*For Parsing EvMessage*/
    public class EvMessage {
        @SerializedName("results")
        @Expose
        private List<EvMessage_Result> results = null;

        public List<EvMessage_Result> getResults() {
            return results;
        }

        public void setResults(List<EvMessage_Result> results) {
            this.results = results;
        }
    }

    public class EvMessage_Result {
        @SerializedName("Message")
        @Expose
        private String Message;
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }
    }
    /*For Parsing EvMessage*/

    /*For Parsing EtMessages*/
    public class EtMessages {
        @SerializedName("results")
        @Expose
        private List<EtMessages_Result> results = null;

        public List<EtMessages_Result> getResults() {
            return results;
        }

        public void setResults(List<EtMessages_Result> results) {
            this.results = results;
        }
    }

    public class EtMessages_Result {
        @SerializedName("Message")
        @Expose
        private String Message;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }
    /*For Parsing EtMessages*/


    /*For Parsing EtNotifHeader*/
    public class EtNotifHeader {
        @SerializedName("results")
        @Expose
        private List<EtNotifHeader_Result> results = null;

        public List<EtNotifHeader_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifHeader_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifHeader_Result {
        public String getNotifType() {
            return notifType;
        }

        public void setNotifType(String notifType) {
            this.notifType = notifType;
        }

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getNotifShorttxt() {
            return notifShorttxt;
        }

        public void setNotifShorttxt(String notifShorttxt) {
            this.notifShorttxt = notifShorttxt;
        }

        public String getFunctionLoc() {
            return functionLoc;
        }

        public void setFunctionLoc(String functionLoc) {
            this.functionLoc = functionLoc;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public String getBautl() {
            return bautl;
        }

        public void setBautl(String bautl) {
            this.bautl = bautl;
        }

        public String getReportedBy() {
            return reportedBy;
        }

        public void setReportedBy(String reportedBy) {
            this.reportedBy = reportedBy;
        }

        public String getMalfuncStdate() {
            return malfuncStdate;
        }

        public void setMalfuncStdate(String malfuncStdate) {
            this.malfuncStdate = malfuncStdate;
        }

        public String getMalfuncEddate() {
            return malfuncEddate;
        }

        public void setMalfuncEddate(String malfuncEddate) {
            this.malfuncEddate = malfuncEddate;
        }

        public String getMalfuncSttime() {
            return malfuncSttime;
        }

        public void setMalfuncSttime(String malfuncSttime) {
            this.malfuncSttime = malfuncSttime;
        }

        public String getMalfuncEdtime() {
            return malfuncEdtime;
        }

        public void setMalfuncEdtime(String malfuncEdtime) {
            this.malfuncEdtime = malfuncEdtime;
        }

        public String getBreakdownInd() {
            return breakdownInd;
        }

        public void setBreakdownInd(String breakdownInd) {
            this.breakdownInd = breakdownInd;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getIngrp() {
            return ingrp;
        }

        public void setIngrp(String ingrp) {
            this.ingrp = ingrp;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getQmdat() {
            return qmdat;
        }

        public void setQmdat(String qmdat) {
            this.qmdat = qmdat;
        }

        public String getStrmn() {
            return strmn;
        }

        public void setStrmn(String strmn) {
            this.strmn = strmn;
        }

        public String getLtrmn() {
            return ltrmn;
        }

        public void setLtrmn(String ltrmn) {
            this.ltrmn = ltrmn;
        }

        public String getStrur() {
            return strur;
        }

        public void setStrur(String strur) {
            this.strur = strur;
        }

        public String getLtrur() {
            return ltrur;
        }

        public void setLtrur(String ltrur) {
            this.ltrur = ltrur;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getParnrVw() {
            return parnrVw;
        }

        public void setParnrVw(String parnrVw) {
            this.parnrVw = parnrVw;
        }

        public String getNameVw() {
            return nameVw;
        }

        public void setNameVw(String nameVw) {
            this.nameVw = nameVw;
        }

        public String getAuswk() {
            return auswk;
        }

        public void setAuswk(String auswk) {
            this.auswk = auswk;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public Integer getNoofperson() {
            return noofperson;
        }

        public void setNoofperson(Integer noofperson) {
            this.noofperson = noofperson;
        }

        public String getDocs() {
            return docs;
        }

        public void setDocs(String docs) {
            this.docs = docs;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getClosed() {
            return closed;
        }

        public void setClosed(String closed) {
            this.closed = closed;
        }

        public String getCompleted() {
            return completed;
        }

        public void setCompleted(String completed) {
            this.completed = completed;
        }

        public String getCreatedon() {
            return createdon;
        }

        public void setCreatedon(String createdon) {
            this.createdon = createdon;
        }

        public String getQmartx() {
            return qmartx;
        }

        public void setQmartx(String qmartx) {
            this.qmartx = qmartx;
        }

        public String getPltxt() {
            return pltxt;
        }

        public void setPltxt(String pltxt) {
            this.pltxt = pltxt;
        }

        public String getEqktx() {
            return eqktx;
        }

        public void setEqktx(String eqktx) {
            this.eqktx = eqktx;
        }

        public String getPriokx() {
            return priokx;
        }

        public void setPriokx(String priokx) {
            this.priokx = priokx;
        }

        public String getAuftext() {
            return auftext;
        }

        public void setAuftext(String auftext) {
            this.auftext = auftext;
        }

        public String getAuarttext() {
            return auarttext;
        }

        public void setAuarttext(String auarttext) {
            this.auarttext = auarttext;
        }

        public String getPlantname() {
            return plantname;
        }

        public void setPlantname(String plantname) {
            this.plantname = plantname;
        }

        public String getWkctrname() {
            return wkctrname;
        }

        public void setWkctrname(String wkctrname) {
            this.wkctrname = wkctrname;
        }

        public String getIngrpname() {
            return ingrpname;
        }

        public void setIngrpname(String ingrpname) {
            this.ingrpname = ingrpname;
        }

        public String getMaktx() {
            return maktx;
        }

        public void setMaktx(String maktx) {
            this.maktx = maktx;
        }

        public String getAuswkt() {
            return auswkt;
        }

        public void setAuswkt(String auswkt) {
            this.auswkt = auswkt;
        }

        public String getXstatus() {
            return xstatus;
        }

        public void setXstatus(String xstatus) {
            this.xstatus = xstatus;
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


        @SerializedName("NotifType")
        @Expose
        private String notifType;
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;
        @SerializedName("NotifShorttxt")
        @Expose
        private String notifShorttxt;
        @SerializedName("FunctionLoc")
        @Expose
        private String functionLoc;
        @SerializedName("Equipment")
        @Expose
        private String equipment;
        @SerializedName("Bautl")
        @Expose
        private String bautl;
        @SerializedName("ReportedBy")
        @Expose
        private String reportedBy;
        @SerializedName("MalfuncStdate")
        @Expose
        private String malfuncStdate;
        @SerializedName("MalfuncEddate")
        @Expose
        private String malfuncEddate;
        @SerializedName("MalfuncSttime")
        @Expose
        private String malfuncSttime;
        @SerializedName("MalfuncEdtime")
        @Expose
        private String malfuncEdtime;
        @SerializedName("BreakdownInd")
        @Expose
        private String breakdownInd;
        @SerializedName("Priority")
        @Expose
        private String priority;
        @SerializedName("Ingrp")
        @Expose
        private String ingrp;
        @SerializedName("Arbpl")
        @Expose
        private String arbpl;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Qmdat")
        @Expose
        private String qmdat;
        @SerializedName("Strmn")
        @Expose
        private String strmn;
        @SerializedName("Ltrmn")
        @Expose
        private String ltrmn;
        @SerializedName("Strur")
        @Expose
        private String strur;
        @SerializedName("Ltrur")
        @Expose
        private String ltrur;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("ParnrVw")
        @Expose
        private String parnrVw;
        @SerializedName("NameVw")
        @Expose
        private String nameVw;
        @SerializedName("Auswk")
        @Expose
        private String auswk;
        @SerializedName("Shift")
        @Expose
        private String shift;
        @SerializedName("Noofperson")
        @Expose
        private Integer noofperson;
        @SerializedName("Docs")
        @Expose
        private String docs;
        @SerializedName("Altitude")
        @Expose
        private String altitude;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;
        @SerializedName("Closed")
        @Expose
        private String closed;
        @SerializedName("Completed")
        @Expose
        private String completed;
        @SerializedName("Createdon")
        @Expose
        private String createdon;
        @SerializedName("Qmartx")
        @Expose
        private String qmartx;
        @SerializedName("Pltxt")
        @Expose
        private String pltxt;
        @SerializedName("Eqktx")
        @Expose
        private String eqktx;
        @SerializedName("Priokx")
        @Expose
        private String priokx;
        @SerializedName("Auftext")
        @Expose
        private String auftext;
        @SerializedName("Auarttext")
        @Expose
        private String auarttext;
        @SerializedName("Plantname")
        @Expose
        private String plantname;
        @SerializedName("Wkctrname")
        @Expose
        private String wkctrname;
        @SerializedName("Ingrpname")
        @Expose
        private String ingrpname;
        @SerializedName("Maktx")
        @Expose
        private String maktx;
        @SerializedName("Auswkt")
        @Expose
        private String auswkt;
        @SerializedName("Xstatus")
        @Expose
        private String xstatus;
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
        @SerializedName("EtNotifHeaderFields")
        @Expose
        private CustomFields etCustomFields;

        public CustomFields getEtCustomFields() {
            return etCustomFields;
        }

        public void setEtCustomFields(CustomFields etCustomFields) {
            this.etCustomFields = etCustomFields;
        }
    }
    /*For Parsing EtNotifHeader*/


    /*For Parsing EtCustomFields*/
    public class CustomFields {
        @SerializedName("results")
        @Expose
        private List<CustomFields_Result> results = null;

        public List<CustomFields_Result> getResults() {
            return results;
        }

        public void setResults(List<CustomFields_Result> results) {
            this.results = results;
        }
    }

    public class CustomFields_Result {
        @SerializedName("Zdoctype")
        @Expose
        private String zdoctype;
        @SerializedName("ZdoctypeItem")
        @Expose
        private String zdoctypeItem;
        @SerializedName("Tabname")
        @Expose
        private String tabname;
        @SerializedName("Fieldname")
        @Expose
        private String fieldname;
        @SerializedName("Datatype")
        @Expose
        private String datatype;
        @SerializedName("Value")
        @Expose
        private String value;
        @SerializedName("Flabel")
        @Expose
        private String flabel;
        @SerializedName("Sequence")
        @Expose
        private String sequence;
        @SerializedName("Length")
        @Expose
        private String length;

        public String getZdoctype() {
            return zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            this.zdoctype = zdoctype;
        }

        public String getZdoctypeItem() {
            return zdoctypeItem;
        }

        public void setZdoctypeItem(String zdoctypeItem) {
            this.zdoctypeItem = zdoctypeItem;
        }

        public String getTabname() {
            return tabname;
        }

        public void setTabname(String tabname) {
            this.tabname = tabname;
        }

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
        }

        public String getDatatype() {
            return datatype;
        }

        public void setDatatype(String datatype) {
            this.datatype = datatype;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getFlabel() {
            return flabel;
        }

        public void setFlabel(String flabel) {
            this.flabel = flabel;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }
    /*For Parsing EtCustomFields*/


    /*For Parsing EtNotifItems*/
    public class EtNotifItems {
        @SerializedName("results")
        @Expose
        private List<EtNotifItems_Result> results = null;

        public List<EtNotifItems_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifItems_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifItems_Result {
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
        @SerializedName("CauseGrp")
        @Expose
        private String causeGrp;
        @SerializedName("Causegrptext")
        @Expose
        private String causegrptext;
        @SerializedName("CauseCod")
        @Expose
        private String causeCod;
        @SerializedName("Causecodetext")
        @Expose
        private String causecodetext;
        @SerializedName("CauseShtxt")
        @Expose
        private String causeShtxt;
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
        @SerializedName("EtNotifItemsFields")
        @Expose
        private CustomFields etCustomFields;


        public CustomFields getEtCustomFields() {
            return etCustomFields;
        }

        public void setEtCustomFields(CustomFields etCustomFields) {
            this.etCustomFields = etCustomFields;
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

        public String getCauseGrp() {
            return causeGrp;
        }

        public void setCauseGrp(String causeGrp) {
            this.causeGrp = causeGrp;
        }

        public String getCausegrptext() {
            return causegrptext;
        }

        public void setCausegrptext(String causegrptext) {
            this.causegrptext = causegrptext;
        }

        public String getCauseCod() {
            return causeCod;
        }

        public void setCauseCod(String causeCod) {
            this.causeCod = causeCod;
        }

        public String getCausecodetext() {
            return causecodetext;
        }

        public void setCausecodetext(String causecodetext) {
            this.causecodetext = causecodetext;
        }

        public String getCauseShtxt() {
            return causeShtxt;
        }

        public void setCauseShtxt(String causeShtxt) {
            this.causeShtxt = causeShtxt;
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
    /*For Parsing EtNotifItems*/


    /*For Parsing EtNotifActvs*/
    public class EtNotifActvs {
        @SerializedName("results")
        @Expose
        private List<EtNotifActvs_Result> results = null;

        public List<EtNotifActvs_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifActvs_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifActvs_Result {
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

        public String getActgrptext() {
            return actgrptext;
        }

        public void setActgrptext(String actgrptext) {
            this.actgrptext = actgrptext;
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

        public CustomFields getEtCustomFields() {
            return etCustomFields;
        }

        public void setEtCustomFields(CustomFields etCustomFields) {
            this.etCustomFields = etCustomFields;
        }

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
        @SerializedName("Actgrptext")
        @Expose
        private String actgrptext;
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
        @SerializedName("EtNotifActvsFields")
        @Expose
        private CustomFields etCustomFields;
    }
    /*For Parsing EtNotifActvs*/


    /*For Parsing EtNotifLongtext*/
    public class EtNotifLongtext {
        @SerializedName("results")
        @Expose
        private List<EtNotifLongtext_Result> results = null;

        public List<EtNotifLongtext_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifLongtext_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifLongtext_Result {
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;
        @SerializedName("Objtype")
        @Expose
        private String objtype;
        @SerializedName("Objkey")
        @Expose
        private String objkey;
        @SerializedName("TextLine")
        @Expose
        private String textLine;

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getObjtype() {
            return objtype;
        }

        public void setObjtype(String objtype) {
            this.objtype = objtype;
        }

        public String getObjkey() {
            return objkey;
        }

        public void setObjkey(String objkey) {
            this.objkey = objkey;
        }

        public String getTextLine() {
            return textLine;
        }

        public void setTextLine(String textLine) {
            this.textLine = textLine;
        }
    }
    /*For Parsing EtNotifLongtext*/


    /*For Parsing EtNotifStatus*/
    public class EtNotifStatus {
        @SerializedName("results")
        @Expose
        private List<EtNotifStatus_Result> results = null;

        public List<EtNotifStatus_Result> getResults() {
            return results;
        }

        public void setResults(List<EtNotifStatus_Result> results) {
            this.results = results;
        }
    }

    public class EtNotifStatus_Result {
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Objnr")
        @Expose
        private String objnr;
        @SerializedName("Manum")
        @Expose
        private String manum;
        @SerializedName("Stsma")
        @Expose
        private String stsma;
        @SerializedName("Inist")
        @Expose
        private String inist;
        @SerializedName("Stonr")
        @Expose
        private String stonr;
        @SerializedName("Hsonr")
        @Expose
        private String hsonr;
        @SerializedName("Nsonr")
        @Expose
        private String nsonr;
        @SerializedName("Stat")
        @Expose
        private String stat;
        @SerializedName("Act")
        @Expose
        private String act;
        @SerializedName("Txt04")
        @Expose
        private String txt04;
        @SerializedName("Txt30")
        @Expose
        private String txt30;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getManum() {
            return manum;
        }

        public void setManum(String manum) {
            this.manum = manum;
        }

        public String getStsma() {
            return stsma;
        }

        public void setStsma(String stsma) {
            this.stsma = stsma;
        }

        public String getInist() {
            return inist;
        }

        public void setInist(String inist) {
            this.inist = inist;
        }

        public String getStonr() {
            return stonr;
        }

        public void setStonr(String stonr) {
            this.stonr = stonr;
        }

        public String getHsonr() {
            return hsonr;
        }

        public void setHsonr(String hsonr) {
            this.hsonr = hsonr;
        }

        public String getNsonr() {
            return nsonr;
        }

        public void setNsonr(String nsonr) {
            this.nsonr = nsonr;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getTxt04() {
            return txt04;
        }

        public void setTxt04(String txt04) {
            this.txt04 = txt04;
        }

        public String getTxt30() {
            return txt30;
        }

        public void setTxt30(String txt30) {
            this.txt30 = txt30;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For Parsing EtNotifStatus*/


    /*For Parsing EtDocs*/
    public class EtDocs {
        @SerializedName("results")
        @Expose
        private List<EtDocs_Result> results = null;

        public List<EtDocs_Result> getResults() {
            return results;
        }

        public void setResults(List<EtDocs_Result> results) {
            this.results = results;
        }
    }

    public class EtDocs_Result {
        @SerializedName("Objtype")
        @Expose
        private String objtype;
        @SerializedName("Zobjid")
        @Expose
        private String zobjid;
        @SerializedName("Zdoctype")
        @Expose
        private String zdoctype;
        @SerializedName("ZdoctypeItem")
        @Expose
        private String zdoctypeItem;
        @SerializedName("Filename")
        @Expose
        private String filename;
        @SerializedName("Filetype")
        @Expose
        private String filetype;
        @SerializedName("Fsize")
        @Expose
        private String fsize;
        @SerializedName("Content")
        @Expose
        private String content;
        @SerializedName("DocId")
        @Expose
        private String docId;
        @SerializedName("DocType")
        @Expose
        private String docType;
        @SerializedName("Contentx")
        @Expose
        private String contentX;

        public String getContentX() {
            return contentX;
        }

        public void setContentX(String contentX) {
            this.contentX = contentX;
        }

        public String getObjtype() {
            return objtype;
        }

        public void setObjtype(String objtype) {
            this.objtype = objtype;
        }

        public String getZobjid() {
            return zobjid;
        }

        public void setZobjid(String zobjid) {
            this.zobjid = zobjid;
        }

        public String getZdoctype() {
            return zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            this.zdoctype = zdoctype;
        }

        public String getZdoctypeItem() {
            return zdoctypeItem;
        }

        public void setZdoctypeItem(String zdoctypeItem) {
            this.zdoctypeItem = zdoctypeItem;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFiletype() {
            return filetype;
        }

        public void setFiletype(String filetype) {
            this.filetype = filetype;
        }

        public String getFsize() {
            return fsize;
        }

        public void setFsize(String fsize) {
            this.fsize = fsize;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

    }
    /*For Parsing EtDocs*/


}