package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class Measurement_Parceble implements Parcelable{

    private boolean selected;
    private String iKey;
    private String qmnum;
    private String aufnr;
    private String vornr;
    private String equnr;
    private String mdocm;
    private String point;
    private String mpobj;
    private String mpobt;
    private String psort;
    private String pttxt;
    private String atinn;
    private String idate;
    private String itime;
    private String mdtxt;
    private String readr;
    private String atbez;
    private String msehi;
    private String msehl;
    private String readc;
    private String desic;
    private String prest;
    private String docaf;
    private String codct;
    private String codgr;
    private String vlcod;
    private String action;

    public Measurement_Parceble() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getiKey() {
        return iKey;
    }

    public void setiKey(String iKey) {
        this.iKey = iKey;
    }

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

    public String getVornr() {
        return vornr;
    }

    public void setVornr(String vornr) {
        this.vornr = vornr;
    }

    public String getEqunr() {
        return equnr;
    }

    public void setEqunr(String equnr) {
        this.equnr = equnr;
    }

    public String getMdocm() {
        return mdocm;
    }

    public void setMdocm(String mdocm) {
        this.mdocm = mdocm;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMpobj() {
        return mpobj;
    }

    public void setMpobj(String mpobj) {
        this.mpobj = mpobj;
    }

    public String getMpobt() {
        return mpobt;
    }

    public void setMpobt(String mpobt) {
        this.mpobt = mpobt;
    }

    public String getPsort() {
        return psort;
    }

    public void setPsort(String psort) {
        this.psort = psort;
    }

    public String getPttxt() {
        return pttxt;
    }

    public void setPttxt(String pttxt) {
        this.pttxt = pttxt;
    }

    public String getAtinn() {
        return atinn;
    }

    public void setAtinn(String atinn) {
        this.atinn = atinn;
    }

    public String getIdate() {
        return idate;
    }

    public void setIdate(String idate) {
        this.idate = idate;
    }

    public String getItime() {
        return itime;
    }

    public void setItime(String itime) {
        this.itime = itime;
    }

    public String getMdtxt() {
        return mdtxt;
    }

    public void setMdtxt(String mdtxt) {
        this.mdtxt = mdtxt;
    }

    public String getReadr() {
        return readr;
    }

    public void setReadr(String readr) {
        this.readr = readr;
    }

    public String getAtbez() {
        return atbez;
    }

    public void setAtbez(String atbez) {
        this.atbez = atbez;
    }

    public String getMsehi() {
        return msehi;
    }

    public void setMsehi(String msehi) {
        this.msehi = msehi;
    }

    public String getMsehl() {
        return msehl;
    }

    public void setMsehl(String msehl) {
        this.msehl = msehl;
    }

    public String getReadc() {
        return readc;
    }

    public void setReadc(String readc) {
        this.readc = readc;
    }

    public String getDesic() {
        return desic;
    }

    public void setDesic(String desic) {
        this.desic = desic;
    }

    public String getPrest() {
        return prest;
    }

    public void setPrest(String prest) {
        this.prest = prest;
    }

    public String getDocaf() {
        return docaf;
    }

    public void setDocaf(String docaf) {
        this.docaf = docaf;
    }

    public String getCodct() {
        return codct;
    }

    public void setCodct(String codct) {
        this.codct = codct;
    }

    public String getCodgr() {
        return codgr;
    }

    public void setCodgr(String codgr) {
        this.codgr = codgr;
    }

    public String getVlcod() {
        return vlcod;
    }

    public void setVlcod(String vlcod) {
        this.vlcod = vlcod;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.iKey);
        dest.writeString(this.qmnum);
        dest.writeString(this.aufnr);
        dest.writeString(this.vornr);
        dest.writeString(this.equnr);
        dest.writeString(this.mdocm);
        dest.writeString(this.point);
        dest.writeString(this.mpobj);
        dest.writeString(this.mpobt);
        dest.writeString(this.psort);
        dest.writeString(this.pttxt);
        dest.writeString(this.atinn);
        dest.writeString(this.idate);
        dest.writeString(this.itime);
        dest.writeString(this.mdtxt);
        dest.writeString(this.readr);
        dest.writeString(this.atbez);
        dest.writeString(this.msehi);
        dest.writeString(this.msehl);
        dest.writeString(this.readc);
        dest.writeString(this.desic);
        dest.writeString(this.prest);
        dest.writeString(this.docaf);
        dest.writeString(this.codct);
        dest.writeString(this.codgr);
        dest.writeString(this.vlcod);
        dest.writeString(this.action);
    }

    protected Measurement_Parceble(Parcel in) {
        this.selected = in.readByte() != 0;
        this.iKey = in.readString();
        this.qmnum = in.readString();
        this.aufnr = in.readString();
        this.vornr = in.readString();
        this.equnr = in.readString();
        this.mdocm = in.readString();
        this.point = in.readString();
        this.mpobj = in.readString();
        this.mpobt = in.readString();
        this.psort = in.readString();
        this.pttxt = in.readString();
        this.atinn = in.readString();
        this.idate = in.readString();
        this.itime = in.readString();
        this.mdtxt = in.readString();
        this.readr = in.readString();
        this.atbez = in.readString();
        this.msehi = in.readString();
        this.msehl = in.readString();
        this.readc = in.readString();
        this.desic = in.readString();
        this.prest = in.readString();
        this.docaf = in.readString();
        this.codct = in.readString();
        this.codgr = in.readString();
        this.vlcod = in.readString();
        this.action = in.readString();
    }

    public static final Creator<Measurement_Parceble> CREATOR = new Creator<Measurement_Parceble>() {
        @Override
        public Measurement_Parceble createFromParcel(Parcel source) {
            return new Measurement_Parceble(source);
        }

        @Override
        public Measurement_Parceble[] newArray(int size) {
            return new Measurement_Parceble[size];
        }
    };
}
