package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdrWDItemPrcbl implements Parcelable{
    private String Wcnr;
    private String Wctim;
    private String Objnr;
    private String Itmtyp;
    private String Seq;
    private String Pred;
    private String Succ;
    private String Ccobj;
    private String Cctyp;
    private String Stxt;
    private String Tggrp;
    private String Tgstep;
    private String Tgproc;
    private String Tgtyp;
    private String Tgseq;
    private String Tgtxt;
    private String Unstep;
    private String Unproc;
    private String Untyp;
    private String Unseq;
    private String Untxt;
    private String Phblflg;
    private String Phbltyp;
    private String Phblnr;
    private String Tgflg;
    private String Tgform;
    private String Tgnr;
    private String Unform;
    private String Unnr;
    private String Control;
    private String Location;
    private String Refobj;
    private String Action;
    private String Btg;
    private String Etg;
    private String Bug;
    private String Eug;
    private String Tgprox;
    private String equipdDesc;
    private boolean tagStatus;
    private boolean untagStatus;

    public boolean isUntagStatus() {
        return untagStatus;
    }

    public void setUntagStatus(boolean untagStatus) {
        this.untagStatus = untagStatus;
    }

    public String getEquipdDesc() {
        return equipdDesc;
    }

    public void setEquipdDesc(String equipdDesc) {
        this.equipdDesc = equipdDesc;
    }

    public boolean isTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(boolean tagStatus) {
        this.tagStatus = tagStatus;
    }

    public String getTgprox() {
        return Tgprox;
    }

    public void setTgprox(String tgprox) {
        Tgprox = tgprox;
    }

    public String getWcnr() {
        return Wcnr;
    }

    public void setWcnr(String wcnr) {
        Wcnr = wcnr;
    }

    public String getWctim() {
        return Wctim;
    }

    public void setWctim(String wctim) {
        Wctim = wctim;
    }

    public String getObjnr() {
        return Objnr;
    }

    public void setObjnr(String objnr) {
        Objnr = objnr;
    }

    public String getItmtyp() {
        return Itmtyp;
    }

    public void setItmtyp(String itmtyp) {
        Itmtyp = itmtyp;
    }

    public String getSeq() {
        return Seq;
    }

    public void setSeq(String seq) {
        Seq = seq;
    }

    public String getPred() {
        return Pred;
    }

    public void setPred(String pred) {
        Pred = pred;
    }

    public String getSucc() {
        return Succ;
    }

    public void setSucc(String succ) {
        Succ = succ;
    }

    public String getCcobj() {
        return Ccobj;
    }

    public void setCcobj(String ccobj) {
        Ccobj = ccobj;
    }

    public String getCctyp() {
        return Cctyp;
    }

    public void setCctyp(String cctyp) {
        Cctyp = cctyp;
    }

    public String getStxt() {
        return Stxt;
    }

    public void setStxt(String stxt) {
        Stxt = stxt;
    }

    public String getTggrp() {
        return Tggrp;
    }

    public void setTggrp(String tggrp) {
        Tggrp = tggrp;
    }

    public String getTgstep() {
        return Tgstep;
    }

    public void setTgstep(String tgstep) {
        Tgstep = tgstep;
    }

    public String getTgproc() {
        return Tgproc;
    }

    public void setTgproc(String tgproc) {
        Tgproc = tgproc;
    }

    public String getTgtyp() {
        return Tgtyp;
    }

    public void setTgtyp(String tgtyp) {
        Tgtyp = tgtyp;
    }

    public String getTgseq() {
        return Tgseq;
    }

    public void setTgseq(String tgseq) {
        Tgseq = tgseq;
    }

    public String getTgtxt() {
        return Tgtxt;
    }

    public void setTgtxt(String tgtxt) {
        Tgtxt = tgtxt;
    }

    public String getUnstep() {
        return Unstep;
    }

    public void setUnstep(String unstep) {
        Unstep = unstep;
    }

    public String getUnproc() {
        return Unproc;
    }

    public void setUnproc(String unproc) {
        Unproc = unproc;
    }

    public String getUntyp() {
        return Untyp;
    }

    public void setUntyp(String untyp) {
        Untyp = untyp;
    }

    public String getUnseq() {
        return Unseq;
    }

    public void setUnseq(String unseq) {
        Unseq = unseq;
    }

    public String getUntxt() {
        return Untxt;
    }

    public void setUntxt(String untxt) {
        Untxt = untxt;
    }

    public String getPhblflg() {
        return Phblflg;
    }

    public void setPhblflg(String phblflg) {
        Phblflg = phblflg;
    }

    public String getPhbltyp() {
        return Phbltyp;
    }

    public void setPhbltyp(String phbltyp) {
        Phbltyp = phbltyp;
    }

    public String getPhblnr() {
        return Phblnr;
    }

    public void setPhblnr(String phblnr) {
        Phblnr = phblnr;
    }

    public String getTgflg() {
        return Tgflg;
    }

    public void setTgflg(String tgflg) {
        Tgflg = tgflg;
    }

    public String getTgform() {
        return Tgform;
    }

    public void setTgform(String tgform) {
        Tgform = tgform;
    }

    public String getTgnr() {
        return Tgnr;
    }

    public void setTgnr(String tgnr) {
        Tgnr = tgnr;
    }

    public String getUnform() {
        return Unform;
    }

    public void setUnform(String unform) {
        Unform = unform;
    }

    public String getUnnr() {
        return Unnr;
    }

    public void setUnnr(String unnr) {
        Unnr = unnr;
    }

    public String getControl() {
        return Control;
    }

    public void setControl(String control) {
        Control = control;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRefobj() {
        return Refobj;
    }

    public void setRefobj(String refobj) {
        Refobj = refobj;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getBtg() {
        return Btg;
    }

    public void setBtg(String btg) {
        Btg = btg;
    }

    public String getEtg() {
        return Etg;
    }

    public void setEtg(String etg) {
        Etg = etg;
    }

    public String getBug() {
        return Bug;
    }

    public void setBug(String bug) {
        Bug = bug;
    }

    public String getEug() {
        return Eug;
    }

    public void setEug(String eug) {
        Eug = eug;
    }

    public OrdrWDItemPrcbl() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Wcnr);
        dest.writeString(this.Wctim);
        dest.writeString(this.Objnr);
        dest.writeString(this.Itmtyp);
        dest.writeString(this.Seq);
        dest.writeString(this.Pred);
        dest.writeString(this.Succ);
        dest.writeString(this.Ccobj);
        dest.writeString(this.Cctyp);
        dest.writeString(this.Stxt);
        dest.writeString(this.Tggrp);
        dest.writeString(this.Tgstep);
        dest.writeString(this.Tgproc);
        dest.writeString(this.Tgtyp);
        dest.writeString(this.Tgseq);
        dest.writeString(this.Tgtxt);
        dest.writeString(this.Unstep);
        dest.writeString(this.Unproc);
        dest.writeString(this.Untyp);
        dest.writeString(this.Unseq);
        dest.writeString(this.Untxt);
        dest.writeString(this.Phblflg);
        dest.writeString(this.Phbltyp);
        dest.writeString(this.Phblnr);
        dest.writeString(this.Tgflg);
        dest.writeString(this.Tgform);
        dest.writeString(this.Tgnr);
        dest.writeString(this.Unform);
        dest.writeString(this.Unnr);
        dest.writeString(this.Control);
        dest.writeString(this.Location);
        dest.writeString(this.Refobj);
        dest.writeString(this.Action);
        dest.writeString(this.Btg);
        dest.writeString(this.Etg);
        dest.writeString(this.Bug);
        dest.writeString(this.Eug);
        dest.writeString(this.Tgprox);
        dest.writeString(this.equipdDesc);
        dest.writeByte(this.tagStatus ? (byte) 1 : (byte) 0);
        dest.writeByte(this.untagStatus ? (byte) 1 : (byte) 0);
    }

    protected OrdrWDItemPrcbl(Parcel in) {
        this.Wcnr = in.readString();
        this.Wctim = in.readString();
        this.Objnr = in.readString();
        this.Itmtyp = in.readString();
        this.Seq = in.readString();
        this.Pred = in.readString();
        this.Succ = in.readString();
        this.Ccobj = in.readString();
        this.Cctyp = in.readString();
        this.Stxt = in.readString();
        this.Tggrp = in.readString();
        this.Tgstep = in.readString();
        this.Tgproc = in.readString();
        this.Tgtyp = in.readString();
        this.Tgseq = in.readString();
        this.Tgtxt = in.readString();
        this.Unstep = in.readString();
        this.Unproc = in.readString();
        this.Untyp = in.readString();
        this.Unseq = in.readString();
        this.Untxt = in.readString();
        this.Phblflg = in.readString();
        this.Phbltyp = in.readString();
        this.Phblnr = in.readString();
        this.Tgflg = in.readString();
        this.Tgform = in.readString();
        this.Tgnr = in.readString();
        this.Unform = in.readString();
        this.Unnr = in.readString();
        this.Control = in.readString();
        this.Location = in.readString();
        this.Refobj = in.readString();
        this.Action = in.readString();
        this.Btg = in.readString();
        this.Etg = in.readString();
        this.Bug = in.readString();
        this.Eug = in.readString();
        this.Tgprox = in.readString();
        this.equipdDesc = in.readString();
        this.tagStatus = in.readByte() != 0;
        this.untagStatus = in.readByte() != 0;
    }

    public static final Creator<OrdrWDItemPrcbl> CREATOR = new Creator<OrdrWDItemPrcbl>() {
        @Override
        public OrdrWDItemPrcbl createFromParcel(Parcel source) {
            return new OrdrWDItemPrcbl(source);
        }

        @Override
        public OrdrWDItemPrcbl[] newArray(int size) {
            return new OrdrWDItemPrcbl[size];
        }
    };
}
