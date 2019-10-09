package com.enstrapp.fieldtekpro.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Model_Notif_Attachments_REST implements Parcelable
{
    @SerializedName("OBJTYPE")
    @Expose
    private String objtype;
    @SerializedName("ZOBJID")
    @Expose
    private String zobjid;
    @SerializedName("ZDOCTYPE")
    @Expose
    private String zdoctype;
    @SerializedName("ZDOCTYPEITEM")
    @Expose
    private String zdoctypeItem;
    @SerializedName("FILENAME")
    @Expose
    private String filename;
    @SerializedName("FILETYPE")
    @Expose
    private String filetype;
    @SerializedName("FSIZE")
    @Expose
    private String fsize;
    @SerializedName("CONTENT")
    @Expose
    private String content;
    @SerializedName("DOCID")
    @Expose
    private String docId;
    @SerializedName("DOCTYPE")
    @Expose
    private String docType;

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getZobjid() {
        return zobjid;
    }

    public void setZobjid(String zobjid) {
        this.zobjid = zobjid;
    }

    public String getZdoctype() {
        return zdoctype;
    }

    public void setZdoctype(String zdoctype) {
        this.zdoctype = zdoctype;
    }

    public String getZdoctypeItem() {
        return zdoctypeItem;
    }

    public void setZdoctypeItem(String zdoctypeItem) {
        this.zdoctypeItem = zdoctypeItem;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objtype);
        dest.writeString(this.zobjid);
        dest.writeString(this.zdoctype);
        dest.writeString(this.zdoctypeItem);
        dest.writeString(this.filename);
        dest.writeString(this.filetype);
        dest.writeString(this.fsize);
        dest.writeString(this.content);
        dest.writeString(this.docId);
        dest.writeString(this.docType);
    }

    public Model_Notif_Attachments_REST() {
    }

    protected Model_Notif_Attachments_REST(Parcel in) {
        this.objtype = in.readString();
        this.zobjid = in.readString();
        this.zdoctype = in.readString();
        this.zdoctypeItem = in.readString();
        this.filename = in.readString();
        this.filetype = in.readString();
        this.fsize = in.readString();
        this.content = in.readString();
        this.docId = in.readString();
        this.docType = in.readString();
    }

    public static final Creator<Model_Notif_Attachments_REST> CREATOR = new Creator<Model_Notif_Attachments_REST>() {
        @Override
        public Model_Notif_Attachments_REST createFromParcel(Parcel source) {
            return new Model_Notif_Attachments_REST(source);
        }

        @Override
        public Model_Notif_Attachments_REST[] newArray(int size) {
            return new Model_Notif_Attachments_REST[size];
        }
    };
}