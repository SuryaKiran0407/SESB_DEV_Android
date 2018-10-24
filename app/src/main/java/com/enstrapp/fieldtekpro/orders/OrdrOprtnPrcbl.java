package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;


public class OrdrOprtnPrcbl implements Parcelable {

    public boolean selected;
    public String ordrId;
    public String ordrSatus;
    public String oprtnId;
    public String oprtnShrtTxt;
    public String oprtnLngTxt;
    public String duration;
    public String durationUnit;
    public String plantId;
    public String plantTxt;
    public String wrkCntrId;
    public String wrkCntrTxt;
    public String cntrlKeyId;
    public String cntrlKeyTxt;
    public String aueru;
    public String usr01;
    public String status;
    public String larnt;
    public String larnt_text;
    public String fsavd;
    public String ssedd;
    public String rueck;
    public String usr02;
    public String usr03;
    public String usr04;

    public String getUsr04() {
        return usr04;
    }

    public void setUsr04(String usr04) {
        this.usr04 = usr04;
    }

    public String getLarnt_text() {
        return larnt_text;
    }

    public void setLarnt_text(String larnt_text) {
        this.larnt_text = larnt_text;
    }

    public String getUsr02() {
        return usr02;
    }

    public void setUsr02(String usr02) {
        this.usr02 = usr02;
    }

    public String getUsr03() {
        return usr03;
    }

    public void setUsr03(String usr03) {
        this.usr03 = usr03;
    }

    public String getLarnt() {
        return larnt;
    }

    public void setLarnt(String larnt) {
        this.larnt = larnt;
    }

    public String getFsavd() {
        return fsavd;
    }

    public void setFsavd(String fsavd) {
        this.fsavd = fsavd;
    }

    public String getSsedd() {
        return ssedd;
    }

    public void setSsedd(String ssedd) {
        this.ssedd = ssedd;
    }

    public String getRueck() {
        return rueck;
    }

