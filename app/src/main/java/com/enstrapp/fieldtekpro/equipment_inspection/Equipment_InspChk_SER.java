package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Equipment_InspChk_SER
{

    @SerializedName("d")
    @Expose
    private D d;
    public D getD()
    {
        return d;
    }
    public void setD(D d)
    {
        this.d = d;
    }


    public class D
    {
        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        public List<Result> getResults()
        {
            return results;
        }
        public void setResults(List<Result> results)
        {
            this.results = results;
        }
    }


    public class Result
    {
        @SerializedName("EtImrg")
        @Expose
        private EtImrg etImrg;

        public EtImrg getEtImrg() {
            return etImrg;
        }

        public void setEtImrg(EtImrg etImrg) {
            this.etImrg = etImrg;
        }
    }


    public class EtImrg
    {
        @SerializedName("results")
        @Expose
        private List<EtImrg_Result> results = null;
        public List<EtImrg_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtImrg_Result> results)
        {
            this.results = results;
        }
    }


    public class EtImrg_Result
    {
        @Expose
        private String qmnum;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Vornr")
        @Expose
        private String vornr;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
        @SerializedName("Mdocm")
        @Expose
        private String mdocm;
        @SerializedName("Point")
        @Expose
        private String point;
        @SerializedName("Mpobj")
        @Expose
        private String mpobj;
        @SerializedName("Mpobt")
        @Expose
        private String mpobt;
        @SerializedName("Psort")
        @Expose
        private String psort;
        @SerializedName("Pttxt")
        @Expose
        private String pttxt;
        @SerializedName("Atinn")
        @Expose
        private String atinn;
        @SerializedName("Idate")
        @Expose
        private String idate;
        @SerializedName("Itime")
        @Expose
        private String itime;
        @SerializedName("Mdtxt")
        @Expose
        private String mdtxt;
        @SerializedName("Readr")
        @Expose
        private String readr;
        @SerializedName("Atbez")
        @Expose
        private String atbez;
        @SerializedName("Msehi")
        @Expose
        private String msehi;
        @SerializedName("Msehl")
        @Expose
        private String msehl;
        @SerializedName("Readc")
        @Expose
        private String readc;
        @SerializedName("Desic")
        @Expose
        private String desic;
        @SerializedName("Prest")
        @Expose
        private String prest;
        @SerializedName("Docaf")
        @Expose
        private String docaf;
        @SerializedName("Codct")
        @Expose
        private String codct;
        @SerializedName("Codgr")
        @Expose
        private String codgr;
        @SerializedName("Vlcod")
        @Expose
        private String vlcod;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getVornr() {
            return vornr;
        }

        public void setVornr(String vornr) {
            this.vornr = vornr;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getMdocm() {
            return mdocm;
        }

        public void setMdocm(String mdocm) {
            this.mdocm = mdocm;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getMpobj() {
            return mpobj;
        }

        public void setMpobj(String mpobj) {
            this.mpobj = mpobj;
        }

        public String getMpobt() {
            return mpobt;
        }

        public void setMpobt(String mpobt) {
            this.mpobt = mpobt;
        }

        public String getPsort() {
            return psort;
        }

        public void setPsort(String psort) {
            this.psort = psort;
        }

        public String getPttxt() {
            return pttxt;
        }

        public void setPttxt(String pttxt) {
            this.pttxt = pttxt;
        }

        public String getAtinn() {
            return atinn;
        }

        public void setAtinn(String atinn) {
            this.atinn = atinn;
        }

        public String getIdate() {
            return idate;
        }

        public void setIdate(String idate) {
            this.idate = idate;
        }

        public String getItime() {
            return itime;
        }

        public void setItime(String itime) {
            this.itime = itime;
        }

        public String getMdtxt() {
            return mdtxt;
        }

        public void setMdtxt(String mdtxt) {
            this.mdtxt = mdtxt;
        }

        public String getReadr() {
            return readr;
        }

        public void setReadr(String readr) {
            this.readr = readr;
        }

        public String getAtbez() {
            return atbez;
        }

        public void setAtbez(String atbez) {
            this.atbez = atbez;
        }

        public String getMsehi() {
            return msehi;
        }

        public void setMsehi(String msehi) {
            this.msehi = msehi;
        }

        public String getMsehl() {
            return msehl;
        }

        public void setMsehl(String msehl) {
            this.msehl = msehl;
        }

        public String getReadc() {
            return readc;
        }

        public void setReadc(String readc) {
            this.readc = readc;
        }

        public String getDesic() {
            return desic;
        }

        public void setDesic(String desic) {
            this.desic = desic;
        }

        public String getPrest() {
            return prest;
        }

        public void setPrest(String prest) {
            this.prest = prest;
        }

        public String getDocaf() {
            return docaf;
        }

        public void setDocaf(String docaf) {
            this.docaf = docaf;
        }

        public String getCodct() {
            return codct;
        }

        public void setCodct(String codct) {
            this.codct = codct;
        }

        public String getCodgr() {
            return codgr;
        }

        public void setCodgr(String codgr) {
            this.codgr = codgr;
        }

        public String getVlcod() {
            return vlcod;
        }

        public void setVlcod(String vlcod) {
            this.vlcod = vlcod;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }


}