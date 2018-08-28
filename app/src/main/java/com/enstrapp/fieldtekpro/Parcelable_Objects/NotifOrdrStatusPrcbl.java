package com.enstrapp.fieldtekpro.Parcelable_Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class NotifOrdrStatusPrcbl implements Parcelable{

    private boolean selected;
    private String UUID;
    private String Qmnum;
    private String Aufnr;
    private String Vornr;
    private String Objnr;
    private String Manum;
    private String Stsma;
    private String Inist;
    private String Stonr;
    private String Hsonr;
    private String Nsonr;
    private String Stat;
    private String Act;
    private String Txt04;
    private String Txt30;
    private String Action;

    public NotifOrdrStatusPrcbl() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getQmnum() {
        return Qmnum;
    }

    public void setQmnum(String qmnum) {
        Qmnum = qmnum;
    }

    public String getManum() {
        return Manum;
    }

    public void setManum(String manum) {
        Manum = manum;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getAufnr() {
        return Aufnr;
    }

    public void setAufnr(String aufnr) {
        Aufnr = aufnr;
    }

    public String getVornr() {
        return Vornr;
    }

    public void setVornr(String vornr) {
        Vornr = vornr;
    }

    public String getObjnr() {
        return Objnr;
    }

    public void setObjnr(String objnr) {
        Objnr = objnr;
    }

    public String getStsma() {
        return Stsma;
    }

    public void setStsma(String stsma) {
        Stsma = stsma;
    }

    public String getInist() {
        return Inist;
    }

    public void setInist(String inist) {
        Inist = inist;
    }

    public String getStonr() {
        return Stonr;
    }

    public void setStonr(String stonr) {
        Stonr = stonr;
    }

    public String getHsonr() {
        return Hsonr;
    }

    public void setHsonr(String hsonr) {
        Hsonr = hsonr;
    }

    public String getNsonr() {
        return Nsonr;
    }

    public void setNsonr(String nsonr) {
        Nsonr = nsonr;
    }

    public String getStat() {
        return Stat;
    }

    public void setStat(String stat) {
        Stat = stat;
    }

    public String getAct() {
        return Act;
    }

    public void setAct(String act) {
        Act = act;
    }

    public String getTxt04() {
        return Txt04;
    }

    public void setTxt04(String txt04) {
        Txt04 = txt04;
    }

    public String getTxt30() {
        return Txt30;
    }

    public void setTxt30(String txt30) {
        Txt30 = txt30;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.UUID);
        dest.writeString(this.Qmnum);
        dest.writeString(this.Aufnr);
        dest.writeString(this.Vornr);
        dest.writeString(this.Objnr);
        dest.writeString(this.Manum);
        dest.writeString(this.Stsma);
        dest.writeString(this.Inist);
        dest.writeString(this.Stonr);
        dest.writeString(this.Hsonr);
        dest.writeString(this.Nsonr);
        dest.writeString(this.Stat);
        dest.writeString(this.Act);
        dest.writeString(this.Txt04);
        dest.writeString(this.Txt30);
        dest.writeString(this.Action);
    }

    protected NotifOrdrStatusPrcbl(Parcel in) {
        this.selected = in.readByte() != 0;
        this.UUID = in.readString();
        this.Qmnum = in.readString();
        this.Aufnr = in.readString();
        this.Vornr = in.readString();
        this.Objnr = in.readString();
        this.Manum = in.readString();
        this.Stsma = in.readString();
        this.Inist = in.readString();
        this.Stonr = in.readString();
        this.Hsonr = in.readString();
        this.Nsonr = in.readString();
        this.Stat = in.readString();
        this.Act = in.readString();
        this.Txt04 = in.readString();
        this.Txt30 = in.readString();
        this.Action = in.readString();
    }

    public static final Creator<NotifOrdrStatusPrcbl> CREATOR = new Creator<NotifOrdrStatusPrcbl>() {
        @Override
        public NotifOrdrStatusPrcbl createFromParcel(Parcel source) {
            return new NotifOrdrStatusPrcbl(source);
        }

        @Override
        public NotifOrdrStatusPrcbl[] newArray(int size) {
            return new NotifOrdrStatusPrcbl[size];
        }
    };
}
