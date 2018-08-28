package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VHLP_WCM_SER {

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
        @SerializedName("EtWCMUsages")
        @Expose
        private EtWCMUsages EtWCMUsages;
        @SerializedName("EtWCMBegru")
        @Expose
        private EtWCMBegru EtWCMBegru;
        @SerializedName("EtWCMTgtyp")
        @Expose
        private EtWCMTgtyp EtWCMTgtyp;
        @SerializedName("EtWCMTypes")
        @Expose
        private EtWCMTypes EtWCMTypes;
        @SerializedName("EtWCMWcvp6")
        @Expose
        private EtWCMWcvp6 EtWCMWcvp6;
        @SerializedName("EtWCMWork")
        @Expose
        private EtWCMWork EtWCMWork;
        @SerializedName("EtWCMReqm")
        @Expose
        private EtWCMReqm EtWCMReqm;
        @SerializedName("EtWCMUsgrp")
        @Expose
        private EtWCMUsgrp EtWCMUsgrp;
        @SerializedName("EtWCMWcco")
        @Expose
        private EtWcmWcco EtWcmWcco;

        public EtWcmWcco getEtWcmWcco() {
            return EtWcmWcco;
        }

        public void setEtWcmWcco(EtWcmWcco etWcmWcco) {
            EtWcmWcco = etWcmWcco;
        }

        public EtWCMUsgrp getEtWCMUsgrp() {
            return EtWCMUsgrp;
        }

        public void setEtWCMUsgrp(EtWCMUsgrp etWCMUsgrp) {
            EtWCMUsgrp = etWCMUsgrp;
        }

        public EtWCMReqm getEtWCMReqm() {
            return EtWCMReqm;
        }

        public void setEtWCMReqm(EtWCMReqm etWCMReqm) {
            EtWCMReqm = etWCMReqm;
        }

        public VHLP_WCM_SER.EtWCMWork getEtWCMWork() {
            return EtWCMWork;
        }

        public void setEtWCMWork(VHLP_WCM_SER.EtWCMWork etWCMWork) {
            EtWCMWork = etWCMWork;
        }

        public VHLP_WCM_SER.EtWCMUsages getEtWCMUsages() {
            return EtWCMUsages;
        }

        public void setEtWCMUsages(VHLP_WCM_SER.EtWCMUsages etWCMUsages) {
            EtWCMUsages = etWCMUsages;
        }

        public VHLP_WCM_SER.EtWCMBegru getEtWCMBegru() {
            return EtWCMBegru;
        }

        public void setEtWCMBegru(VHLP_WCM_SER.EtWCMBegru etWCMBegru) {
            EtWCMBegru = etWCMBegru;
        }

        public VHLP_WCM_SER.EtWCMTgtyp getEtWCMTgtyp() {
            return EtWCMTgtyp;
        }

        public void setEtWCMTgtyp(VHLP_WCM_SER.EtWCMTgtyp etWCMTgtyp) {
            EtWCMTgtyp = etWCMTgtyp;
        }

        public VHLP_WCM_SER.EtWCMTypes getEtWCMTypes() {
            return EtWCMTypes;
        }

        public void setEtWCMTypes(VHLP_WCM_SER.EtWCMTypes etWCMTypes) {
            EtWCMTypes = etWCMTypes;
        }

        public VHLP_WCM_SER.EtWCMWcvp6 getEtWCMWcvp6() {
            return EtWCMWcvp6;
        }

        public void setEtWCMWcvp6(VHLP_WCM_SER.EtWCMWcvp6 etWCMWcvp6) {
            EtWCMWcvp6 = etWCMWcvp6;
        }
    }


    /*EtWcmWcco*/
    public class EtWcmWcco {
        @SerializedName("results")
        @Expose
        private List<EtWcmWcco_Result> results = null;

        public List<EtWcmWcco_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWcco_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWcco_Result {
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Objart")
        @Expose
        private String objart;
        @SerializedName("Objtyp")
        @Expose
        private String objtyp;
        @SerializedName("Wcmuse")
        @Expose
        private String wcmuse;
        @SerializedName("Direction")
        @Expose
        private String direction;
        @SerializedName("Asgnflg")
        @Expose
        private String asgnflg;

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getObjart() {
            return objart;
        }

        public void setObjart(String objart) {
            this.objart = objart;
        }

        public String getObjtyp() {
            return objtyp;
        }

        public void setObjtyp(String objtyp) {
            this.objtyp = objtyp;
        }

        public String getWcmuse() {
            return wcmuse;
        }

        public void setWcmuse(String wcmuse) {
            this.wcmuse = wcmuse;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getAsgnflg() {
            return asgnflg;
        }

        public void setAsgnflg(String asgnflg) {
            this.asgnflg = asgnflg;
        }
    }
    /*EtWCMUsgrp*/

    /*EtWCMUsgrp*/
    public class EtWCMUsgrp {
        @SerializedName("results")
        @Expose
        private List<EtWCMUsgrp_Result> results = null;

        public List<EtWCMUsgrp_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMUsgrp_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMUsgrp_Result {
        @SerializedName("Usgrp")
        @Expose
        private String usgrp;
        @SerializedName("Pmsog")
        @Expose
        private String pmsog;

        public String getUsgrp() {
            return usgrp;
        }

        public void setUsgrp(String usgrp) {
            this.usgrp = usgrp;
        }

        public String getPmsog() {
            return pmsog;
        }

        public void setPmsog(String pmsog) {
            this.pmsog = pmsog;
        }
    }
    /*EtWCMUsgrp*/


    /*EtWCMReqm*/
    public class EtWCMReqm {
        @SerializedName("results")
        @Expose
        private List<EtWCMReqm_Result> results = null;

        public List<EtWCMReqm_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMReqm_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMReqm_Result {
        public String getIwerk() {
            return Iwerk;
        }

        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }

        public String getObjart() {
            return Objart;
        }

        public void setObjart(String objart) {
            Objart = objart;
        }

        public String getObjtyp() {
            return Objtyp;
        }

        public void setObjtyp(String objtyp) {
            Objtyp = objtyp;
        }

        public String getWapiuse() {
            return Wapiuse;
        }

        public void setWapiuse(String wapiuse) {
            Wapiuse = wapiuse;
        }

        public String getScrtextL() {
            return ScrtextL;
        }

        public void setScrtextL(String scrtextL) {
            ScrtextL = scrtextL;
        }

        public String getWkgrp() {
            return Wkgrp;
        }

        public void setWkgrp(String wkgrp) {
            Wkgrp = wkgrp;
        }

        public String getPropflg() {
            return Propflg;
        }

        public void setPropflg(String propflg) {
            Propflg = propflg;
        }

        public String getModflg() {
            return Modflg;
        }

        public void setModflg(String modflg) {
            Modflg = modflg;
        }

        public String getDpflg() {
            return Dpflg;
        }

        public void setDpflg(String dpflg) {
            Dpflg = dpflg;
        }

        public String getAprv4unmarked() {
            return Aprv4unmarked;
        }

        public void setAprv4unmarked(String aprv4unmarked) {
            Aprv4unmarked = aprv4unmarked;
        }

        public String getAprv4marked() {
            return Aprv4marked;
        }

        public void setAprv4marked(String aprv4marked) {
            Aprv4marked = aprv4marked;
        }

        public String getNeedid() {
            return Needid;
        }

        public void setNeedid(String needid) {
            Needid = needid;
        }

        public String getNeedgrp() {
            return Needgrp;
        }

        public void setNeedgrp(String needgrp) {
            Needgrp = needgrp;
        }

        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Objart")
        @Expose
        private String Objart;
        @SerializedName("Objtyp")
        @Expose
        private String Objtyp;
        @SerializedName("Wapiuse")
        @Expose
        private String Wapiuse;
        @SerializedName("Needid")
        @Expose
        private String Needid;
        @SerializedName("ScrtextL")
        @Expose
        private String ScrtextL;
        @SerializedName("Wkgrp")
        @Expose
        private String Wkgrp;
        @SerializedName("Needgrp")
        @Expose
        private String Needgrp;
        @SerializedName("Propflg")
        @Expose
        private String Propflg;
        @SerializedName("Modflg")
        @Expose
        private String Modflg;
        @SerializedName("Dpflg")
        @Expose
        private String Dpflg;
        @SerializedName("Aprv4unmarked")
        @Expose
        private String Aprv4unmarked;
        @SerializedName("Aprv4marked")
        @Expose
        private String Aprv4marked;
    }
    /*EtWCMWork*/


    /*EtWCMWork*/
    public class EtWCMWork {
        @SerializedName("results")
        @Expose
        private List<EtWCMWork_Result> results = null;

        public List<EtWCMWork_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMWork_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMWork_Result {
        public String getIwerk() {
            return Iwerk;
        }

        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }

        public String getObjart() {
            return Objart;
        }

        public void setObjart(String objart) {
            Objart = objart;
        }

        public String getObjtyp() {
            return Objtyp;
        }

        public void setObjtyp(String objtyp) {
            Objtyp = objtyp;
        }

        public String getWapiuse() {
            return Wapiuse;
        }

        public void setWapiuse(String wapiuse) {
            Wapiuse = wapiuse;
        }

        public String getWkid() {
            return Wkid;
        }

        public void setWkid(String wkid) {
            Wkid = wkid;
        }

        public String getScrtextL() {
            return ScrtextL;
        }

        public void setScrtextL(String scrtextL) {
            ScrtextL = scrtextL;
        }

        public String getWkgrp() {
            return Wkgrp;
        }

        public void setWkgrp(String wkgrp) {
            Wkgrp = wkgrp;
        }

        public String getPropflg() {
            return Propflg;
        }

        public void setPropflg(String propflg) {
            Propflg = propflg;
        }

        public String getModflg() {
            return Modflg;
        }

        public void setModflg(String modflg) {
            Modflg = modflg;
        }

        public String getDpflg() {
            return Dpflg;
        }

        public void setDpflg(String dpflg) {
            Dpflg = dpflg;
        }

        public String getAprv4unmarked() {
            return Aprv4unmarked;
        }

        public void setAprv4unmarked(String aprv4unmarked) {
            Aprv4unmarked = aprv4unmarked;
        }

        public String getAprv4marked() {
            return Aprv4marked;
        }

        public void setAprv4marked(String aprv4marked) {
            Aprv4marked = aprv4marked;
        }

        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Objart")
        @Expose
        private String Objart;
        @SerializedName("Objtyp")
        @Expose
        private String Objtyp;
        @SerializedName("Wapiuse")
        @Expose
        private String Wapiuse;
        @SerializedName("Wkid")
        @Expose
        private String Wkid;
        @SerializedName("ScrtextL")
        @Expose
        private String ScrtextL;
        @SerializedName("Wkgrp")
        @Expose
        private String Wkgrp;
        @SerializedName("Propflg")
        @Expose
        private String Propflg;
        @SerializedName("Modflg")
        @Expose
        private String Modflg;
        @SerializedName("Dpflg")
        @Expose
        private String Dpflg;
        @SerializedName("Aprv4unmarked")
        @Expose
        private String Aprv4unmarked;
        @SerializedName("Aprv4marked")
        @Expose
        private String Aprv4marked;
    }
    /*EtWCMWork*/


    /*EtWCMWcvp6*/
    public class EtWCMWcvp6 {
        @SerializedName("results")
        @Expose
        private List<EtWCMWcvp6_Result> results = null;

        public List<EtWCMWcvp6_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMWcvp6_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMWcvp6_Result {
        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Objart")
        @Expose
        private String Objart;
        @SerializedName("Objtyp")
        @Expose
        private String Objtyp;
        @SerializedName("Stxt")
        @Expose
        private String Stxt;
        @SerializedName("Pmsog")
        @Expose
        private String Pmsog;
        @SerializedName("Gntxt")
        @Expose
        private String Gntxt;
        @SerializedName("Agent")
        @Expose
        private String Agent;

        public String getPmsog() {
            return Pmsog;
        }

        public void setPmsog(String pmsog) {
            Pmsog = pmsog;
        }

        public String getGntxt() {
            return Gntxt;
        }

        public void setGntxt(String gntxt) {
            Gntxt = gntxt;
        }

        public String getAgent() {
            return Agent;
        }

        public void setAgent(String agent) {
            Agent = agent;
        }

        public String getIwerk() {
            return Iwerk;
        }

        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }

        public String getObjart() {
            return Objart;
        }

        public void setObjart(String objart) {
            Objart = objart;
        }

        public String getObjtyp() {
            return Objtyp;
        }

        public void setObjtyp(String objtyp) {
            Objtyp = objtyp;
        }

        public String getStxt() {
            return Stxt;
        }

        public void setStxt(String stxt) {
            Stxt = stxt;
        }
    }
    /*EtWCMWcvp6*/


    /*EtWCMTypes*/
    public class EtWCMTypes {
        @SerializedName("results")
        @Expose
        private List<EtWCMTypes_Result> results = null;

        public List<EtWCMTypes_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMTypes_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMTypes_Result {
        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Objart")
        @Expose
        private String Objart;
        @SerializedName("Objtyp")
        @Expose
        private String Objtyp;
        @SerializedName("Stxt")
        @Expose
        private String Stxt;

        public String getIwerk() {
            return Iwerk;
        }

        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }

        public String getObjart() {
            return Objart;
        }

        public void setObjart(String objart) {
            Objart = objart;
        }

        public String getObjtyp() {
            return Objtyp;
        }

        public void setObjtyp(String objtyp) {
            Objtyp = objtyp;
        }

        public String getStxt() {
            return Stxt;
        }

        public void setStxt(String stxt) {
            Stxt = stxt;
        }
    }
    /*EtWCMTypes*/


    /*EtWCMTgtyp*/
    public class EtWCMTgtyp {
        @SerializedName("results")
        @Expose
        private List<EtWCMTgtyp_Result> results = null;

        public List<EtWCMTgtyp_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMTgtyp_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMTgtyp_Result {
        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Tggrp")
        @Expose
        private String Tggrp;
        @SerializedName("Tgproc")
        @Expose
        private String Tgproc;
        @SerializedName("Tgtyp")
        @Expose
        private String Tgtyp;
        @SerializedName("Unproc")
        @Expose
        private String Unproc;
        @SerializedName("Untyp")
        @Expose
        private String Untyp;
        @SerializedName("Phblflg")
        @Expose
        private String Phblflg;
        @SerializedName("Tgflg")
        @Expose
        private String Tgflg;
        @SerializedName("Usable")
        @Expose
        private String Usable;
        @SerializedName("Tgprocx")
        @Expose
        private String Tgprocx;

        public String getIwerk() {
            return Iwerk;
        }

        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }

        public String getTggrp() {
            return Tggrp;
        }

        public void setTggrp(String tggrp) {
            Tggrp = tggrp;
        }

        public String getTgproc() {
            return Tgproc;
        }

        public void setTgproc(String tgproc) {
            Tgproc = tgproc;
        }

        public String getTgtyp() {
            return Tgtyp;
        }

        public void setTgtyp(String tgtyp) {
            Tgtyp = tgtyp;
        }

        public String getUnproc() {
            return Unproc;
        }

        public void setUnproc(String unproc) {
            Unproc = unproc;
        }

        public String getUntyp() {
            return Untyp;
        }

        public void setUntyp(String untyp) {
            Untyp = untyp;
        }

        public String getPhblflg() {
            return Phblflg;
        }

        public void setPhblflg(String phblflg) {
            Phblflg = phblflg;
        }

        public String getTgflg() {
            return Tgflg;
        }

        public void setTgflg(String tgflg) {
            Tgflg = tgflg;
        }

        public String getUsable() {
            return Usable;
        }

        public void setUsable(String usable) {
            Usable = usable;
        }

        public String getTgprocx() {
            return Tgprocx;
        }

        public void setTgprocx(String tgprocx) {
            Tgprocx = tgprocx;
        }
    }
    /*EtWCMTgtyp*/


    /*EtWCMBegru*/
    public class EtWCMBegru {
        @SerializedName("results")
        @Expose
        private List<EtWCMBegru_Result> results = null;

        public List<EtWCMBegru_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMBegru_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMBegru_Result {
        @SerializedName("Begru")
        @Expose
        private String Begru;
        @SerializedName("Begtx")
        @Expose
        private String Begtx;

        public String getBegru() {
            return Begru;
        }

        public void setBegru(String begru) {
            Begru = begru;
        }

        public String getBegtx() {
            return Begtx;
        }

        public void setBegtx(String begtx) {
            Begtx = begtx;
        }
    }
    /*EtWCMBegru*/


    /*EtWCMUsages*/
    public class EtWCMUsages {
        @SerializedName("results")
        @Expose
        private List<EtWCMUsages_Result> results = null;

        public List<EtWCMUsages_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWCMUsages_Result> results) {
            this.results = results;
        }
    }

    public class EtWCMUsages_Result {
        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Objart")
        @Expose
        private String Objart;
        @SerializedName("Use")
        @Expose
        private String Use;
        @SerializedName("Usex")
        @Expose
        private String Usex;

        public String getIwerk() {
            return Iwerk;
        }

        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }

        public String getObjart() {
            return Objart;
        }

        public void setObjart(String objart) {
            Objart = objart;
        }

        public String getUse() {
            return Use;
        }

        public void setUse(String use) {
            Use = use;
        }

        public String getUsex() {
            return Usex;
        }

        public void setUsex(String usex) {
            Usex = usex;
        }
    }
    /*EtWCMUsages*/

}