package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdrWaChkReqPrcbl implements Parcelable {

    private String Wapinr;
    private String Wapityp;
    private String Needid;
    private String Wkid;
    private String Value;
    private String ChkPointType;
    private String Desctext;
    private String Tplnr;
    private String Wkgrp;
    private String Needgrp;
    private String Equnr;
    private boolean yes_checked;
    private boolean no_checked;

    public boolean isYes_checked() {
        return yes_checked;
    }

    public void setYes_checked(boolean yes_checked) {
        this.yes_checked = yes_checked;
    }

    public boolean isNo_checked() {
        return no_checked;
    }

    public void setNo_checked(boolean no_checked) {
        this.no_checked = no_checked;
    }

    public String getWkid() {
        return Wkid;
    }

    public void setWkid(String wkid) {
        Wkid = wkid;
    }

    public OrdrWaChkReqPrcbl() {
    }

    public String getWapinr() {
        return Wapinr;
    }

    public void setWapinr(String wapinr) {
        Wapinr = wapinr;
    }

    public String getWapityp() {
        return Wapityp;
    }

    public void setWapityp(String wapityp) {
        Wapityp = wapityp;
    }

    public String getNeedid() {
        return Needid;
    }

    public void setNeedid(String needid) {
        Needid = needid;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getChkPointType() {
        return ChkPointType;
    }

    public void setChkPointType(String chkPointType) {
        ChkPointType = chkPointType;
    }

    public String getDesctext() {
        return Desctext;
    }

    public void setDesctext(String desctext) {
        Desctext = desctext;
    }

    public String getTplnr() {
        return Tplnr;
    }

    public void setTplnr(String tplnr) {
        Tplnr = tplnr;
    }

    public String getWkgrp() {
        return Wkgrp;
    }

    public void setWkgrp(String wkgrp) {
        Wkgrp = wkgrp;
    }

    public String getNeedgrp() {
        return Needgrp;
    }

    public void setNeedgrp(String needgrp) {
        Needgrp = needgrp;
    }

    public String getEqunr() {
        return Equnr;
    }

    public void setEqunr(String equnr) {
        Equnr = equnr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Wapinr);
        dest.writeString(this.Wapityp);
        dest.writeString(this.Needid);
        dest.writeString(this.Wkid);
        dest.writeString(this.Value);
        dest.writeString(this.ChkPointType);
        dest.writeString(this.Desctext);
        dest.writeString(this.Tplnr);
        dest.writeString(this.Wkgrp);
        dest.writeString(this.Needgrp);
        dest.writeString(this.Equnr);
        dest.writeByte(this.yes_checked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.no_checked ? (byte) 1 : (byte) 0);
    }

    protected OrdrWaChkReqPrcbl(Parcel in) {
        this.Wapinr = in.readString();
        this.Wapityp = in.readString();
        this.Needid = in.readString();
        this.Wkid = in.readString();
        this.Value = in.readString();
        this.ChkPointType = in.readString();
        this.Desctext = in.readString();
        this.Tplnr = in.readString();
        this.Wkgrp = in.readString();
        this.Needgrp = in.readString();
        this.Equnr = in.readString();
        this.yes_checked = in.readByte() != 0;
        this.no_checked = in.readByte() != 0;
    }

    public static final Creator<OrdrWaChkReqPrcbl> CREATOR = new Creator<OrdrWaChkReqPrcbl>() {
        @Override
        public OrdrWaChkReqPrcbl createFromParcel(Parcel source) {
            return new OrdrWaChkReqPrcbl(source);
        }

        @Override
        public OrdrWaChkReqPrcbl[] newArray(int size) {
            return new OrdrWaChkReqPrcbl[size];
        }
    };
}
