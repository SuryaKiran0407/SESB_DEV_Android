package com.enstrapp.fieldtekpro.orders;

public class WcdDup_Object {
    private String Aufnr;
    private String Stxt;
    private String Sysst;
    private String Wcnr;

    public WcdDup_Object(String aufnr, String stxt, String sysst, String wcnr) {
        Aufnr = aufnr;
        Stxt = stxt;
        Sysst = sysst;
        Wcnr = wcnr;
    }

    public String getAufnr() {

        return Aufnr;
    }

    public void setAufnr(String aufnr) {
        Aufnr = aufnr;
    }

    public String getStxt() {
        return Stxt;
    }

    public void setStxt(String stxt) {
        Stxt = stxt;
    }

    public String getSysst() {
        return Sysst;
    }

    public void setSysst(String sysst) {
        Sysst = sysst;
    }

    public String getWcnr() {
        return Wcnr;
    }

    public void setWcnr(String wcnr) {
        Wcnr = wcnr;
    }
}