    public void setRueck(String rueck) {
        this.rueck = rueck;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getOrdrId() {
        return ordrId;
    }

    public void setOrdrId(String ordrId) {
        this.ordrId = ordrId;
    }

    public String getOrdrSatus() {
        return ordrSatus;
    }

    public void setOrdrSatus(String ordrSatus) {
        this.ordrSatus = ordrSatus;
    }

    public String getOprtnId() {
        return oprtnId;
    }

    public void setOprtnId(String oprtnId) {
        this.oprtnId = oprtnId;
    }

    public String getOprtnShrtTxt() {
        return oprtnShrtTxt;
    }

    public void setOprtnShrtTxt(String oprtnShrtTxt) {
        this.oprtnShrtTxt = oprtnShrtTxt;
    }

    public String getOprtnLngTxt() {
        return oprtnLngTxt;
    }

    public void setOprtnLngTxt(String oprtnLngTxt) {
        this.oprtnLngTxt = oprtnLngTxt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getPlantTxt() {
        return plantTxt;
    }

    public void setPlantTxt(String plantTxt) {
        this.plantTxt = plantTxt;
    }

    public String getWrkCntrId() {
        return wrkCntrId;
    }

    public void setWrkCntrId(String wrkCntrId) {
        this.wrkCntrId = wrkCntrId;
    }

    public String getWrkCntrTxt() {
        return wrkCntrTxt;
    }

    public void setWrkCntrTxt(String wrkCntrTxt) {
        this.wrkCntrTxt = wrkCntrTxt;
    }

    public String getCntrlKeyId() {
        return cntrlKeyId;
    }

    public void setCntrlKeyId(String cntrlKeyId) {
        this.cntrlKeyId = cntrlKeyId;
    }

    public String getCntrlKeyTxt() {
        return cntrlKeyTxt;
    }

    public void setCntrlKeyTxt(String cntrlKeyTxt) {
        this.cntrlKeyTxt = cntrlKeyTxt;
    }

    public String getAueru() {
        return aueru;
    }

    public void setAueru(String aueru) {
        this.aueru = aueru;
    }

    public String getUsr01() {
        return usr01;
    }

    public void setUsr01(String usr01) {
        this.usr01 = usr01;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    /* @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder setSelected(boolean value);
        public abstract Builder setOrdrId(String value);
        public abstract Builder setOrdrSatus(String value);
        public abstract Builder setOprtnId(String value);
        public abstract Builder setOprtnShrtTxt(String value);
        public abstract Builder setOprtnLngTxt(String value);
        public abstract Builder setDuration(String value);
        public abstract Builder setDurationUnit(String value);
        public abstract Builder setPlantId(String value);
        public abstract Builder setPlantTxt(String value);
        public abstract Builder setWrkCntrId(String value);
        public abstract Builder setWrkCntrTxt(String value);
        public abstract Builder setCntrlKeyId(String value);
        public abstract Builder setCntrlKeyTxt(String value);
        public abstract Builder setAueru(String value);
        public abstract Builder setUsr01(String value);
        public abstract Builder setStatus(String value);
        public abstract OrdrOprtnPrcbl build();
    }*/

    /*public static Builder builder() {
        return new AutoValue_OrdrOprtnPrcbl.Builder();
    }

    public abstract Builder toBuilder();

    public OrdrOprtnPrcbl withSelected(boolean selected) {
        return toBuilder().setSelected(selected).build();
    }*/

    /*public static OrdrOprtnPrcbl create(boolean selected,
                                        String ordrId,
                                        String ordrSatus,
                                        String oprtnId,
                                        String oprtnShrtTxt,
                                        String oprtnLngTxt,
                                        String duration,
                                        String durationUnit,
                                        String plantId,
                                        String plantTxt,
                                        String wrkCntrId,
                                        String wrkCntrTxt,
                                        String cntrlKeyId,
                                        String cntrlKeyTxt,
                                        String aueru,
                                        String usr01,
                                        String status) {
        return new AutoValue_OrdrOprtnPrcbl(selected, ordrId, ordrSatus, oprtnId, oprtnShrtTxt, oprtnLngTxt, duration, durationUnit,
                plantId, plantTxt, wrkCntrId, wrkCntrTxt, cntrlKeyId, cntrlKeyTxt, aueru, usr01, status);
    }*/

    public OrdrOprtnPrcbl() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.ordrId);
        dest.writeString(this.ordrSatus);
        dest.writeString(this.oprtnId);
        dest.writeString(this.oprtnShrtTxt);
        dest.writeString(this.oprtnLngTxt);
        dest.writeString(this.duration);
        dest.writeString(this.durationUnit);
        dest.writeString(this.plantId);
        dest.writeString(this.plantTxt);
        dest.writeString(this.wrkCntrId);
        dest.writeString(this.wrkCntrTxt);
        dest.writeString(this.cntrlKeyId);
        dest.writeString(this.cntrlKeyTxt);
        dest.writeString(this.aueru);
        dest.writeString(this.usr01);
        dest.writeString(this.status);
        dest.writeString(this.larnt);
        dest.writeString(this.larnt_text);
        dest.writeString(this.fsavd);
        dest.writeString(this.ssedd);
        dest.writeString(this.rueck);
        dest.writeString(this.usr02);
        dest.writeString(this.usr03);
        dest.writeString(this.usr04);
    }

    protected OrdrOprtnPrcbl(Parcel in) {
        this.selected = in.readByte() != 0;
        this.ordrId = in.readString();
        this.ordrSatus = in.readString();
        this.oprtnId = in.readString();
        this.oprtnShrtTxt = in.readString();
        this.oprtnLngTxt = in.readString();
        this.duration = in.readString();
        this.durationUnit = in.readString();
        this.plantId = in.readString();
        this.plantTxt = in.readString();
        this.wrkCntrId = in.readString();
        this.wrkCntrTxt = in.readString();
        this.cntrlKeyId = in.readString();
        this.cntrlKeyTxt = in.readString();
        this.aueru = in.readString();
        this.usr01 = in.readString();
        this.status = in.readString();
        this.larnt = in.readString();
        this.larnt_text = in.readString();
        this.fsavd = in.readString();
        this.ssedd = in.readString();
        this.rueck = in.readString();
        this.usr02 = in.readString();
        this.usr03 = in.readString();
        this.usr04 = in.readString();
    }

    public static final Creator<OrdrOprtnPrcbl> CREATOR = new Creator<OrdrOprtnPrcbl>() {
        @Override
        public OrdrOprtnPrcbl createFromParcel(Parcel source) {
            return new OrdrOprtnPrcbl(source);
        }

        @Override
        public OrdrOprtnPrcbl[] newArray(int size) {
            return new OrdrOprtnPrcbl[size];
        }
    };
}

