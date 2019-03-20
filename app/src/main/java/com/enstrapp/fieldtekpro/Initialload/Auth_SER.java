package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Auth_SER {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }


    public class D {
        @SerializedName("results")
        @Expose
        private List<Result> results = null;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }


    public class Result {
        @SerializedName("EtBusf")
        @Expose
        private EtBusf EtBusf;
        @SerializedName("EsUser")
        @Expose
        private EsUser EsUser;
        @SerializedName("EtMusrf")
        @Expose
        private EtMusrf EtMusrf;
        @SerializedName("EtUsrf")
        @Expose
        private EtUsrf EtUsrf;

        public Auth_SER.EtUsrf getEtUsrf() {
            return EtUsrf;
        }

        public void setEtUsrf(Auth_SER.EtUsrf etUsrf) {
            EtUsrf = etUsrf;
        }

        public Auth_SER.EtMusrf getEtMusrf() {
            return EtMusrf;
        }

        public void setEtMusrf(Auth_SER.EtMusrf etMusrf) {
            EtMusrf = etMusrf;
        }

        public EsUser getEsUser() {
            return EsUser;
        }

        public void setEsUser(EsUser esUser) {
            EsUser = esUser;
        }

        public Auth_SER.EtBusf getEtBusf() {
            return EtBusf;
        }

        public void setEtBusf(Auth_SER.EtBusf etBusf) {
            EtBusf = etBusf;
        }
    }


    /*For EtUsrf*/
    public class EtUsrf {
        @SerializedName("results")
        @Expose
        private List<EtUsrf_Result> results = null;

        public List<EtUsrf_Result> getResults() {
            return results;
        }

        public void setResults(List<EtUsrf_Result> results) {
            this.results = results;
        }
    }

    public class EtUsrf_Result {
        @SerializedName("Mandt")
        @Expose
        private String Mandt;
        @SerializedName("Usgrp")
        @Expose
        private String Usgrp;
        @SerializedName("Zdoctype")
        @Expose
        private String Zdoctype;
        @SerializedName("Zactivity")
        @Expose
        private String Zactivity;
        @SerializedName("Inactive")
        @Expose
        private String Inactive;

        public String getMandt() {
            return Mandt;
        }

        public void setMandt(String mandt) {
            Mandt = mandt;
        }

        public String getUsgrp() {
            return Usgrp;
        }

        public void setUsgrp(String usgrp) {
            Usgrp = usgrp;
        }

        public String getZdoctype() {
            return Zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            Zdoctype = zdoctype;
        }

        public String getZactivity() {
            return Zactivity;
        }

        public void setZactivity(String zactivity) {
            Zactivity = zactivity;
        }

        public String getInactive() {
            return Inactive;
        }

        public void setInactive(String inactive) {
            Inactive = inactive;
        }
    }
    /*For EtUsrf*/


    /*For EtMusrf*/
    public class EtMusrf {
        @SerializedName("results")
        @Expose
        private List<EtMusrf_Result> results = null;

        public List<EtMusrf_Result> getResults() {
            return results;
        }

        public void setResults(List<EtMusrf_Result> results) {
            this.results = results;
        }
    }

    public class EtMusrf_Result {
        @SerializedName("Mandt")
        @Expose
        private String Mandt;
        @SerializedName("Muser")
        @Expose
        private String muser;
        @SerializedName("Zdoctype")
        @Expose
        private String Zdoctype;
        @SerializedName("Zactivity")
        @Expose
        private String Zactivity;
        @SerializedName("Inactive")
        @Expose
        private String Inactive;

        public String getMandt() {
            return Mandt;
        }

        public void setMandt(String mandt) {
            Mandt = mandt;
        }

        public String getMuser() {
            return muser;
        }

        public void setMuser(String muser) {
            this.muser = muser;
        }

        public String getZdoctype() {
            return Zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            Zdoctype = zdoctype;
        }

        public String getZactivity() {
            return Zactivity;
        }

        public void setZactivity(String zactivity) {
            Zactivity = zactivity;
        }

        public String getInactive() {
            return Inactive;
        }

        public void setInactive(String inactive) {
            Inactive = inactive;
        }
    }
    /*For EtMusrf*/


    /*For EsUser*/
    public class EsUser {
        @SerializedName("results")
        @Expose
        private List<EsUser_Result> results = null;

        public List<EsUser_Result> getResults() {
            return results;
        }

        public void setResults(List<EsUser_Result> results) {
            this.results = results;
        }
    }

    public class EsUser_Result {
        @SerializedName("Sapuser")
        @Expose
        private String sapuser;
        @SerializedName("Muser")
        @Expose
        private String muser;
        @SerializedName("Fname")
        @Expose
        private String fname;
        @SerializedName("Lname")
        @Expose
        private String lname;
        @SerializedName("Kostl")
        @Expose
        private String kostl;
        @SerializedName("Arbpl")
        @Expose
        private String arbpl;
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Ounit")
        @Expose
        private String ounit;
        @SerializedName("Pernr")
        @Expose
        private String pernr;
        @SerializedName("Ingrp")
        @Expose
        private String ingrp;
        @SerializedName("Parvw")
        @Expose
        private String parvw;
        @SerializedName("Parnr")
        @Expose
        private String parnr;
        @SerializedName("Suser")
        @Expose
        private String suser;
        @SerializedName("Ustyp")
        @Expose
        private String ustyp;
        @SerializedName("Usgrp")
        @Expose
        private String usgrp;

        public String getSapuser() {
            return sapuser;
        }

        public void setSapuser(String sapuser) {
            this.sapuser = sapuser;
        }

        public String getMuser() {
            return muser;
        }

        public void setMuser(String muser) {
            this.muser = muser;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getKostl() {
            return kostl;
        }

        public void setKostl(String kostl) {
            this.kostl = kostl;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getOunit() {
            return ounit;
        }

        public void setOunit(String ounit) {
            this.ounit = ounit;
        }

        public String getPernr() {
            return pernr;
        }

        public void setPernr(String pernr) {
            this.pernr = pernr;
        }

        public String getIngrp() {
            return ingrp;
        }

        public void setIngrp(String ingrp) {
            this.ingrp = ingrp;
        }

        public String getParvw() {
            return parvw;
        }

        public void setParvw(String parvw) {
            this.parvw = parvw;
        }

        public String getParnr() {
            return parnr;
        }

        public void setParnr(String parnr) {
            this.parnr = parnr;
        }

        public String getSuser() {
            return suser;
        }

        public void setSuser(String suser) {
            this.suser = suser;
        }

        public String getUstyp() {
            return ustyp;
        }

        public void setUstyp(String ustyp) {
            this.ustyp = ustyp;
        }

        public String getUsgrp() {
            return usgrp;
        }

        public void setUsgrp(String usgrp) {
            this.usgrp = usgrp;
        }
    }
    /*For EsUser*/


    /*For EtBusf*/
    public class EtBusf {
        @SerializedName("results")
        @Expose
        private List<EtBusf_Result> results = null;

        public List<EtBusf_Result> getResults() {
            return results;
        }

        public void setResults(List<EtBusf_Result> results) {
            this.results = results;
        }
    }

    public class EtBusf_Result {
        @SerializedName("Mandt")
        @Expose
        private String Mandt;
        @SerializedName("Usgrp")
        @Expose
        private String Usgrp;
        @SerializedName("Busftype")
        @Expose
        private String Busftype;
        @SerializedName("Active")
        @Expose
        private String Active;

        public String getMandt() {
            return Mandt;
        }

        public void setMandt(String mandt) {
            Mandt = mandt;
        }

        public String getUsgrp() {
            return Usgrp;
        }

        public void setUsgrp(String usgrp) {
            Usgrp = usgrp;
        }

        public String getBusftype() {
            return Busftype;
        }

        public void setBusftype(String busftype) {
            Busftype = busftype;
        }

        public String getActive() {
            return Active;
        }

        public void setActive(String active) {
            Active = active;
        }
    }
    /*For EtBusf*/


}