package com.enstrapp.fieldtekpro.Parcelable_Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class EtDocsPrcbl implements Parcelable {

    private String Zobjid;
    private String Zdoctype;
    private String ZdoctypeItem;
    private String Filename;
    private String Filetype;
    private String Fsize;
    private String Content;
    private String DocId;
    private String DocType;
    private String Objtype;

    public String getZobjid() {
        return Zobjid;
    }

    public void setZobjid(String zobjid) {
        Zobjid = zobjid;
    }

    public String getZdoctype() {
        return Zdoctype;
    }

    public void setZdoctype(String zdoctype) {
        Zdoctype = zdoctype;
    }

    public String getZdoctypeItem() {
        return ZdoctypeItem;
    }

    public void setZdoctypeItem(String zdoctypeItem) {
        ZdoctypeItem = zdoctypeItem;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFiletype() {
        return Filetype;
    }

    public void setFiletype(String filetype) {
        Filetype = filetype;
    }

    public String getFsize() {
        return Fsize;
    }

    public void setFsize(String fsize) {
        Fsize = fsize;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDocId() {
        return DocId;
    }

    public void setDocId(String docId) {
        DocId = docId;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getObjtype() {
        return Objtype;
    }

    public void setObjtype(String objtype) {
        Objtype = objtype;
    }

    public EtDocsPrcbl() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Zobjid);
        dest.writeString(this.Zdoctype);
        dest.writeString(this.ZdoctypeItem);
        dest.writeString(this.Filename);
        dest.writeString(this.Filetype);
        dest.writeString(this.Fsize);
        dest.writeString(this.Content);
        dest.writeString(this.DocId);
        dest.writeString(this.DocType);
        dest.writeString(this.Objtype);
    }

    protected EtDocsPrcbl(Parcel in) {
        this.Zobjid = in.readString();
        this.Zdoctype = in.readString();
        this.ZdoctypeItem = in.readString();
        this.Filename = in.readString();
        this.Filetype = in.readString();
        this.Fsize = in.readString();
        this.Content = in.readString();
        this.DocId = in.readString();
        this.DocType = in.readString();
        this.Objtype = in.readString();
    }

    public static final Creator<EtDocsPrcbl> CREATOR = new Creator<EtDocsPrcbl>() {
        @Override
        public EtDocsPrcbl createFromParcel(Parcel source) {
            return new EtDocsPrcbl(source);
        }

        @Override
        public EtDocsPrcbl[] newArray(int size) {
            return new EtDocsPrcbl[size];
        }
    };
}
