package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;

import java.util.ArrayList;

public class OrdrPermitPrcbl implements Parcelable {

    private boolean selected;
    private String FuncLoc;
    private String UUID;
    private String ApplName;
    private String Aufnr;
    private String Objart;
    private String Wapnr;
    private String Wapinr;
    private String Wcnr;
    private String Iwerk;
    private String Objtyp;
    private String Usage;
    private String Usagex;
    private String Train;
    private String Trainx;
    private String Anlzu;
    private String Anlzux;
    private String Etape;
    private String Etapex;
    private String Rctime;
    private String Rcunit;
    private String Priok;
    private String Priokx;
    private String Stxt;
    private String Datefr;
    private String Timefr;
    private String Dateto;
    private String Timeto;
    private String Objnr;
    private String Refobj;
    private String Crea;
    private String Prep;
    private String Comp;
    private String Appr;
    private String Pappr;
    private String Action;
    private String Begru;
    private String Begtx;
    private int Extperiod;
    private ArrayList<NotifOrdrStatusPrcbl> statusPrcbl_Al;
    private ArrayList<OrdrPermitPrcbl> waWdPrcbl_Al;
    private ArrayList<OrdrWDItemPrcbl> wdItemPrcbl_Al;
    private ArrayList<OrdrWcagnsPrcbl> wcagnsPrcbl_Al;
    private ArrayList<OrdrWaChkReqPrcbl> waChkReqPrcbl_Al;
    private ArrayList<OrdrTagUnTagTextPrcbl> tagText_al;
    private ArrayList<OrdrTagUnTagTextPrcbl> unTagText_al;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<OrdrTagUnTagTextPrcbl> getTagText_al() {
        return tagText_al;
    }

    public void setTagText_al(ArrayList<OrdrTagUnTagTextPrcbl> tagText_al) {
        this.tagText_al = tagText_al;
    }

    public ArrayList<OrdrTagUnTagTextPrcbl> getUnTagText_al() {
        return unTagText_al;
    }

    public void setUnTagText_al(ArrayList<OrdrTagUnTagTextPrcbl> unTagText_al) {
        this.unTagText_al = unTagText_al;
    }

    public String getFuncLoc() {
        return FuncLoc;
    }

    public void setFuncLoc(String funcLoc) {
        FuncLoc = funcLoc;
    }

    public String getApplName() {
        return ApplName;
    }

    public void setApplName(String applName) {
        ApplName = applName;
    }

    public ArrayList<NotifOrdrStatusPrcbl> getStatusPrcbl_Al() {
        return statusPrcbl_Al;
    }

    public void setStatusPrcbl_Al(ArrayList<NotifOrdrStatusPrcbl> statusPrcbl_Al) {
        this.statusPrcbl_Al = statusPrcbl_Al;
    }

    public ArrayList<OrdrWDItemPrcbl> getWdItemPrcbl_Al() {
        return wdItemPrcbl_Al;
    }

    public void setWdItemPrcbl_Al(ArrayList<OrdrWDItemPrcbl> wdItemPrcbl_Al) {
        this.wdItemPrcbl_Al = wdItemPrcbl_Al;
    }

    public ArrayList<OrdrWcagnsPrcbl> getWcagnsPrcbl_Al() {
        return wcagnsPrcbl_Al;
    }

    public void setWcagnsPrcbl_Al(ArrayList<OrdrWcagnsPrcbl> wcagnsPrcbl_Al) {
        this.wcagnsPrcbl_Al = wcagnsPrcbl_Al;
    }

    public ArrayList<OrdrWaChkReqPrcbl> getWaChkReqPrcbl_Al() {
        return waChkReqPrcbl_Al;
    }

    public void setWaChkReqPrcbl_Al(ArrayList<OrdrWaChkReqPrcbl> waChkReqPrcbl_Al) {
        this.waChkReqPrcbl_Al = waChkReqPrcbl_Al;
    }

    public OrdrPermitPrcbl() {
    }

    public int getExtperiod() {
        return Extperiod;
    }

    public void setExtperiod(int extperiod) {
        Extperiod = extperiod;
    }

    public String getWapinr() {
        return Wapinr;
    }

    public void setWapinr(String wapinr) {
        Wapinr = wapinr;
    }

    public String getWcnr() {
        return Wcnr;
    }

    public void setWcnr(String wcnr) {
        Wcnr = wcnr;
    }

