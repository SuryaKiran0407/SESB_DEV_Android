package com.enstrapp.fieldtekpro.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NotifHeaderPrcbl implements Parcelable {

    private String UUID;
    private String Notf_Status;
    private String NotifType;
    private String Qmnum;
    private String NotifShrtTxt;
    private String NotifLngTxt;
    private String FuncLoc;
    private String Equip;
    private String Bautl;
    private String ReportedBy;
    private String MalfuncStDt;
    private String MalfuncEdDt;
    private String MalfuncSttm;
    private String MalfuncEdtm;
    private String BrkDwnInd;
    private String Priority;
    private String Ingrp;
    private String Arbpl;
    private String Werks;
    private String Strmn;
    private String Ltrmn;
    private String Aufnr;
    private String Docs;
    private String Altitude;
    private String Latitude;
    private String Longitude;
    private String Closed;
    private String Completed;
    private String CreatedOn;
    private String Qmartx;
    private String Pltxt;
    private String EquipTxt;
    private String PriorityTxt;
    private String AufTxt;
    private String AuartTxt;
    private String PlantTxt;
    private String WrkCntrTxt;
    private String IngrpName;
    private String Maktx;
    private String XStatus;
    private String Usr01;
    private String Usr02;
    private String Usr03;
    private String Usr04;
    private String Usr05;
    private String Status;
    private String ParnrVw;
    private String NameVw;
    private String Auswk;
    private String Shift;
    private String NoOfPerson;
    private String Auswkt;
    private String Strur;
    private String Ltrur;
    private String Qmdat;
    private String Iwerk;
    private ArrayList<NotifCausCodActvPrcbl> causCodPrcblAl;
    private ArrayList<NotifCausCodActvPrcbl> actvPrcblAl;
    private ArrayList<Notif_EtDocs_Parcelable> etDocsParcelables;
    private ArrayList<Notif_Status_WithNum_Prcbl> status_withNum_prcbls;
    private ArrayList<Notif_Status_WithNum_Prcbl> status_withoutNum_prcbls;
    private ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatus_prcbls;
    private ArrayList<NotifTaskPrcbl> notifTaskPrcbls;

    public String getIwerk() {
        return Iwerk;
    }

    public void setIwerk(String iwerk) {
        Iwerk = iwerk;
    }

    public ArrayList<NotifTaskPrcbl> getNotifTaskPrcbls() {
        return notifTaskPrcbls;
    }

    public void setNotifTaskPrcbls(ArrayList<NotifTaskPrcbl> notifTaskPrcbls) {
        this.notifTaskPrcbls = notifTaskPrcbls;
    }

    public NotifHeaderPrcbl() {
    }

    public ArrayList<Notif_Status_WithNum_Prcbl> getStatus_systemstatus_prcbls() {
        return status_systemstatus_prcbls;
    }
    public void setStatus_systemstatus_prcbls(ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatus_prcbls) {
        this.status_systemstatus_prcbls = status_systemstatus_prcbls;
    }
    public ArrayList<Notif_Status_WithNum_Prcbl> getStatus_withoutNum_prcbls() {
        return status_withoutNum_prcbls;
    }
    public void setStatus_withoutNum_prcbls(ArrayList<Notif_Status_WithNum_Prcbl> status_withoutNum_prcbls) {
        this.status_withoutNum_prcbls = status_withoutNum_prcbls;
    }
    public ArrayList<Notif_Status_WithNum_Prcbl> getStatus_withNum_prcbls() {
        return status_withNum_prcbls;
    }
    public void setStatus_withNum_prcbls(ArrayList<Notif_Status_WithNum_Prcbl> status_withNum_prcbls) {
        this.status_withNum_prcbls = status_withNum_prcbls;
    }
    public ArrayList<Notif_EtDocs_Parcelable> getEtDocsParcelables() {
        return etDocsParcelables;
    }
    public void setEtDocsParcelables(ArrayList<Notif_EtDocs_Parcelable> etDocsParcelables) {
        this.etDocsParcelables = etDocsParcelables;
    }
    public String getNotf_Status() {
        return Notf_Status;
    }
    public void setNotf_Status(String notf_Status) {
        Notf_Status = notf_Status;
    }
    public String getAuswk() {
        return Auswk;
    }

    public void setAuswk(String auswk) {
        Auswk = auswk;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getNoOfPerson() {
        return NoOfPerson;
    }

    public void setNoOfPerson(String noOfPerson) {
        NoOfPerson = noOfPerson;
    }

    public String getNotifLngTxt() {
        return NotifLngTxt;
    }

    public void setNotifLngTxt(String notifLngTxt) {
        NotifLngTxt = notifLngTxt;
    }

    public ArrayList<NotifCausCodActvPrcbl> getCausCodPrcblAl() {
        return causCodPrcblAl;
    }

    public void setCausCodPrcblAl(ArrayList<NotifCausCodActvPrcbl> causCodPrcblAl) {
        this.causCodPrcblAl = causCodPrcblAl;
    }

    public ArrayList<NotifCausCodActvPrcbl> getActvPrcblAl() {
        return actvPrcblAl;
    }

    public void setActvPrcblAl(ArrayList<NotifCausCodActvPrcbl> actvPrcblAl) {
        this.actvPrcblAl = actvPrcblAl;
    }

    public String getUUID() {

        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getNotifType() {
        return NotifType;
    }

    public void setNotifType(String notifType) {
        NotifType = notifType;
    }

    public String getQmnum() {
        return Qmnum;
    }

    public void setQmnum(String qmnum) {
        Qmnum = qmnum;
    }

    public String getNotifShrtTxt() {
        return NotifShrtTxt;
    }

    public void setNotifShrtTxt(String notifShrtTxt) {
        NotifShrtTxt = notifShrtTxt;
    }

    public String getFuncLoc() {
        return FuncLoc;
    }

    public void setFuncLoc(String funcLoc) {
        FuncLoc = funcLoc;
    }

    public String getEquip() {
        return Equip;
    }

    public void setEquip(String equip) {
        Equip = equip;
    }

    public String getBautl() {
        return Bautl;
    }

    public void setBautl(String bautl) {
        Bautl = bautl;
    }

    public String getReportedBy() {
        return ReportedBy;
    }

    public void setReportedBy(String reportedBy) {
        ReportedBy = reportedBy;
    }

    public String getMalfuncStDt() {
        return MalfuncStDt;
    }

    public void setMalfuncStDt(String malfuncStDt) {
        MalfuncStDt = malfuncStDt;
    }

    public String getMalfuncEdDt() {
        return MalfuncEdDt;
    }

    public void setMalfuncEdDt(String malfuncEdDt) {
        MalfuncEdDt = malfuncEdDt;
    }

    public String getMalfuncSttm() {
        return MalfuncSttm;
    }

    public void setMalfuncSttm(String malfuncSttm) {
        MalfuncSttm = malfuncSttm;
    }

    public String getMalfuncEdtm() {
        return MalfuncEdtm;
    }

    public void setMalfuncEdtm(String malfuncEdtm) {
        MalfuncEdtm = malfuncEdtm;
    }

    public String getBrkDwnInd() {
        return BrkDwnInd;
    }

    public void setBrkDwnInd(String brkDwnInd) {
        BrkDwnInd = brkDwnInd;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getIngrp() {
        return Ingrp;
    }

    public void setIngrp(String ingrp) {
        Ingrp = ingrp;
    }

    public String getArbpl() {
        return Arbpl;
    }

    public void setArbpl(String arbpl) {
        Arbpl = arbpl;
    }

    public String getWerks() {
        return Werks;
    }

    public void setWerks(String werks) {
        Werks = werks;
    }

    public String getStrmn() {
        return Strmn;
    }

    public void setStrmn(String strmn) {
        Strmn = strmn;
    }

    public String getLtrmn() {
        return Ltrmn;
    }

    public void setLtrmn(String ltrmn) {
        Ltrmn = ltrmn;
    }

    public String getAufnr() {
        return Aufnr;
    }

    public void setAufnr(String aufnr) {
        Aufnr = aufnr;
    }

    public String getDocs() {
        return Docs;
    }

    public void setDocs(String docs) {
        Docs = docs;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getClosed() {
        return Closed;
    }

    public void setClosed(String closed) {
        Closed = closed;
    }

    public String getCompleted() {
        return Completed;
    }

    public void setCompleted(String completed) {
        Completed = completed;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getQmartx() {
        return Qmartx;
    }

    public void setQmartx(String qmartx) {
        Qmartx = qmartx;
    }

    public String getPltxt() {
        return Pltxt;
    }

    public void setPltxt(String pltxt) {
        Pltxt = pltxt;
    }

    public String getEquipTxt() {
        return EquipTxt;
    }

    public void setEquipTxt(String equipTxt) {
        EquipTxt = equipTxt;
    }

    public String getPriorityTxt() {
        return PriorityTxt;
    }

    public void setPriorityTxt(String priorityTxt) {
        PriorityTxt = priorityTxt;
    }

    public String getAufTxt() {
        return AufTxt;
    }

    public void setAufTxt(String aufTxt) {
        AufTxt = aufTxt;
    }

    public String getAuartTxt() {
        return AuartTxt;
    }

    public void setAuartTxt(String auartTxt) {
        AuartTxt = auartTxt;
    }

    public String getPlantTxt() {
        return PlantTxt;
    }

    public void setPlantTxt(String plantTxt) {
        PlantTxt = plantTxt;
    }

    public String getWrkCntrTxt() {
        return WrkCntrTxt;
    }

    public void setWrkCntrTxt(String wrkCntrTxt) {
        WrkCntrTxt = wrkCntrTxt;
    }

    public String getIngrpName() {
        return IngrpName;
    }

    public void setIngrpName(String ingrpName) {
        IngrpName = ingrpName;
    }

    public String getMaktx() {
        return Maktx;
    }

    public void setMaktx(String maktx) {
        Maktx = maktx;
    }

    public String getXStatus() {
        return XStatus;
    }

    public void setXStatus(String XStatus) {
        this.XStatus = XStatus;
    }

    public String getUsr01() {
        return Usr01;
    }

    public void setUsr01(String usr01) {
        Usr01 = usr01;
    }

    public String getUsr02() {
        return Usr02;
    }

    public void setUsr02(String usr02) {
        Usr02 = usr02;
    }

    public String getUsr03() {
        return Usr03;
    }

    public void setUsr03(String usr03) {
        Usr03 = usr03;
    }

    public String getUsr04() {
        return Usr04;
    }

    public void setUsr04(String usr04) {
        Usr04 = usr04;
    }

    public String getUsr05() {
        return Usr05;
    }

    public void setUsr05(String usr05) {
        Usr05 = usr05;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getParnrVw() {
        return ParnrVw;
    }

    public void setParnrVw(String parnrVw) {
        ParnrVw = parnrVw;
    }

    public String getNameVw() {
        return NameVw;
    }

    public void setNameVw(String nameVw) {
        NameVw = nameVw;
    }

    public String getAuswkt() {
        return Auswkt;
    }

    public void setAuswkt(String auswkt) {
        Auswkt = auswkt;
    }

    public String getStrur() {
        return Strur;
    }

    public void setStrur(String strur) {
        Strur = strur;
    }

    public String getLtrur() {
        return Ltrur;
    }

    public void setLtrur(String ltrur) {
        Ltrur = ltrur;
    }

    public String getQmdat() {
        return Qmdat;
    }

    public void setQmdat(String qmdat) {
        Qmdat = qmdat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UUID);
        dest.writeString(this.Notf_Status);
        dest.writeString(this.NotifType);
        dest.writeString(this.Qmnum);
        dest.writeString(this.NotifShrtTxt);
        dest.writeString(this.NotifLngTxt);
        dest.writeString(this.FuncLoc);
        dest.writeString(this.Equip);
        dest.writeString(this.Bautl);
        dest.writeString(this.ReportedBy);
        dest.writeString(this.MalfuncStDt);
        dest.writeString(this.MalfuncEdDt);
        dest.writeString(this.MalfuncSttm);
        dest.writeString(this.MalfuncEdtm);
        dest.writeString(this.BrkDwnInd);
        dest.writeString(this.Priority);
        dest.writeString(this.Ingrp);
        dest.writeString(this.Arbpl);
        dest.writeString(this.Werks);
        dest.writeString(this.Strmn);
        dest.writeString(this.Ltrmn);
        dest.writeString(this.Aufnr);
        dest.writeString(this.Docs);
        dest.writeString(this.Altitude);
        dest.writeString(this.Latitude);
        dest.writeString(this.Longitude);
        dest.writeString(this.Closed);
        dest.writeString(this.Completed);
        dest.writeString(this.CreatedOn);
        dest.writeString(this.Qmartx);
        dest.writeString(this.Pltxt);
        dest.writeString(this.EquipTxt);
        dest.writeString(this.PriorityTxt);
        dest.writeString(this.AufTxt);
        dest.writeString(this.AuartTxt);
        dest.writeString(this.PlantTxt);
        dest.writeString(this.WrkCntrTxt);
        dest.writeString(this.IngrpName);
        dest.writeString(this.Maktx);
        dest.writeString(this.XStatus);
        dest.writeString(this.Usr01);
        dest.writeString(this.Usr02);
        dest.writeString(this.Usr03);
        dest.writeString(this.Usr04);
        dest.writeString(this.Usr05);
        dest.writeString(this.Status);
        dest.writeString(this.ParnrVw);
        dest.writeString(this.NameVw);
        dest.writeString(this.Auswk);
        dest.writeString(this.Shift);
        dest.writeString(this.NoOfPerson);
        dest.writeString(this.Auswkt);
        dest.writeString(this.Strur);
        dest.writeString(this.Ltrur);
        dest.writeString(this.Qmdat);
        dest.writeString(this.Iwerk);
        dest.writeTypedList(this.causCodPrcblAl);
        dest.writeTypedList(this.actvPrcblAl);
        dest.writeTypedList(this.etDocsParcelables);
        dest.writeTypedList(this.status_withNum_prcbls);
        dest.writeTypedList(this.status_withoutNum_prcbls);
        dest.writeTypedList(this.status_systemstatus_prcbls);
        dest.writeTypedList(this.notifTaskPrcbls);
    }

    protected NotifHeaderPrcbl(Parcel in) {
        this.UUID = in.readString();
        this.Notf_Status = in.readString();
        this.NotifType = in.readString();
        this.Qmnum = in.readString();
        this.NotifShrtTxt = in.readString();
        this.NotifLngTxt = in.readString();
        this.FuncLoc = in.readString();
        this.Equip = in.readString();
        this.Bautl = in.readString();
        this.ReportedBy = in.readString();
        this.MalfuncStDt = in.readString();
        this.MalfuncEdDt = in.readString();
        this.MalfuncSttm = in.readString();
        this.MalfuncEdtm = in.readString();
        this.BrkDwnInd = in.readString();
        this.Priority = in.readString();
        this.Ingrp = in.readString();
        this.Arbpl = in.readString();
        this.Werks = in.readString();
        this.Strmn = in.readString();
        this.Ltrmn = in.readString();
        this.Aufnr = in.readString();
        this.Docs = in.readString();
        this.Altitude = in.readString();
        this.Latitude = in.readString();
        this.Longitude = in.readString();
        this.Closed = in.readString();
        this.Completed = in.readString();
        this.CreatedOn = in.readString();
        this.Qmartx = in.readString();
        this.Pltxt = in.readString();
        this.EquipTxt = in.readString();
        this.PriorityTxt = in.readString();
        this.AufTxt = in.readString();
        this.AuartTxt = in.readString();
        this.PlantTxt = in.readString();
        this.WrkCntrTxt = in.readString();
        this.IngrpName = in.readString();
        this.Maktx = in.readString();
        this.XStatus = in.readString();
        this.Usr01 = in.readString();
        this.Usr02 = in.readString();
        this.Usr03 = in.readString();
        this.Usr04 = in.readString();
        this.Usr05 = in.readString();
        this.Status = in.readString();
        this.ParnrVw = in.readString();
        this.NameVw = in.readString();
        this.Auswk = in.readString();
        this.Shift = in.readString();
        this.NoOfPerson = in.readString();
        this.Auswkt = in.readString();
        this.Strur = in.readString();
        this.Ltrur = in.readString();
        this.Qmdat = in.readString();
        this.Iwerk = in.readString();
        this.causCodPrcblAl = in.createTypedArrayList(NotifCausCodActvPrcbl.CREATOR);
        this.actvPrcblAl = in.createTypedArrayList(NotifCausCodActvPrcbl.CREATOR);
        this.etDocsParcelables = in.createTypedArrayList(Notif_EtDocs_Parcelable.CREATOR);
        this.status_withNum_prcbls = in.createTypedArrayList(Notif_Status_WithNum_Prcbl.CREATOR);
        this.status_withoutNum_prcbls = in.createTypedArrayList(Notif_Status_WithNum_Prcbl.CREATOR);
        this.status_systemstatus_prcbls = in.createTypedArrayList(Notif_Status_WithNum_Prcbl.CREATOR);
        this.notifTaskPrcbls = in.createTypedArrayList(NotifTaskPrcbl.CREATOR);
    }

    public static final Creator<NotifHeaderPrcbl> CREATOR = new Creator<NotifHeaderPrcbl>() {
        @Override
        public NotifHeaderPrcbl createFromParcel(Parcel source) {
            return new NotifHeaderPrcbl(source);
        }

        @Override
        public NotifHeaderPrcbl[] newArray(int size) {
            return new NotifHeaderPrcbl[size];
        }
    };
}
