package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;

import java.util.ArrayList;

public class OrdrHeaderPrcbl implements Parcelable {

    private String ordrId;
    private String ordrUUId;
    private String ordrStatus;
    private String statusALL;
    private String ordrTypId;
    private String ordrTypName;
    private String opera;
    private String ordrShrtTxt;
    private String ordrLngTxt;
    private String notifId;
    private String funcLocId;
    private String funcLocName;
    private String equipNum;
    private String equipName;
    private String priorityId;
    private String priorityTxt;
    private String plnrGrpId;
    private String plnrGrpName;
    private String perRespId;
    private String perRespName;
    private String basicStart;
    private String basicEnd;
    private String respCostCntrId;
    private String respCostCntrName;
    private String sysCondId;
    private String sysCondName;
    private String wrkCntrId;
    private String plant;
    private String plantName;
    private String wrkCntrName;
    private String iwerk;
    private String posid;
    private String revnr;
    private String bukrs;
    private String activitytype_id;
    private String activitytype_text;
    private ArrayList<OrdrOprtnPrcbl> ordrOprtnPrcbls;
    private ArrayList<OrdrObjectPrcbl> ordrObjectPrcbls;
    private ArrayList<OrdrMatrlPrcbl> ordrMatrlPrcbls;
    private ArrayList<NotifOrdrStatusPrcbl> ordrStatusPrcbls;
    private ArrayList<OrdrPermitPrcbl> ordrPermitPrcbls;
    private ArrayList<Notif_EtDocs_Parcelable> ordrDocsPrcbls;

    public String getOrdrId() {
        return ordrId;
    }

    public void setOrdrId(String ordrId) {
        this.ordrId = ordrId;
    }

    public String getOrdrUUId() {
        return ordrUUId;
    }

    public void setOrdrUUId(String ordrUUId) {
        this.ordrUUId = ordrUUId;
    }

    public String getOrdrStatus() {
        return ordrStatus;
    }

    public void setOrdrStatus(String ordrStatus) {
        this.ordrStatus = ordrStatus;
    }

    public String getStatusALL() {
        return statusALL;
    }

    public void setStatusALL(String statusALL) {
        this.statusALL = statusALL;
    }

    public String getOrdrTypId() {
        return ordrTypId;
    }

    public void setOrdrTypId(String ordrTypId) {
        this.ordrTypId = ordrTypId;
    }

    public String getOrdrTypName() {
        return ordrTypName;
    }

    public void setOrdrTypName(String ordrTypName) {
        this.ordrTypName = ordrTypName;
    }

    public String getOpera() {
        return opera;
    }

    public void setOpera(String opera) {
        this.opera = opera;
    }

    public String getOrdrShrtTxt() {
        return ordrShrtTxt;
    }

    public void setOrdrShrtTxt(String ordrShrtTxt) {
        this.ordrShrtTxt = ordrShrtTxt;
    }

    public String getOrdrLngTxt() {
        return ordrLngTxt;
    }

