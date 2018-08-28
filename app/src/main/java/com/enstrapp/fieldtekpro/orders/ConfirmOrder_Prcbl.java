package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfirmOrder_Prcbl implements Parcelable{

    private String Aufnr;
    private String Vornr;
    private String ConfNo;
    private String ConfText;
    private String ActWork;
    private String UnWork;
    private String PlanWork;
    private String Learr;
    private String Bemot;
    private String Grund;
    private String Leknw;
    private String Aueru;
    private String Ausor;
    private String Pernr;
    private String Loart;
    private String Status;
    private String Rsnum;
    private String Rspos;
    private String PostgDate;
    private String Plant;
    private String WorkCntr;
    private String Posnr;
    private String Matnr;
    private String Bwart;
    private String Werks;
    private String Lgort;
    private String Erfmg;
    private String Erfme;
    private String ExecStartDate;
    private String ExecStartTime;
    private String ExecFinDate;
    private String ExecFinTime;

    public String getWorkCntr() {
        return WorkCntr;
    }

    public void setWorkCntr(String workCntr) {
        WorkCntr = workCntr;
    }

    public String getPlant() {
        return Plant;
    }

    public void setPlant(String plant) {
        Plant = plant;
    }

    public String getPostgDate() {
        return PostgDate;
    }

    public void setPostgDate(String postgDate) {
        PostgDate = postgDate;
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

    public String getConfNo() {
        return ConfNo;
    }

    public void setConfNo(String confNo) {
        ConfNo = confNo;
    }

    public String getConfText() {
        return ConfText;
    }

    public void setConfText(String confText) {
        ConfText = confText;
    }

    public String getActWork() {
        return ActWork;
    }

    public void setActWork(String actWork) {
        ActWork = actWork;
    }

    public String getUnWork() {
        return UnWork;
    }

    public void setUnWork(String unWork) {
        UnWork = unWork;
    }

    public String getPlanWork() {
        return PlanWork;
    }

    public void setPlanWork(String planWork) {
        PlanWork = planWork;
    }

    public String getLearr() {
        return Learr;
    }

    public void setLearr(String learr) {
        Learr = learr;
    }

    public String getBemot() {
        return Bemot;
    }

    public void setBemot(String bemot) {
        Bemot = bemot;
    }

    public String getGrund() {
        return Grund;
    }

    public void setGrund(String grund) {
        Grund = grund;
    }

    public String getLeknw() {
        return Leknw;
    }

    public void setLeknw(String leknw) {
        Leknw = leknw;
    }

    public String getAueru() {
        return Aueru;
    }

    public void setAueru(String aueru) {
        Aueru = aueru;
    }

    public String getAusor() {
        return Ausor;
    }

    public void setAusor(String ausor) {
        Ausor = ausor;
    }

    public String getPernr() {
        return Pernr;
    }

    public void setPernr(String pernr) {
        Pernr = pernr;
    }

    public String getLoart() {
        return Loart;
    }

    public void setLoart(String loart) {
        Loart = loart;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRsnum() {
        return Rsnum;
    }

    public void setRsnum(String rsnum) {
        Rsnum = rsnum;
    }

    public String getRspos() {
        return Rspos;
    }

    public void setRspos(String rspos) {
        Rspos = rspos;
    }

    public String getPosnr() {
        return Posnr;
    }

    public void setPosnr(String posnr) {
        Posnr = posnr;
    }

    public String getMatnr() {
        return Matnr;
    }

    public void setMatnr(String matnr) {
        Matnr = matnr;
    }

    public String getBwart() {
        return Bwart;
    }

    public void setBwart(String bwart) {
        Bwart = bwart;
    }

    public String getWerks() {
        return Werks;
    }

    public void setWerks(String werks) {
        Werks = werks;
    }

    public String getLgort() {
        return Lgort;
    }

    public void setLgort(String lgort) {
        Lgort = lgort;
    }

    public String getErfmg() {
        return Erfmg;
    }

    public void setErfmg(String erfmg) {
        Erfmg = erfmg;
    }

    public String getErfme() {
        return Erfme;
    }

    public void setErfme(String erfme) {
        Erfme = erfme;
    }

    public String getExecStartDate() {
        return ExecStartDate;
    }

    public void setExecStartDate(String execStartDate) {
        ExecStartDate = execStartDate;
    }

    public String getExecStartTime() {
        return ExecStartTime;
    }

    public void setExecStartTime(String execStartTime) {
        ExecStartTime = execStartTime;
    }

    public String getExecFinDate() {
        return ExecFinDate;
    }

    public void setExecFinDate(String execFinDate) {
        ExecFinDate = execFinDate;
    }

    public String getExecFinTime() {
        return ExecFinTime;
    }

    public void setExecFinTime(String execFinTime) {
        ExecFinTime = execFinTime;
    }

    public ConfirmOrder_Prcbl() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Aufnr);
        dest.writeString(this.Vornr);
        dest.writeString(this.ConfNo);
        dest.writeString(this.ConfText);
        dest.writeString(this.ActWork);
        dest.writeString(this.UnWork);
        dest.writeString(this.PlanWork);
        dest.writeString(this.Learr);
        dest.writeString(this.Bemot);
        dest.writeString(this.Grund);
        dest.writeString(this.Leknw);
        dest.writeString(this.Aueru);
        dest.writeString(this.Ausor);
        dest.writeString(this.Pernr);
        dest.writeString(this.Loart);
        dest.writeString(this.Status);
        dest.writeString(this.Rsnum);
        dest.writeString(this.Rspos);
        dest.writeString(this.PostgDate);
        dest.writeString(this.Plant);
        dest.writeString(this.WorkCntr);
        dest.writeString(this.Posnr);
        dest.writeString(this.Matnr);
        dest.writeString(this.Bwart);
        dest.writeString(this.Werks);
        dest.writeString(this.Lgort);
        dest.writeString(this.Erfmg);
        dest.writeString(this.Erfme);
        dest.writeString(this.ExecStartDate);
        dest.writeString(this.ExecStartTime);
        dest.writeString(this.ExecFinDate);
        dest.writeString(this.ExecFinTime);
    }

    protected ConfirmOrder_Prcbl(Parcel in) {
        this.Aufnr = in.readString();
        this.Vornr = in.readString();
        this.ConfNo = in.readString();
        this.ConfText = in.readString();
        this.ActWork = in.readString();
        this.UnWork = in.readString();
        this.PlanWork = in.readString();
        this.Learr = in.readString();
        this.Bemot = in.readString();
        this.Grund = in.readString();
        this.Leknw = in.readString();
        this.Aueru = in.readString();
        this.Ausor = in.readString();
        this.Pernr = in.readString();
        this.Loart = in.readString();
        this.Status = in.readString();
        this.Rsnum = in.readString();
        this.Rspos = in.readString();
        this.PostgDate = in.readString();
        this.Plant = in.readString();
        this.WorkCntr = in.readString();
        this.Posnr = in.readString();
        this.Matnr = in.readString();
        this.Bwart = in.readString();
        this.Werks = in.readString();
        this.Lgort = in.readString();
        this.Erfmg = in.readString();
        this.Erfme = in.readString();
        this.ExecStartDate = in.readString();
        this.ExecStartTime = in.readString();
        this.ExecFinDate = in.readString();
        this.ExecFinTime = in.readString();
    }

    public static final Creator<ConfirmOrder_Prcbl> CREATOR = new Creator<ConfirmOrder_Prcbl>() {
        @Override
        public ConfirmOrder_Prcbl createFromParcel(Parcel source) {
            return new ConfirmOrder_Prcbl(source);
        }

        @Override
        public ConfirmOrder_Prcbl[] newArray(int size) {
            return new ConfirmOrder_Prcbl[size];
        }
    };
}
