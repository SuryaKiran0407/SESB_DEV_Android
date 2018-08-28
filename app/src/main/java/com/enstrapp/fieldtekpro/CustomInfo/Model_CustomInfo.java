package com.enstrapp.fieldtekpro.CustomInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Model_CustomInfo
{
    @SerializedName("Zdoctype")
    @Expose
    private String zdoctype;
    @SerializedName("ZdoctypeItem")
    @Expose
    private String zdoctypeItem;
    @SerializedName("Tabname")
    @Expose
    private String tabname;
    @SerializedName("Fieldname")
    @Expose
    private String fieldname;
    @SerializedName("Datatype")
    @Expose
    private String datatype;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Flabel")
    @Expose
    private String flabel;
    @SerializedName("Sequence")
    @Expose
    private String sequence;
    @SerializedName("Length")
    @Expose
    private String length;

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

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlabel() {
        return flabel;
    }

    public void setFlabel(String flabel) {
        this.flabel = flabel;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

}