    public void setOrdrLngTxt(String ordrLngTxt) {
        this.ordrLngTxt = ordrLngTxt;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getFuncLocId() {
        return funcLocId;
    }

    public void setFuncLocId(String funcLocId) {
        this.funcLocId = funcLocId;
    }

    public String getFuncLocName() {
        return funcLocName;
    }

    public void setFuncLocName(String funcLocName) {
        this.funcLocName = funcLocName;
    }

    public String getEquipNum() {
        return equipNum;
    }

    public void setEquipNum(String equipNum) {
        this.equipNum = equipNum;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityTxt() {
        return priorityTxt;
    }

    public void setPriorityTxt(String priorityTxt) {
        this.priorityTxt = priorityTxt;
    }

    public String getPlnrGrpId() {
        return plnrGrpId;
    }

    public void setPlnrGrpId(String plnrGrpId) {
        this.plnrGrpId = plnrGrpId;
    }

    public String getPlnrGrpName() {
        return plnrGrpName;
    }

    public void setPlnrGrpName(String plnrGrpName) {
        this.plnrGrpName = plnrGrpName;
    }

    public String getPerRespId() {
        return perRespId;
    }

    public void setPerRespId(String perRespId) {
        this.perRespId = perRespId;
    }

    public String getPerRespName() {
        return perRespName;
    }

    public void setPerRespName(String perRespName) {
        this.perRespName = perRespName;
    }

    public String getBasicStart() {
        return basicStart;
    }

    public void setBasicStart(String basicStart) {
        this.basicStart = basicStart;
    }

    public String getBasicEnd() {
        return basicEnd;
    }

    public void setBasicEnd(String basicEnd) {
        this.basicEnd = basicEnd;
    }

    public String getRespCostCntrId() {
        return respCostCntrId;
    }

    public void setRespCostCntrId(String respCostCntrId) {
        this.respCostCntrId = respCostCntrId;
    }

    public String getRespCostCntrName() {
        return respCostCntrName;
    }

    public void setRespCostCntrName(String respCostCntrName) {
        this.respCostCntrName = respCostCntrName;
    }

    public String getSysCondId() {
        return sysCondId;
    }

    public void setSysCondId(String sysCondId) {
        this.sysCondId = sysCondId;
    }

    public String getSysCondName() {
        return sysCondName;
    }

    public void setSysCondName(String sysCondName) {
        this.sysCondName = sysCondName;
    }

    public String getWrkCntrId() {
        return wrkCntrId;
    }

    public void setWrkCntrId(String wrkCntrId) {
        this.wrkCntrId = wrkCntrId;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getWrkCntrName() {
        return wrkCntrName;
    }

    public void setWrkCntrName(String wrkCntrName) {
        this.wrkCntrName = wrkCntrName;
    }

    public String getIwerk() {
        return iwerk;
    }

    public void setIwerk(String iwerk) {
        this.iwerk = iwerk;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getRevnr() {
        return revnr;
    }

    public void setRevnr(String revnr) {
        this.revnr = revnr;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getActivitytype_id() {
        return activitytype_id;
    }

    public void setActivitytype_id(String activitytype_id) {
        this.activitytype_id = activitytype_id;
    }

    public String getActivitytype_text() {
        return activitytype_text;
    }

    public void setActivitytype_text(String activitytype_text) {
        this.activitytype_text = activitytype_text;
    }

    public ArrayList<OrdrOprtnPrcbl> getOrdrOprtnPrcbls() {
        return ordrOprtnPrcbls;
    }

    public void setOrdrOprtnPrcbls(ArrayList<OrdrOprtnPrcbl> ordrOprtnPrcbls) {
        this.ordrOprtnPrcbls = ordrOprtnPrcbls;
    }

    public ArrayList<OrdrObjectPrcbl> getOrdrObjectPrcbls() {
        return ordrObjectPrcbls;
    }

    public void setOrdrObjectPrcbls(ArrayList<OrdrObjectPrcbl> ordrObjectPrcbls) {
        this.ordrObjectPrcbls = ordrObjectPrcbls;
    }

    public ArrayList<OrdrMatrlPrcbl> getOrdrMatrlPrcbls() {
        return ordrMatrlPrcbls;
    }

    public void setOrdrMatrlPrcbls(ArrayList<OrdrMatrlPrcbl> ordrMatrlPrcbls) {
        this.ordrMatrlPrcbls = ordrMatrlPrcbls;
    }

    public ArrayList<NotifOrdrStatusPrcbl> getOrdrStatusPrcbls() {
        return ordrStatusPrcbls;
    }

    public void setOrdrStatusPrcbls(ArrayList<NotifOrdrStatusPrcbl> ordrStatusPrcbls) {
        this.ordrStatusPrcbls = ordrStatusPrcbls;
    }

    public ArrayList<OrdrPermitPrcbl> getOrdrPermitPrcbls() {
        return ordrPermitPrcbls;
    }

    public void setOrdrPermitPrcbls(ArrayList<OrdrPermitPrcbl> ordrPermitPrcbls) {
        this.ordrPermitPrcbls = ordrPermitPrcbls;
    }

    public ArrayList<Notif_EtDocs_Parcelable> getOrdrDocsPrcbls() {
        return ordrDocsPrcbls;
    }

    public void setOrdrDocsPrcbls(ArrayList<Notif_EtDocs_Parcelable> ordrDocsPrcbls) {
        this.ordrDocsPrcbls = ordrDocsPrcbls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ordrId);
        dest.writeString(this.ordrUUId);
        dest.writeString(this.ordrStatus);
        dest.writeString(this.statusALL);
        dest.writeString(this.ordrTypId);
        dest.writeString(this.ordrTypName);
        dest.writeString(this.opera);
        dest.writeString(this.ordrShrtTxt);
        dest.writeString(this.ordrLngTxt);
        dest.writeString(this.notifId);
        dest.writeString(this.funcLocId);
        dest.writeString(this.funcLocName);
        dest.writeString(this.equipNum);
        dest.writeString(this.equipName);
        dest.writeString(this.priorityId);
        dest.writeString(this.priorityTxt);
        dest.writeString(this.plnrGrpId);
        dest.writeString(this.plnrGrpName);
        dest.writeString(this.perRespId);
        dest.writeString(this.perRespName);
        dest.writeString(this.basicStart);
        dest.writeString(this.basicEnd);
        dest.writeString(this.respCostCntrId);
        dest.writeString(this.respCostCntrName);
        dest.writeString(this.sysCondId);
        dest.writeString(this.sysCondName);
        dest.writeString(this.wrkCntrId);
        dest.writeString(this.plant);
        dest.writeString(this.plantName);
        dest.writeString(this.wrkCntrName);
        dest.writeString(this.iwerk);
        dest.writeString(this.posid);
        dest.writeString(this.revnr);
        dest.writeString(this.bukrs);
        dest.writeString(this.activitytype_id);
        dest.writeString(this.activitytype_text);
        dest.writeTypedList(this.ordrOprtnPrcbls);
        dest.writeTypedList(this.ordrObjectPrcbls);
        dest.writeTypedList(this.ordrMatrlPrcbls);
        dest.writeTypedList(this.ordrStatusPrcbls);
        dest.writeTypedList(this.ordrPermitPrcbls);
        dest.writeTypedList(this.ordrDocsPrcbls);
    }

    public OrdrHeaderPrcbl() {
    }

    protected OrdrHeaderPrcbl(Parcel in) {
        this.ordrId = in.readString();
        this.ordrUUId = in.readString();
        this.ordrStatus = in.readString();
        this.statusALL = in.readString();
        this.ordrTypId = in.readString();
        this.ordrTypName = in.readString();
        this.opera = in.readString();
        this.ordrShrtTxt = in.readString();
        this.ordrLngTxt = in.readString();
        this.notifId = in.readString();
        this.funcLocId = in.readString();
        this.funcLocName = in.readString();
        this.equipNum = in.readString();
        this.equipName = in.readString();
        this.priorityId = in.readString();
        this.priorityTxt = in.readString();
        this.plnrGrpId = in.readString();
        this.plnrGrpName = in.readString();
        this.perRespId = in.readString();
        this.perRespName = in.readString();
        this.basicStart = in.readString();
        this.basicEnd = in.readString();
        this.respCostCntrId = in.readString();
        this.respCostCntrName = in.readString();
        this.sysCondId = in.readString();
        this.sysCondName = in.readString();
        this.wrkCntrId = in.readString();
        this.plant = in.readString();
        this.plantName = in.readString();
        this.wrkCntrName = in.readString();
        this.iwerk = in.readString();
        this.posid = in.readString();
        this.revnr = in.readString();
        this.bukrs = in.readString();
        this.activitytype_id = in.readString();
        this.activitytype_text = in.readString();
        this.ordrOprtnPrcbls = in.createTypedArrayList(OrdrOprtnPrcbl.CREATOR);
        this.ordrObjectPrcbls = in.createTypedArrayList(OrdrObjectPrcbl.CREATOR);
        this.ordrMatrlPrcbls = in.createTypedArrayList(OrdrMatrlPrcbl.CREATOR);
        this.ordrStatusPrcbls = in.createTypedArrayList(NotifOrdrStatusPrcbl.CREATOR);
        this.ordrPermitPrcbls = in.createTypedArrayList(OrdrPermitPrcbl.CREATOR);
        this.ordrDocsPrcbls = in.createTypedArrayList(Notif_EtDocs_Parcelable.CREATOR);
    }

    public static final Creator<OrdrHeaderPrcbl> CREATOR = new Creator<OrdrHeaderPrcbl>() {
        @Override
        public OrdrHeaderPrcbl createFromParcel(Parcel source) {
            return new OrdrHeaderPrcbl(source);
        }

        @Override
        public OrdrHeaderPrcbl[] newArray(int size) {
            return new OrdrHeaderPrcbl[size];
        }
    };
}
