package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdrTagUnTagTextPrcbl implements Parcelable {

    private String aufnr;
    private String wcnr;
    private String objtype;
    private String formatCol;
    private String textLine;
    private String action;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getWcnr() {
        return wcnr;
    }

    public void setWcnr(String wcnr) {
        this.wcnr = wcnr;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getFormatCol() {
        return formatCol;
    }

    public void setFormatCol(String formatCol) {
        this.formatCol = formatCol;
    }

    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
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
        dest.writeString(this.aufnr);
        dest.writeString(this.wcnr);
        dest.writeString(this.objtype);
        dest.writeString(this.formatCol);
        dest.writeString(this.textLine);
        dest.writeString(this.action);
    }

    public OrdrTagUnTagTextPrcbl() {
    }

    protected OrdrTagUnTagTextPrcbl(Parcel in) {
        this.aufnr = in.readString();
        this.wcnr = in.readString();
        this.objtype = in.readString();
        this.formatCol = in.readString();
        this.textLine = in.readString();
        this.action = in.readString();
    }

    public static final Creator<OrdrTagUnTagTextPrcbl> CREATOR = new Creator<OrdrTagUnTagTextPrcbl>() {
        @Override
        public OrdrTagUnTagTextPrcbl createFromParcel(Parcel source) {
            return new OrdrTagUnTagTextPrcbl(source);
        }

        @Override
        public OrdrTagUnTagTextPrcbl[] newArray(int size) {
            return new OrdrTagUnTagTextPrcbl[size];
        }
    };
}
