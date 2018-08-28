package com.enstrapp.fieldtekpro.notifications;

import android.os.Parcel;
import android.os.Parcelable;

public class NotifCausCodActvPrcbl implements Parcelable {

    private boolean selected;
    private String UUID;
    private String Qmnum;
    private String ItmKey;
    private String ItmPrtGrp;
    private String PrtGrpTxt;
    private String ItmPrtCod;
    private String PrtCodTxt;
    private String ItmDefectGrp;
    private String DefectGrpTxt;
    private String ItmDefectCod;
    private String DefectCodTxt;
    private String ItmDefectShTxt;
    private String CausKey;
    private String CausGrp;
    private String CausGrpTxt;
    private String CausCod;
    private String CausCodTxt;
    private String CausShTxt;
    private String ActvKey;
    private String ActvGrp;
    private String ActGrpTxt;
    private String ActvCod;
    private String ActvCodTxt;
    private String ActvShTxt;
    private String Usr01;
    private String Usr02;
    private String Usr03;
    private String Usr04;
    private String Usr05;
    private String Status;
    private String Fields;
    private String Action;

    public String getFields() {
        return Fields;
    }

    public void setFields(String fields) {
        Fields = fields;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public NotifCausCodActvPrcbl() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getActvKey() {
        return ActvKey;
    }

    public void setActvKey(String actvKey) {
        ActvKey = actvKey;
    }

    public String getActvGrp() {
        return ActvGrp;
    }

    public void setActvGrp(String actvGrp) {
        ActvGrp = actvGrp;
    }

    public String getActGrpTxt() {
        return ActGrpTxt;
    }

    public void setActGrpTxt(String actGrpTxt) {
        ActGrpTxt = actGrpTxt;
    }

    public String getActvCod() {
        return ActvCod;
    }

    public void setActvCod(String actvCod) {
        ActvCod = actvCod;
    }

    public String getActvCodTxt() {
        return ActvCodTxt;
    }

    public void setActvCodTxt(String actvCodTxt) {
        ActvCodTxt = actvCodTxt;
    }

    public String getActvShTxt() {
        return ActvShTxt;
    }

    public void setActvShTxt(String actvShTxt) {
        ActvShTxt = actvShTxt;
    }

    public String getDefectCodTxt() {
        return DefectCodTxt;
    }

    public void setDefectCodTxt(String defectCodTxt) {
        DefectCodTxt = defectCodTxt;
    }

    public String getItmDefectCod() {
        return ItmDefectCod;

    }

    public void setItmDefectCod(String itmDefectCod) {
        ItmDefectCod = itmDefectCod;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getQmnum() {
        return Qmnum;
    }

    public void setQmnum(String qmnum) {
        Qmnum = qmnum;
    }

    public String getItmKey() {
        return ItmKey;
    }

    public void setItmKey(String itmKey) {
        ItmKey = itmKey;
    }

    public String getItmPrtGrp() {
        return ItmPrtGrp;
    }

    public void setItmPrtGrp(String itmPrtGrp) {
        ItmPrtGrp = itmPrtGrp;
    }

    public String getPrtGrpTxt() {
        return PrtGrpTxt;
    }

    public void setPrtGrpTxt(String prtGrpTxt) {
        PrtGrpTxt = prtGrpTxt;
    }

    public String getItmPrtCod() {
        return ItmPrtCod;
    }

    public void setItmPrtCod(String itmPrtCod) {
        ItmPrtCod = itmPrtCod;
    }

    public String getPrtCodTxt() {
        return PrtCodTxt;
    }

    public void setPrtCodTxt(String prtCodTxt) {
        PrtCodTxt = prtCodTxt;
    }

    public String getItmDefectGrp() {
        return ItmDefectGrp;
    }

    public void setItmDefectGrp(String itmDefectGrp) {
        ItmDefectGrp = itmDefectGrp;
    }

    public String getDefectGrpTxt() {
        return DefectGrpTxt;
    }

    public void setDefectGrpTxt(String defectGrpTxt) {
        DefectGrpTxt = defectGrpTxt;
    }

    public String getItmDefectShTxt() {
        return ItmDefectShTxt;
    }

    public void setItmDefectShTxt(String itmDefectShTxt) {
        ItmDefectShTxt = itmDefectShTxt;
    }

    public String getCausKey() {
        return CausKey;
    }

    public void setCausKey(String causKey) {
        CausKey = causKey;
    }

    public String getCausGrp() {
        return CausGrp;
    }

    public void setCausGrp(String causGrp) {
        CausGrp = causGrp;
    }

    public String getCausGrpTxt() {
        return CausGrpTxt;
    }

    public void setCausGrpTxt(String causGrpTxt) {
        CausGrpTxt = causGrpTxt;
    }

    public String getCausCod() {
        return CausCod;
    }

    public void setCausCod(String causCod) {
        CausCod = causCod;
    }

    public String getCausCodTxt() {
        return CausCodTxt;
    }

    public void setCausCodTxt(String causCodTxt) {
        CausCodTxt = causCodTxt;
    }

    public String getCausShTxt() {
        return CausShTxt;
    }

    public void setCausShTxt(String causShTxt) {
        CausShTxt = causShTxt;
    }

    public String getUsr01() {
        return Usr01;
    }

    public void setUsr01(String usr01) {
        Usr01 = usr01;
    }

    public String getUsr02() {
        return Usr02;
    }

    public void setUsr02(String usr02) {
        Usr02 = usr02;
    }

    public String getUsr03() {
        return Usr03;
    }

    public void setUsr03(String usr03) {
        Usr03 = usr03;
    }

    public String getUsr04() {
        return Usr04;
    }

    public void setUsr04(String usr04) {
        Usr04 = usr04;
    }

    public String getUsr05() {
        return Usr05;
    }

    public void setUsr05(String usr05) {
        Usr05 = usr05;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
        dest.writeString(this.ItmKey);
        dest.writeString(this.ItmPrtGrp);
        dest.writeString(this.PrtGrpTxt);
        dest.writeString(this.ItmPrtCod);
        dest.writeString(this.PrtCodTxt);
        dest.writeString(this.ItmDefectGrp);
        dest.writeString(this.DefectGrpTxt);
        dest.writeString(this.ItmDefectCod);
        dest.writeString(this.DefectCodTxt);
        dest.writeString(this.ItmDefectShTxt);
        dest.writeString(this.CausKey);
        dest.writeString(this.CausGrp);
        dest.writeString(this.CausGrpTxt);
        dest.writeString(this.CausCod);
        dest.writeString(this.CausCodTxt);
        dest.writeString(this.CausShTxt);
        dest.writeString(this.ActvKey);
        dest.writeString(this.ActvGrp);
        dest.writeString(this.ActGrpTxt);
        dest.writeString(this.ActvCod);
        dest.writeString(this.ActvCodTxt);
        dest.writeString(this.ActvShTxt);
        dest.writeString(this.Usr01);
        dest.writeString(this.Usr02);
        dest.writeString(this.Usr03);
        dest.writeString(this.Usr04);
        dest.writeString(this.Usr05);
        dest.writeString(this.Status);
        dest.writeString(this.Fields);
        dest.writeString(this.Action);
    }

    protected NotifCausCodActvPrcbl(Parcel in) {
        this.selected = in.readByte() != 0;
        this.UUID = in.readString();
        this.Qmnum = in.readString();
        this.ItmKey = in.readString();
        this.ItmPrtGrp = in.readString();
        this.PrtGrpTxt = in.readString();
        this.ItmPrtCod = in.readString();
        this.PrtCodTxt = in.readString();
        this.ItmDefectGrp = in.readString();
        this.DefectGrpTxt = in.readString();
        this.ItmDefectCod = in.readString();
        this.DefectCodTxt = in.readString();
        this.ItmDefectShTxt = in.readString();
        this.CausKey = in.readString();
        this.CausGrp = in.readString();
        this.CausGrpTxt = in.readString();
        this.CausCod = in.readString();
        this.CausCodTxt = in.readString();
        this.CausShTxt = in.readString();
        this.ActvKey = in.readString();
        this.ActvGrp = in.readString();
        this.ActGrpTxt = in.readString();
        this.ActvCod = in.readString();
        this.ActvCodTxt = in.readString();
        this.ActvShTxt = in.readString();
        this.Usr01 = in.readString();
        this.Usr02 = in.readString();
        this.Usr03 = in.readString();
        this.Usr04 = in.readString();
        this.Usr05 = in.readString();
        this.Status = in.readString();
        this.Fields = in.readString();
        this.Action = in.readString();
    }

    public static final Creator<NotifCausCodActvPrcbl> CREATOR = new Creator<NotifCausCodActvPrcbl>() {
        @Override
        public NotifCausCodActvPrcbl createFromParcel(Parcel source) {
            return new NotifCausCodActvPrcbl(source);
        }

        @Override
        public NotifCausCodActvPrcbl[] newArray(int size) {
            return new NotifCausCodActvPrcbl[size];
        }
    };
}
