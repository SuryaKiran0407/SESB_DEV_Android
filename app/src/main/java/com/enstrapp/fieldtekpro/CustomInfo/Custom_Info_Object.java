package com.enstrapp.fieldtekpro.CustomInfo;


public class Custom_Info_Object
{
    private String Fieldname, ZdoctypeItem, Datatype, Tabname, Zdoctype, Sequence, Flabel, Spras, Length, Value;
    public String getFieldname() {
        return Fieldname;
    }
    public void setFieldname(String fieldname) {
        Fieldname = fieldname;
    }
    public String getZdoctypeItem() {
        return ZdoctypeItem;
    }
    public void setZdoctypeItem(String zdoctypeItem) {
        ZdoctypeItem = zdoctypeItem;
    }
    public String getDatatype() {
        return Datatype;
    }
    public void setDatatype(String datatype) {
        Datatype = datatype;
    }
    public String getTabname() {
        return Tabname;
    }
    public void setTabname(String tabname) {
        Tabname = tabname;
    }
    public String getZdoctype() {
        return Zdoctype;
    }
    public void setZdoctype(String zdoctype) {
        Zdoctype = zdoctype;
    }
    public String getSequence() {
        return Sequence;
    }
    public void setSequence(String sequence) {
        Sequence = sequence;
    }
    public String getFlabel() {
        return Flabel;
    }
    public void setFlabel(String flabel) {
        Flabel = flabel;
    }
    public String getSpras() {
        return Spras;
    }
    public void setSpras(String spras) {
        Spras = spras;
    }
    public String getLength() {
        return Length;
    }
    public void setLength(String length) {
        Length = length;
    }
    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }
    public Custom_Info_Object(String Fieldname, String ZdoctypeItem, String Datatype, String Tabname, String Zdoctype, String Sequence, String Flabel, String Spras, String Length, String Value)
    {
        this.Fieldname = Fieldname;
        this.ZdoctypeItem = ZdoctypeItem;
        this.Datatype = Datatype;
        this.Tabname = Tabname;
        this.Zdoctype = Zdoctype;
        this.Sequence = Sequence;
        this.Flabel = Flabel;
        this.Spras = Spras;
        this.Length = Length;
        this.Value = Value;
    }
}
