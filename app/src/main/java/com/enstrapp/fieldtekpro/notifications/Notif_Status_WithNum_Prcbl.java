package com.enstrapp.fieldtekpro.notifications;

import android.os.Parcel;
import android.os.Parcelable;

public class Notif_Status_WithNum_Prcbl implements Parcelable {

    private String Qmnum;
    private String Aufnr;
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
    private String Act_Status;
    private String Checked_Status;

    public String getAct_Status() {
        return Act_Status;
    }

    public void setAct_Status(String act_Status) {
        Act_Status = act_Status;
    }

    public String getChecked_Status() {
        return Checked_Status;
    }

    public void setChecked_Status(String checked_Status) {
        Checked_Status = checked_Status;
    }



    public String getQmnum() {
        return Qmnum;
    }

    public void setQmnum(String qmnum) {
        Qmnum = qmnum;
    }

    public String getAufnr() {
        return Aufnr;
    }

    public void setAufnr(String aufnr) {
        Aufnr = aufnr;
    }

    public String getObjnr() {
        return Objnr;
    }

    public void setObjnr(String objnr) {
        Objnr = objnr;
    }

    public String getManum() {
        return Manum;
    }

    public void setManum(String manum) {
        Manum = manum;
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

    public Notif_Status_WithNum_Prcbl() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Qmnum);
        dest.writeString(this.Aufnr);
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
        dest.writeString(this.Act_Status);
        dest.writeString(this.Checked_Status);
    }

    protected Notif_Status_WithNum_Prcbl(Parcel in) {
        this.Qmnum = in.readString();
        this.Aufnr = in.readString();
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
        this.Act_Status = in.readString();
        this.Checked_Status = in.readString();
    }

    public static final Creator<Notif_Status_WithNum_Prcbl> CREATOR = new Creator<Notif_Status_WithNum_Prcbl>() {
        @Override
        public Notif_Status_WithNum_Prcbl createFromParcel(Parcel source) {
            return new Notif_Status_WithNum_Prcbl(source);
        }

        @Override
        public Notif_Status_WithNum_Prcbl[] newArray(int size) {
            return new Notif_Status_WithNum_Prcbl[size];
        }
    };
}
