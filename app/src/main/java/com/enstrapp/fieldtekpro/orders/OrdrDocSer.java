package com.enstrapp.fieldtekpro.orders;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrDocSer {
    @SerializedName("Objtype")
    @Expose
    private String objtype;
    @SerializedName("Zobjid")
    @Expose
    private String zobjid;
    @SerializedName("Zdoctype")
    @Expose
    private String zdoctype;
    @SerializedName("ZdoctypeItem")
    @Expose
    private String zdoctypeItem;
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
    private String docId;
    @SerializedName("DocType")
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
}