    public String getObjtyp() {
        return Objtyp;
    }

    public void setObjtyp(String objtyp) {
        Objtyp = objtyp;
    }

    public String getRefobj() {
        return Refobj;
    }

    public void setRefobj(String refobj) {
        Refobj = refobj;
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

    public String getObjart() {
        return Objart;
    }

    public void setObjart(String objart) {
        Objart = objart;
    }

    public String getWapnr() {
        return Wapnr;
    }

    public void setWapnr(String wapnr) {
        Wapnr = wapnr;
    }

    public String getIwerk() {
        return Iwerk;
    }

    public void setIwerk(String iwerk) {
        Iwerk = iwerk;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        Usage = usage;
    }

    public String getUsagex() {
        return Usagex;
    }

    public void setUsagex(String usagex) {
        Usagex = usagex;
    }

    public String getTrain() {
        return Train;
    }

    public void setTrain(String train) {
        Train = train;
    }

    public String getTrainx() {
        return Trainx;
    }

    public void setTrainx(String trainx) {
        Trainx = trainx;
    }

    public String getAnlzu() {
        return Anlzu;
    }

    public void setAnlzu(String anlzu) {
        Anlzu = anlzu;
    }

    public String getAnlzux() {
        return Anlzux;
    }

    public void setAnlzux(String anlzux) {
        Anlzux = anlzux;
    }

    public String getEtape() {
        return Etape;
    }

    public void setEtape(String etape) {
        Etape = etape;
    }

    public String getEtapex() {
        return Etapex;
    }

    public void setEtapex(String etapex) {
        Etapex = etapex;
    }

    public String getRctime() {
        return Rctime;
    }

    public void setRctime(String rctime) {
        Rctime = rctime;
    }

    public String getRcunit() {
        return Rcunit;
    }

    public void setRcunit(String rcunit) {
        Rcunit = rcunit;
    }

    public String getPriok() {
        return Priok;
    }

    public void setPriok(String priok) {
        Priok = priok;
    }

    public String getPriokx() {
        return Priokx;
    }

    public void setPriokx(String priokx) {
        Priokx = priokx;
    }

    public String getStxt() {
        return Stxt;
    }

    public void setStxt(String stxt) {
        Stxt = stxt;
    }

    public String getDatefr() {
        return Datefr;
    }

    public void setDatefr(String datefr) {
        Datefr = datefr;
    }

    public String getTimefr() {
        return Timefr;
    }

    public void setTimefr(String timefr) {
        Timefr = timefr;
    }

    public String getDateto() {
        return Dateto;
    }

    public void setDateto(String dateto) {
        Dateto = dateto;
    }

    public String getTimeto() {
        return Timeto;
    }

    public void setTimeto(String timeto) {
        Timeto = timeto;
    }

    public String getObjnr() {
        return Objnr;
    }

    public ArrayList<OrdrPermitPrcbl> getWaWdPrcbl_Al() {
        return waWdPrcbl_Al;
    }

    public void setWaWdPrcbl_Al(ArrayList<OrdrPermitPrcbl> waWdPrcbl_Al) {
        this.waWdPrcbl_Al = waWdPrcbl_Al;
    }

    public void setObjnr(String objnr) {
        Objnr = objnr;
    }

    public String getCrea() {
        return Crea;
    }

    public void setCrea(String crea) {
        Crea = crea;
    }

    public String getPrep() {
        return Prep;
    }

    public void setPrep(String prep) {
        Prep = prep;
    }

    public String getComp() {
        return Comp;
    }

    public void setComp(String comp) {
        Comp = comp;
    }

    public String getAppr() {
        return Appr;
    }

    public void setAppr(String appr) {
        Appr = appr;
    }

    public String getPappr() {
        return Pappr;
    }

    public void setPappr(String pappr) {
        Pappr = pappr;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getBegru() {
        return Begru;
    }

    public void setBegru(String begru) {
        Begru = begru;
    }

    public String getBegtx() {
        return Begtx;
    }

    public void setBegtx(String begtx) {
        Begtx = begtx;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.FuncLoc);
        dest.writeString(this.UUID);
        dest.writeString(this.ApplName);
        dest.writeString(this.Aufnr);
        dest.writeString(this.Objart);
        dest.writeString(this.Wapnr);
        dest.writeString(this.Wapinr);
        dest.writeString(this.Wcnr);
        dest.writeString(this.Iwerk);
        dest.writeString(this.Objtyp);
        dest.writeString(this.Usage);
        dest.writeString(this.Usagex);
        dest.writeString(this.Train);
        dest.writeString(this.Trainx);
        dest.writeString(this.Anlzu);
        dest.writeString(this.Anlzux);
        dest.writeString(this.Etape);
        dest.writeString(this.Etapex);
        dest.writeString(this.Rctime);
        dest.writeString(this.Rcunit);
        dest.writeString(this.Priok);
        dest.writeString(this.Priokx);
        dest.writeString(this.Stxt);
        dest.writeString(this.Datefr);
        dest.writeString(this.Timefr);
        dest.writeString(this.Dateto);
        dest.writeString(this.Timeto);
        dest.writeString(this.Objnr);
        dest.writeString(this.Refobj);
        dest.writeString(this.Crea);
        dest.writeString(this.Prep);
        dest.writeString(this.Comp);
        dest.writeString(this.Appr);
        dest.writeString(this.Pappr);
        dest.writeString(this.Action);
        dest.writeString(this.Begru);
        dest.writeString(this.Begtx);
        dest.writeInt(this.Extperiod);
        dest.writeTypedList(this.statusPrcbl_Al);
        dest.writeTypedList(this.waWdPrcbl_Al);
        dest.writeTypedList(this.wdItemPrcbl_Al);
        dest.writeTypedList(this.wcagnsPrcbl_Al);
        dest.writeTypedList(this.waChkReqPrcbl_Al);
        dest.writeTypedList(this.tagText_al);
        dest.writeTypedList(this.unTagText_al);
    }

    protected OrdrPermitPrcbl(Parcel in) {
        this.selected = in.readByte() != 0;
        this.FuncLoc = in.readString();
        this.UUID = in.readString();
        this.ApplName = in.readString();
        this.Aufnr = in.readString();
        this.Objart = in.readString();
        this.Wapnr = in.readString();
        this.Wapinr = in.readString();
        this.Wcnr = in.readString();
        this.Iwerk = in.readString();
        this.Objtyp = in.readString();
        this.Usage = in.readString();
        this.Usagex = in.readString();
        this.Train = in.readString();
        this.Trainx = in.readString();
        this.Anlzu = in.readString();
        this.Anlzux = in.readString();
        this.Etape = in.readString();
        this.Etapex = in.readString();
        this.Rctime = in.readString();
        this.Rcunit = in.readString();
        this.Priok = in.readString();
        this.Priokx = in.readString();
        this.Stxt = in.readString();
        this.Datefr = in.readString();
        this.Timefr = in.readString();
        this.Dateto = in.readString();
        this.Timeto = in.readString();
        this.Objnr = in.readString();
        this.Refobj = in.readString();
        this.Crea = in.readString();
        this.Prep = in.readString();
        this.Comp = in.readString();
        this.Appr = in.readString();
        this.Pappr = in.readString();
        this.Action = in.readString();
        this.Begru = in.readString();
        this.Begtx = in.readString();
        this.Extperiod = in.readInt();
        this.statusPrcbl_Al = in.createTypedArrayList(NotifOrdrStatusPrcbl.CREATOR);
        this.waWdPrcbl_Al = in.createTypedArrayList(OrdrPermitPrcbl.CREATOR);
        this.wdItemPrcbl_Al = in.createTypedArrayList(OrdrWDItemPrcbl.CREATOR);
        this.wcagnsPrcbl_Al = in.createTypedArrayList(OrdrWcagnsPrcbl.CREATOR);
        this.waChkReqPrcbl_Al = in.createTypedArrayList(OrdrWaChkReqPrcbl.CREATOR);
        this.tagText_al = in.createTypedArrayList(OrdrTagUnTagTextPrcbl.CREATOR);
        this.unTagText_al = in.createTypedArrayList(OrdrTagUnTagTextPrcbl.CREATOR);
    }

    public static final Creator<OrdrPermitPrcbl> CREATOR = new Creator<OrdrPermitPrcbl>() {
        @Override
        public OrdrPermitPrcbl createFromParcel(Parcel source) {
            return new OrdrPermitPrcbl(source);
        }

        @Override
        public OrdrPermitPrcbl[] newArray(int size) {
            return new OrdrPermitPrcbl[size];
        }
    };
}
