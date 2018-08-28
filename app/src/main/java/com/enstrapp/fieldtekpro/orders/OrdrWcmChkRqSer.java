package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrWcmChkRqSer {
    @SerializedName("Wapinr")
    @Expose
    private String wapinr;
    @SerializedName("Wapityp")
    @Expose
    private String wapityp;
    @SerializedName("ChkPointType")
    @Expose
    private String chkPointType;
    @SerializedName("Wkid")
    @Expose
    private String wkid;
    @SerializedName("Needid")
    @Expose
    private String needid;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Desctext")
    @Expose
    private String desctext;
    @SerializedName("Wkgrp")
    @Expose
    private String wkgrp;
    @SerializedName("Needgrp")
    @Expose
    private String needgrp;
    @SerializedName("Tplnr")
    @Expose
    private String tplnr;
    @SerializedName("Equnr")
    @Expose
    private String equnr;

    public String getWapinr() {
        return wapinr;
    }

    public void setWapinr(String wapinr) {
        this.wapinr = wapinr;
    }

    public String getWapityp() {
        return wapityp;
    }

    public void setWapityp(String wapityp) {
        this.wapityp = wapityp;
    }

    public String getChkPointType() {
        return chkPointType;
    }

    public void setChkPointType(String chkPointType) {
        this.chkPointType = chkPointType;
    }

    public String getWkid() {
        return wkid;
    }

    public void setWkid(String wkid) {
        this.wkid = wkid;
    }

    public String getNeedid() {
        return needid;
    }

    public void setNeedid(String needid) {
        this.needid = needid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesctext() {
        return desctext;
    }

    public void setDesctext(String desctext) {
        this.desctext = desctext;
    }

    public String getWkgrp() {
        return wkgrp;
    }

    public void setWkgrp(String wkgrp) {
        this.wkgrp = wkgrp;
    }

    public String getNeedgrp() {
        return needgrp;
    }

    public void setNeedgrp(String needgrp) {
        this.needgrp = needgrp;
    }

    public String getTplnr() {
        return tplnr;
    }

    public void setTplnr(String tplnr) {
        this.tplnr = tplnr;
    }

    public String getEqunr() {
        return equnr;
    }

    public void setEqunr(String equnr) {
        this.equnr = equnr;
    }
}

