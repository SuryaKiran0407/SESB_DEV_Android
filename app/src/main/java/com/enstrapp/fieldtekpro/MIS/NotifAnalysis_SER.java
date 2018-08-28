package com.enstrapp.fieldtekpro.MIS;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotifAnalysis_SER {

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
        @SerializedName("EsNotifTotal")
        @Expose
        private EsNotifTotal EsNotifTotal;
        @SerializedName("EtNotifPlantTotal")
        @Expose
        private EtNotifPlantTotal EtNotifPlantTotal;
        @SerializedName("EtNotifArbplTotal")
        @Expose
        private EtNotifArbplTotal EtNotifArbplTotal;
        @SerializedName("EtNotifTypeTotal")
        @Expose
        private EtNotifTypeTotal EtNotifTypeTotal;
        @SerializedName("EtNotifRep")
        @Expose
        private EtNotifRep EtNotifRep;


        public EsNotifTotal getEsNotifTotal() {
            return EsNotifTotal;
        }

        public void setEsNotifTotal(EsNotifTotal esNotifTotal) {
            EsNotifTotal = esNotifTotal;
        }

        public EtNotifPlantTotal getEtNotifPlantTotal() {
            return EtNotifPlantTotal;
        }

        public void setEtNotifPlantTotal(EtNotifPlantTotal etNotifPlantTotal) {
            EtNotifPlantTotal = etNotifPlantTotal;
        }

        public EtNotifArbplTotal getEtNotifArbplTotal() {
            return EtNotifArbplTotal;
        }

        public void setEtNotifArbplTotal(EtNotifArbplTotal etNotifArbplTotal) {
            EtNotifArbplTotal = etNotifArbplTotal;
        }

        public EtNotifTypeTotal getEtNotifTypeTotal() {
            return EtNotifTypeTotal;
        }

        public void setEtNotifTypeTotal(EtNotifTypeTotal etNotifTypeTotal) {
            EtNotifTypeTotal = etNotifTypeTotal;
        }

        public EtNotifRep getEtNotifRep() {

            return EtNotifRep;
        }

        public void setEtNotifRep(EtNotifRep etNotifRep) {
            EtNotifRep = etNotifRep;


        }


        /*For Parsing EsNotifTotal*/
        public class EsNotifTotal {
            @SerializedName("results")
            @Expose
            private List<EsNotifTotal_Result> results = null;

            public List<EsNotifTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EsNotifTotal_Result> results) {
                this.results = results;
            }
        }

        public class EsNotifTotal_Result {
            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getOuts() {
                return outs;
            }

            public void setOuts(String outs) {
                this.outs = outs;
            }

            public String getInpr() {
                return inpr;
            }

            public void setInpr(String inpr) {
                this.inpr = inpr;
            }

            public String getComp() {
                return comp;
            }

            public void setComp(String comp) {
                this.comp = comp;
            }

            public String getDele() {
                return dele;
            }

            public void setDele(String dele) {
                this.dele = dele;
            }


            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Outs")
            @Expose
            private String outs;
            @SerializedName("Inpr")
            @Expose
            private String inpr;
            @SerializedName("Comp")
            @Expose
            private String comp;
            @SerializedName("Dele")
            @Expose
            private String dele;
        }
    /*For Parsing EsNotifTotal*/


        /*For Parsing EtNotifPlantTotal*/
        public class EtNotifPlantTotal {
            @SerializedName("results")
            @Expose
            private List<EtNotifPlantTotal_Result> results = null;

            public List<EtNotifPlantTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtNotifPlantTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtNotifPlantTotal_Result {
            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getSwerk() {
                return swerk;
            }

            public void setSwerk(String swerk) {
                this.swerk = swerk;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQmart() {
                return qmart;
            }

            public void setQmart(String qmart) {
                this.qmart = qmart;
            }

            public String getQmartx() {
                return qmartx;
            }

            public void setQmartx(String qmartx) {
                this.qmartx = qmartx;
            }

            public String getOuts() {
                return outs;
            }

            public void setOuts(String outs) {
                this.outs = outs;
            }

            public String getInpr() {
                return inpr;
            }

            public void setInpr(String inpr) {
                this.inpr = inpr;
            }

            public String getComp() {
                return comp;
            }

            public void setComp(String comp) {
                this.comp = comp;
            }

            public String getDele() {
                return dele;
            }

            public void setDele(String dele) {
                this.dele = dele;
            }


            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Swerk")
            @Expose
            private String swerk;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Qmart")
            @Expose
            private String qmart;
            @SerializedName("Qmartx")
            @Expose
            private String qmartx;
            @SerializedName("Outs")
            @Expose
            private String outs;
            @SerializedName("Inpr")
            @Expose
            private String inpr;
            @SerializedName("Comp")
            @Expose
            private String comp;
            @SerializedName("Dele")
            @Expose
            private String dele;

        }
    /*For Parsing EtNotifPlantTotal*/


        /*For Parsing EtNotifArbplTotal*/
        public class EtNotifArbplTotal {
            @SerializedName("results")
            @Expose
            private List<EtNotifArbplTotal_Result> results = null;

            public List<EtNotifArbplTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtNotifArbplTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtNotifArbplTotal_Result {
            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getSwerk() {
                return swerk;
            }

            public void setSwerk(String swerk) {
                this.swerk = swerk;
            }

            public String getArbpl() {
                return arbpl;
            }

            public void setArbpl(String arbpl) {
                this.arbpl = arbpl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQmart() {
                return qmart;
            }

            public void setQmart(String qmart) {
                this.qmart = qmart;
            }

            public String getQmartx() {
                return qmartx;
            }

            public void setQmartx(String qmartx) {
                this.qmartx = qmartx;
            }

            public String getOuts() {
                return outs;
            }

            public void setOuts(String outs) {
                this.outs = outs;
            }

            public String getInpr() {
                return inpr;
            }

            public void setInpr(String inpr) {
                this.inpr = inpr;
            }

            public String getComp() {
                return comp;
            }

            public void setComp(String comp) {
                this.comp = comp;
            }

            public String getDele() {
                return dele;
            }

            public void setDele(String dele) {
                this.dele = dele;
            }


            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Swerk")
            @Expose
            private String swerk;
            @SerializedName("Arbpl")
            @Expose
            private String arbpl;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Qmart")
            @Expose
            private String qmart;
            @SerializedName("Qmartx")
            @Expose
            private String qmartx;
            @SerializedName("Outs")
            @Expose
            private String outs;
            @SerializedName("Inpr")
            @Expose
            private String inpr;
            @SerializedName("Comp")
            @Expose
            private String comp;
            @SerializedName("Dele")
            @Expose
            private String dele;

        }
    /*For Parsing EtNotifArbplTotal*/


        /*For Parsing EtNotifTypeTotal*/
        public class EtNotifTypeTotal {
            @SerializedName("results")
            @Expose
            private List<EtNotifTypeTotal_Result> results = null;

            public List<EtNotifTypeTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtNotifTypeTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtNotifTypeTotal_Result {
            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getSwerk() {
                return swerk;
            }

            public void setSwerk(String swerk) {
                this.swerk = swerk;
            }

            public String getArbpl() {
                return arbpl;
            }

            public void setArbpl(String arbpl) {
                this.arbpl = arbpl;
            }


            public String getQmart() {
                return qmart;
            }

            public void setQmart(String qmart) {
                this.qmart = qmart;
            }

            public String getQmartx() {
                return qmartx;
            }

            public void setQmartx(String qmartx) {
                this.qmartx = qmartx;
            }

            public String getOuts() {
                return outs;
            }

            public void setOuts(String outs) {
                this.outs = outs;
            }

            public String getInpr() {
                return inpr;
            }

            public void setInpr(String inpr) {
                this.inpr = inpr;
            }

            public String getComp() {
                return comp;
            }

            public void setComp(String comp) {
                this.comp = comp;
            }

            public String getDele() {
                return dele;
            }

            public void setDele(String dele) {
                this.dele = dele;
            }


            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Swerk")
            @Expose
            private String swerk;
            @SerializedName("Arbpl")
            @Expose
            private String arbpl;
            @SerializedName("Qmart")
            @Expose
            private String qmart;
            @SerializedName("Qmartx")
            @Expose
            private String qmartx;
            @SerializedName("Outs")
            @Expose
            private String outs;
            @SerializedName("Inpr")
            @Expose
            private String inpr;
            @SerializedName("Comp")
            @Expose
            private String comp;
            @SerializedName("Dele")
            @Expose
            private String dele;
        }
        /*For Parsing EtNotifTypeTotal*/


        /*For Parsing EtNotifRep*/
        public class EtNotifRep {
            @SerializedName("results")
            @Expose
            private List<EtNotifRep_Result> results = null;

            public List<EtNotifRep_Result> getResults() {
                return results;
            }

            public void setResults(List<EtNotifRep_Result> results) {
                this.results = results;
            }
        }

        public class EtNotifRep_Result {
            @SerializedName("Phase")
            @Expose
            public String Phase;
            @SerializedName("Swerk")
            @Expose
            public String Swerk;
            @SerializedName("Arbpl")
            @Expose
            public String Arbpl;
            @SerializedName("Qmart")
            @Expose
            public String Qmart;
            @SerializedName("Qmnum")
            @Expose
            public String Qmnum;
            @SerializedName("Qmtxt")
            @Expose
            public String Qmtxt;
            @SerializedName("Ernam")
            @Expose
            public String Ernam;
            @SerializedName("Equnr")
            @Expose
            public String Equnr;
            @SerializedName("Eqktx")
            @Expose
            public String Eqktx;
            @SerializedName("Priok")
            @Expose
            public String Priok;
            @SerializedName("Strmn")
            @Expose
            public String Strmn;
            @SerializedName("Ltrmn")
            @Expose
            public String Ltrmn;
            @SerializedName("Auswk")
            @Expose
            public String Auswk;
            @SerializedName("Tplnr")
            @Expose
            public String Tplnr;
            @SerializedName("Msaus")
            @Expose
            public String Msaus;
            @SerializedName("Ausvn")
            @Expose
            public String Ausvn;
            @SerializedName("Ausbs")
            @Expose
            public String Ausbs;

            public String getPhase() {
                return Phase;
            }

            public void setPhase(String phase) {
                Phase = phase;
            }

            public String getSwerk() {
                return Swerk;
            }

            public void setSwerk(String swerk) {
                Swerk = Swerk;
            }

            public String getArbpl() {
                return Arbpl;
            }

            public void setArbpl(String arbpl) {
                Arbpl = arbpl;
            }

            public String getQmart() {
                return Qmart;
            }

            public void setQmart(String qmart) {
                Qmart = qmart;
            }

            public String getQmnum() {
                return Qmnum;
            }

            public void setQmnum(String qmnum) {
                Qmnum = qmnum;
            }

            public String getQmtxt() {
                return Qmtxt;
            }

            public void setQmtxt(String qmtxt) {
                Qmtxt = qmtxt;
            }

            public String getErnam() {
                return Ernam;
            }

            public void setErnam(String ernam) {
                Ernam = ernam;
            }

            public String getEqunr() {
                return Equnr;
            }

            public void setEqunr(String equnr) {
                Equnr = equnr;
            }

            public String getEqktx() {
                return Eqktx;
            }

            public void setEqktx(String eqktx) {
                Eqktx = eqktx;
            }

            public String getPriok() {
                return Priok;
            }

            public void setPriok(String priok) {
                Priok = priok;
            }

            public String getStrmn() {
                return Strmn;
            }

            public void setStrmn(String strmn) {
                Strmn = strmn;
            }

            public String getLtrmn() {
                return Ltrmn;
            }

            public void setLtrmn(String ltrmn) {
                Ltrmn = ltrmn;
            }

            public String getAuswk() {
                return Auswk;
            }

            public void setAuswk(String auswk) {
                Auswk = auswk;
            }

            public String getTplnr() {
                return Tplnr;
            }

            public void setTplnr(String tplnr) {
                Tplnr = tplnr;
            }

            public String getMsaus() {
                return Msaus;
            }

            public void setMsaus(String msaus) {
                Msaus = msaus;
            }

            public String getAusvn() {
                return Ausvn;
            }

            public void setAusvn(String ausvn) {
                Ausvn = ausvn;
            }

            public String getAusbs() {
                return Ausbs;
            }

            public void setAusbs(String ausbs) {
                Ausbs = ausbs;
            }

        }
    /*For Parsing EtNotifRep*/

    }
}