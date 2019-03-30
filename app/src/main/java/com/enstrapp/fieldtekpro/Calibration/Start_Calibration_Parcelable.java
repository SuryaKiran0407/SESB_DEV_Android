package com.enstrapp.fieldtekpro.Calibration;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VENKATA SURYA KIRAN on 11-Jun-18.
 */

public class Start_Calibration_Parcelable implements Parcelable {
    private String Merknr;
    private String prueflos;
    private String vorglfnr;
    private String VERWMERKM;
    private String MSEHI;
    private String KURZTEXT;
    private String QUALITAT;
    private String RESULT;
    private String PRUEFBEMKT;
    private String TOLERANZUB;
    private String TOLERANZOB;
    private String ANZWERTG;
    private String ISTSTPUMF;
    private String MSEHL;
    private String AUSWMENGE1;
    private String WERKS;
    private String PRUEFER;
    private String Valuation;
    private String uuid;
    private String QUANTITAT;
    private String EQUNR;

    public String getEQUNR() {
        return EQUNR;
    }

    public void setEQUNR(String EQUNR) {
        this.EQUNR = EQUNR;
    }

    public String getMerknr() {
        return Merknr;
    }

    public void setMerknr(String merknr) {
        Merknr = merknr;
    }
    public String getQUANTITAT() {
        return QUANTITAT;
    }

    public void setQUANTITAT(String QUANTITAT) {
        this.QUANTITAT = QUANTITAT;
    }

    public String getPrueflos() {
        return prueflos;
    }

    public void setPrueflos(String prueflos) {
        this.prueflos = prueflos;
    }

    public String getVorglfnr() {
        return vorglfnr;
    }

    public void setVorglfnr(String vorglfnr) {
        this.vorglfnr = vorglfnr;
    }

    public String getVERWMERKM() {
        return VERWMERKM;
    }

    public void setVERWMERKM(String VERWMERKM) {
        this.VERWMERKM = VERWMERKM;
    }

    public String getMSEHI() {
        return MSEHI;
    }

    public void setMSEHI(String MSEHI) {
        this.MSEHI = MSEHI;
    }

    public String getKURZTEXT() {
        return KURZTEXT;
    }

    public void setKURZTEXT(String KURZTEXT) {
        this.KURZTEXT = KURZTEXT;
    }

    public String getQUALITAT() {
        return QUALITAT;
    }

    public void setQUALITAT(String QUALITAT) {
        this.QUALITAT = QUALITAT;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getPRUEFBEMKT() {
        return PRUEFBEMKT;
    }

    public void setPRUEFBEMKT(String PRUEFBEMKT) {
        this.PRUEFBEMKT = PRUEFBEMKT;
    }

    public String getTOLERANZUB() {
        return TOLERANZUB;
    }

    public void setTOLERANZUB(String TOLERANZUB) {
        this.TOLERANZUB = TOLERANZUB;
    }

    public String getTOLERANZOB() {
        return TOLERANZOB;
    }

    public void setTOLERANZOB(String TOLERANZOB) {
        this.TOLERANZOB = TOLERANZOB;
    }

    public String getANZWERTG() {
        return ANZWERTG;
    }

    public void setANZWERTG(String ANZWERTG) {
        this.ANZWERTG = ANZWERTG;
    }

    public String getISTSTPUMF() {
        return ISTSTPUMF;
    }

    public void setISTSTPUMF(String ISTSTPUMF) {
        this.ISTSTPUMF = ISTSTPUMF;
    }

    public String getMSEHL() {
        return MSEHL;
    }

    public void setMSEHL(String MSEHL) {
        this.MSEHL = MSEHL;
    }

    public String getAUSWMENGE1() {
        return AUSWMENGE1;
    }

    public void setAUSWMENGE1(String AUSWMENGE1) {
        this.AUSWMENGE1 = AUSWMENGE1;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getPRUEFER() {
        return PRUEFER;
    }

    public void setPRUEFER(String PRUEFER) {
        this.PRUEFER = PRUEFER;
    }

    public String getValuation() {
        return Valuation;
    }

    public void setValuation(String valuation) {
        Valuation = valuation;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Start_Calibration_Parcelable() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Merknr);
        dest.writeString(this.prueflos);
        dest.writeString(this.vorglfnr);
        dest.writeString(this.VERWMERKM);
        dest.writeString(this.MSEHI);
        dest.writeString(this.KURZTEXT);
        dest.writeString(this.QUALITAT);
        dest.writeString(this.RESULT);
        dest.writeString(this.PRUEFBEMKT);
        dest.writeString(this.TOLERANZUB);
        dest.writeString(this.TOLERANZOB);
        dest.writeString(this.ANZWERTG);
        dest.writeString(this.ISTSTPUMF);
        dest.writeString(this.MSEHL);
        dest.writeString(this.AUSWMENGE1);
        dest.writeString(this.WERKS);
        dest.writeString(this.PRUEFER);
        dest.writeString(this.Valuation);
        dest.writeString(this.uuid);
        dest.writeString(this.QUANTITAT);
        dest.writeString(this.EQUNR);
    }

    protected Start_Calibration_Parcelable(Parcel in) {
        this.Merknr = in.readString();
        this.prueflos = in.readString();
        this.vorglfnr = in.readString();
        this.VERWMERKM = in.readString();
        this.MSEHI = in.readString();
        this.KURZTEXT = in.readString();
        this.QUALITAT = in.readString();
        this.RESULT = in.readString();
        this.PRUEFBEMKT = in.readString();
        this.TOLERANZUB = in.readString();
        this.TOLERANZOB = in.readString();
        this.ANZWERTG = in.readString();
        this.ISTSTPUMF = in.readString();
        this.MSEHL = in.readString();
        this.AUSWMENGE1 = in.readString();
        this.WERKS = in.readString();
        this.PRUEFER = in.readString();
        this.Valuation = in.readString();
        this.uuid = in.readString();
        this.QUANTITAT = in.readString();
        this.EQUNR = in.readString();
    }

    public static final Creator<Start_Calibration_Parcelable> CREATOR = new Creator<Start_Calibration_Parcelable>() {
        @Override
        public Start_Calibration_Parcelable createFromParcel(Parcel source) {
            return new Start_Calibration_Parcelable(source);
        }

        @Override
        public Start_Calibration_Parcelable[] newArray(int size) {
            return new Start_Calibration_Parcelable[size];
        }
    };
}
