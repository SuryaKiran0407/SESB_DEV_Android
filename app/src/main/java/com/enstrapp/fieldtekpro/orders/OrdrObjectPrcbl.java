package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdrObjectPrcbl implements Parcelable {

    public String notifNo;

    public String equipId;

    public String equipTxt;

    public String aufnr;
    public String obknr;
    public String obzae;
    public String strno;
    public String tplnr;
    public String bautl;
    public String qmtxt;
    public String pltxt;
    public String maktx;
    public String action;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getObknr() {
        return obknr;
    }

    public void setObknr(String obknr) {
        this.obknr = obknr;
    }

    public String getObzae() {
        return obzae;
    }

    public void setObzae(String obzae) {
        this.obzae = obzae;
    }

    public String getStrno() {
        return strno;
    }

    public void setStrno(String strno) {
        this.strno = strno;
    }

    public String getTplnr() {
        return tplnr;
    }

    public void setTplnr(String tplnr) {
        this.tplnr = tplnr;
    }

    public String getBautl() {
        return bautl;
    }

    public void setBautl(String bautl) {
        this.bautl = bautl;
    }

    public String getQmtxt() {
        return qmtxt;
    }

    public void setQmtxt(String qmtxt) {
        this.qmtxt = qmtxt;
    }

    public String getPltxt() {
        return pltxt;
    }

    public void setPltxt(String pltxt) {
        this.pltxt = pltxt;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNotifNo() {
        return notifNo;
    }

    public void setNotifNo(String notifNo) {
        this.notifNo = notifNo;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getEquipTxt() {
        return equipTxt;
    }

    public void setEquipTxt(String equipTxt) {
        this.equipTxt = equipTxt;
    }

    public OrdrObjectPrcbl() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.notifNo);
        dest.writeString(this.equipId);
        dest.writeString(this.equipTxt);
        dest.writeString(this.aufnr);
        dest.writeString(this.obknr);
        dest.writeString(this.obzae);
        dest.writeString(this.strno);
        dest.writeString(this.tplnr);
        dest.writeString(this.bautl);
        dest.writeString(this.qmtxt);
        dest.writeString(this.pltxt);
        dest.writeString(this.maktx);
        dest.writeString(this.action);
    }

    protected OrdrObjectPrcbl(Parcel in) {
        this.notifNo = in.readString();
        this.equipId = in.readString();
        this.equipTxt = in.readString();
        this.aufnr = in.readString();
        this.obknr = in.readString();
        this.obzae = in.readString();
        this.strno = in.readString();
        this.tplnr = in.readString();
        this.bautl = in.readString();
        this.qmtxt = in.readString();
        this.pltxt = in.readString();
        this.maktx = in.readString();
        this.action = in.readString();
    }

    public static final Creator<OrdrObjectPrcbl> CREATOR = new Creator<OrdrObjectPrcbl>() {
        @Override
        public OrdrObjectPrcbl createFromParcel(Parcel source) {
            return new OrdrObjectPrcbl(source);
        }

        @Override
        public OrdrObjectPrcbl[] newArray(int size) {
            return new OrdrObjectPrcbl[size];
        }
    };
}
