package com.enstrapp.fieldtekpro.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notif_EtDocs_Parcelable implements  Parcelable
{

    @SerializedName("Selected")
    @Expose
    private boolean selected;

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("Zobjid")
    @Expose
    private String zobjid;
    @SerializedName("Zdoctype")
    @Expose
    private String zdoctype;
    @SerializedName("ZdoctypeItem")
    @Expose
    private String zdoctypeitem;
    @SerializedName("file_path")
    @Expose
    private String filepath;
    @SerializedName("Filename")
    @Expose
    private String filename;
    @SerializedName("Filetype")
    @Expose
    private String filetype;
    @SerializedName("Fsize")
    @Expose
    private String fsize;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("DocId")
    @Expose
    private String docid;
    @SerializedName("DocType")
    @Expose
    private String doctype;
    @SerializedName("Objtype")
    @Expose
    private String objtype;
    @SerializedName("Status")
    @Expose
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Notif_EtDocs_Parcelable() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFilepath() {

        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public String getZdoctypeitem() {
        return zdoctypeitem;
    }

    public void setZdoctypeitem(String zdoctypeitem) {
        this.zdoctypeitem = zdoctypeitem;
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

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.uuid);
        dest.writeString(this.zobjid);
        dest.writeString(this.zdoctype);
        dest.writeString(this.zdoctypeitem);
        dest.writeString(this.filepath);
        dest.writeString(this.filename);
        dest.writeString(this.filetype);
        dest.writeString(this.fsize);
        dest.writeString(this.content);
        dest.writeString(this.docid);
        dest.writeString(this.doctype);
        dest.writeString(this.objtype);
        dest.writeString(this.Status);
    }

    protected Notif_EtDocs_Parcelable(Parcel in) {
        this.selected = in.readByte() != 0;
        this.uuid = in.readString();
        this.zobjid = in.readString();
        this.zdoctype = in.readString();
        this.zdoctypeitem = in.readString();
        this.filepath = in.readString();
        this.filename = in.readString();
        this.filetype = in.readString();
        this.fsize = in.readString();
        this.content = in.readString();
        this.docid = in.readString();
        this.doctype = in.readString();
        this.objtype = in.readString();
        this.Status = in.readString();
    }

    public static final Creator<Notif_EtDocs_Parcelable> CREATOR = new Creator<Notif_EtDocs_Parcelable>() {
        @Override
        public Notif_EtDocs_Parcelable createFromParcel(Parcel source) {
            return new Notif_EtDocs_Parcelable(source);
        }

        @Override
        public Notif_EtDocs_Parcelable[] newArray(int size) {
            return new Notif_EtDocs_Parcelable[size];
        }
    };
}
