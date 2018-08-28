package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdrWcagnsPrcbl implements Parcelable{

    private String UUID;
    private String Aufnr;
    private String Objnr;
    private String Counter;
    private String Objart;
    private String Objtyp;
    private String Pmsog;
    private String Gntxt;
    private String Geniakt;
    private String Genvname;
    private String Action;
    private String Werks;
    private String Crname;
    private int Hilvl;
    private String Procflg;
    private String Direction;
    private String Copyflg;
    private String Mandflg;
    private String Deacflg;
    private String Status;
    private String Asgnflg;
    private String Autoflg;
    private String Agent;
    private String Valflg;
    private String Wcmuse;
    private String Gendatum;
    private String Gentime;
    private boolean geniakt_status;

    public boolean isGeniakt_status() {
        return geniakt_status;
    }

    public void setGeniakt_status(boolean geniakt_status) {
        this.geniakt_status = geniakt_status;
    }

    public OrdrWcagnsPrcbl() {
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

    public String getObjnr() {
        return Objnr;
    }

    public void setObjnr(String objnr) {
        Objnr = objnr;
    }

    public String getCounter() {
        return Counter;
    }

    public void setCounter(String counter) {
        Counter = counter;
    }

    public String getObjart() {
        return Objart;
    }

    public void setObjart(String objart) {
        Objart = objart;
    }

    public String getObjtyp() {
        return Objtyp;
    }

    public void setObjtyp(String objtyp) {
        Objtyp = objtyp;
    }

    public String getPmsog() {
        return Pmsog;
    }

    public void setPmsog(String pmsog) {
        Pmsog = pmsog;
    }

    public String getGntxt() {
        return Gntxt;
    }

    public void setGntxt(String gntxt) {
        Gntxt = gntxt;
    }

    public String getGeniakt() {
        return Geniakt;
    }

    public void setGeniakt(String geniakt) {
        Geniakt = geniakt;
    }

    public String getGenvname() {
        return Genvname;
    }

    public void setGenvname(String genvname) {
        Genvname = genvname;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getWerks() {
        return Werks;
    }

    public void setWerks(String werks) {
        Werks = werks;
    }

    public String getCrname() {
        return Crname;
    }

    public void setCrname(String crname) {
        Crname = crname;
    }

    public int getHilvl() {
        return Hilvl;
    }

    public void setHilvl(int hilvl) {
        Hilvl = hilvl;
    }

    public String getProcflg() {
        return Procflg;
    }

    public void setProcflg(String procflg) {
        Procflg = procflg;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getCopyflg() {
        return Copyflg;
    }

    public void setCopyflg(String copyflg) {
        Copyflg = copyflg;
    }

    public String getMandflg() {
        return Mandflg;
    }

    public void setMandflg(String mandflg) {
        Mandflg = mandflg;
    }

    public String getDeacflg() {
        return Deacflg;
    }

    public void setDeacflg(String deacflg) {
        Deacflg = deacflg;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAsgnflg() {
        return Asgnflg;
    }

    public void setAsgnflg(String asgnflg) {
        Asgnflg = asgnflg;
    }

    public String getAutoflg() {
        return Autoflg;
    }

    public void setAutoflg(String autoflg) {
        Autoflg = autoflg;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getValflg() {
        return Valflg;
    }

    public void setValflg(String valflg) {
        Valflg = valflg;
    }

    public String getWcmuse() {
        return Wcmuse;
    }

    public void setWcmuse(String wcmuse) {
        Wcmuse = wcmuse;
    }

    public String getGendatum() {
        return Gendatum;
    }

    public void setGendatum(String gendatum) {
        Gendatum = gendatum;
    }

    public String getGentime() {
        return Gentime;
    }

    public void setGentime(String gentime) {
        Gentime = gentime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UUID);
        dest.writeString(this.Aufnr);
        dest.writeString(this.Objnr);
        dest.writeString(this.Counter);
        dest.writeString(this.Objart);
        dest.writeString(this.Objtyp);
        dest.writeString(this.Pmsog);
        dest.writeString(this.Gntxt);
        dest.writeString(this.Geniakt);
        dest.writeString(this.Genvname);
        dest.writeString(this.Action);
        dest.writeString(this.Werks);
        dest.writeString(this.Crname);
        dest.writeInt(this.Hilvl);
        dest.writeString(this.Procflg);
        dest.writeString(this.Direction);
        dest.writeString(this.Copyflg);
        dest.writeString(this.Mandflg);
        dest.writeString(this.Deacflg);
        dest.writeString(this.Status);
        dest.writeString(this.Asgnflg);
        dest.writeString(this.Autoflg);
        dest.writeString(this.Agent);
        dest.writeString(this.Valflg);
        dest.writeString(this.Wcmuse);
        dest.writeString(this.Gendatum);
        dest.writeString(this.Gentime);
        dest.writeByte(this.geniakt_status ? (byte) 1 : (byte) 0);
    }

    protected OrdrWcagnsPrcbl(Parcel in) {
        this.UUID = in.readString();
        this.Aufnr = in.readString();
        this.Objnr = in.readString();
        this.Counter = in.readString();
        this.Objart = in.readString();
        this.Objtyp = in.readString();
        this.Pmsog = in.readString();
        this.Gntxt = in.readString();
        this.Geniakt = in.readString();
        this.Genvname = in.readString();
        this.Action = in.readString();
        this.Werks = in.readString();
        this.Crname = in.readString();
        this.Hilvl = in.readInt();
        this.Procflg = in.readString();
        this.Direction = in.readString();
        this.Copyflg = in.readString();
        this.Mandflg = in.readString();
        this.Deacflg = in.readString();
        this.Status = in.readString();
        this.Asgnflg = in.readString();
        this.Autoflg = in.readString();
        this.Agent = in.readString();
        this.Valflg = in.readString();
        this.Wcmuse = in.readString();
        this.Gendatum = in.readString();
        this.Gentime = in.readString();
        this.geniakt_status = in.readByte() != 0;
    }

    public static final Creator<OrdrWcagnsPrcbl> CREATOR = new Creator<OrdrWcagnsPrcbl>() {
        @Override
        public OrdrWcagnsPrcbl createFromParcel(Parcel source) {
            return new OrdrWcagnsPrcbl(source);
        }

        @Override
        public OrdrWcagnsPrcbl[] newArray(int size) {
            return new OrdrWcagnsPrcbl[size];
        }
    };
}
