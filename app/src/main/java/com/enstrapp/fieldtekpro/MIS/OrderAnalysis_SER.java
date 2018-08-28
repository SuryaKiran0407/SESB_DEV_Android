package com.enstrapp.fieldtekpro.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class OrderAnalysis_SER {

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
        @SerializedName("EsOrdTotal")
        @Expose
        private Result.EsOrdTotal EsOrdTotal;
        @SerializedName("EtOrdPlantTotal")
        @Expose
        private Result.EtOrdPlantTotal EtOrdPlantTotal;
        @SerializedName("EtOrdArbplTotal")
        @Expose
        private Result.EtOrdArbplTotal EtOrdArbplTotal;
        @SerializedName("EtOrdTypeTotal")
        @Expose
        private Result.EtOrdTypeTotal EtOrdTypeTotal;
        @SerializedName("EtOrdRep")
        @Expose
        private Result.EtOrdRep EtOrdRep;


        public Result.EsOrdTotal getEsOrdTotal() {
            return EsOrdTotal;
        }

        public void setEsOrdTotal(Result.EsOrdTotal esOrdTotal) {
            EsOrdTotal = esOrdTotal;
        }

        public Result.EtOrdPlantTotal getEtOrdPlantTotal() {
            return EtOrdPlantTotal;
        }

        public void setEtOrdPlantTotal(Result.EtOrdPlantTotal etOrdPlantTotal) {
            EtOrdPlantTotal = etOrdPlantTotal;
        }

        public Result.EtOrdArbplTotal getEtOrdArbplTotal() {
            return EtOrdArbplTotal;
        }

        public void setEtOrdArbplTotal(Result.EtOrdArbplTotal etOrdArbplTotal) {
            EtOrdArbplTotal = etOrdArbplTotal;
        }

        public Result.EtOrdTypeTotal getEtOrdTypeTotal() {
            return EtOrdTypeTotal;
        }

        public void setEtOrdTypeTotal(Result.EtOrdTypeTotal etOrdTypeTotal) {
            EtOrdTypeTotal = etOrdTypeTotal;
        }

        public Result.EtOrdRep getEtOrdRep() {

            return EtOrdRep;
        }

        public void setEtOrdRep(Result.EtOrdRep etOrdRep) {
            EtOrdRep = etOrdRep;


        }

        /*For Parsing EsOrdTotal*/
        public class EsOrdTotal {
            @SerializedName("results")
            @Expose
            private List<EsOrdTotal_Result> results = null;

            public List<EsOrdTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EsOrdTotal_Result> results) {
                this.results = results;
            }
        }

        public class EsOrdTotal_Result {

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

        }
        /*For Parsing EsOrdTotal*/

        /*For Parsing EtOrdPlantTotal*/

        public class EtOrdPlantTotal {
            @SerializedName("results")
            @Expose
            private List<EtOrdPlantTotal_Result> results = null;

            public List<EtOrdPlantTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtOrdPlantTotal_Result> results) {
                this.results = results;
            }
        }


        public class EtOrdPlantTotal_Result {

            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Swerk")
            @Expose
            private String swerk;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Auart")
            @Expose
            private String auart;
            @SerializedName("Auartx")
            @Expose
            private String auartx;
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

            public String getAuart() {
                return auart;
            }

            public void setAuart(String auart) {
                this.auart = auart;
            }

            public String getAuartx() {
                return auartx;
            }

            public void setAuartx(String auartx) {
                this.auartx = auartx;
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
        }
        /*For Parsing EtOrdPlantTotal*/

        /*For Parsing EtOrdArbplTotal*/

        public class EtOrdArbplTotal {
            @SerializedName("results")
            @Expose
            private List<EtOrdArbplTotal_Result> results = null;

            public List<EtOrdArbplTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtOrdArbplTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtOrdArbplTotal_Result {

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
            @SerializedName("Auart")
            @Expose
            private String auart;
            @SerializedName("Auartx")
            @Expose
            private String auartx;
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

            public String getAuart() {
                return auart;
            }

            public void setAuart(String auart) {
                this.auart = auart;
            }

            public String getAuartx() {
                return auartx;
            }

            public void setAuartx(String auartx) {
                this.auartx = auartx;
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

        }
        /*For Parsing EtOrdArbplTotal*/


        /*For Parsing EtOrdTypeTotal*/


        public class EtOrdTypeTotal {
            @SerializedName("results")
            @Expose
            private List<EtOrdTypeTotal_Result> results = null;

            public List<EtOrdTypeTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtOrdTypeTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtOrdTypeTotal_Result {

            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Swerk")
            @Expose
            private String swerk;
            @SerializedName("Arbpl")
            @Expose
            private String arbpl;
            @SerializedName("Auart")
            @Expose
            private String auart;
            @SerializedName("Auartx")
            @Expose
            private String auartx;
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

            public String getAuart() {
                return auart;
            }

            public void setAuart(String auart) {
                this.auart = auart;
            }

            public String getAuartx() {
                return auartx;
            }

            public void setAuartx(String auartx) {
                this.auartx = auartx;
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


        }
        /*For Parsing EtOrdTypeTotal*/

        /*For Parsing EtOrdRep*/


        public class EtOrdRep {
            @SerializedName("results")
            @Expose
            private List<EtOrdRep_Result> results = null;

            public List<EtOrdRep_Result> getResults() {
                return results;
            }

            public void setResults(List<EtOrdRep_Result> results) {
                this.results = results;
            }
        }

        public class EtOrdRep_Result {
            @SerializedName("Phase")
            @Expose
            private String phase;
            @SerializedName("Swerk")
            @Expose
            private String swerk;
            @SerializedName("Arbpl")
            @Expose
            private String arbpl;
            @SerializedName("Auart")
            @Expose
            private String auart;
            @SerializedName("Aufnr")
            @Expose
            private String aufnr;
            @SerializedName("Qmart")
            @Expose
            private String qmart;
            @SerializedName("Qmnum")
            @Expose
            private String qmnum;
            @SerializedName("Ktext")
            @Expose
            private String ktext;
            @SerializedName("Ernam")
            @Expose
            private String ernam;
            @SerializedName("Equnr")
            @Expose
            private String equnr;
            @SerializedName("Eqktx")
            @Expose
            private String eqktx;
            @SerializedName("Priok")
            @Expose
            private String priok;
            @SerializedName("Gstrp")
            @Expose
            private String gstrp;
            @SerializedName("Gltrp")
            @Expose
            private String gltrp;
            @SerializedName("Tplnr")
            @Expose
            private String tplnr;
            @SerializedName("Idat2")
            @Expose
            private String idat2;

            public String getPhase() {
                return phase;
            }

            public void setPhase(String phase) {
                this.phase = phase;
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

            public String getAuart() {
                return auart;
            }

            public void setAuart(String auart) {
                this.auart = auart;
            }

            public String getAufnr() {
                return aufnr;
            }

            public void setAufnr(String aufnr) {
                this.aufnr = aufnr;
            }

            public String getQmart() {
                return qmart;
            }

            public void setQmart(String qmart) {
                this.qmart = qmart;
            }

            public String getQmnum() {
                return qmnum;
            }

            public void setQmnum(String qmnum) {
                this.qmnum = qmnum;
            }

            public String getKtext() {
                return ktext;
            }

            public void setKtext(String ktext) {
                this.ktext = ktext;
            }

            public String getErnam() {
                return ernam;
            }

            public void setErnam(String ernam) {
                this.ernam = ernam;
            }

            public String getEqunr() {
                return equnr;
            }

            public void setEqunr(String equnr) {
                this.equnr = equnr;
            }

            public String getEqktx() {
                return eqktx;
            }

            public void setEqktx(String eqktx) {
                this.eqktx = eqktx;
            }

            public String getPriok() {
                return priok;
            }

            public void setPriok(String priok) {
                this.priok = priok;
            }

            public String getGstrp() {
                return gstrp;
            }

            public void setGstrp(String gstrp) {
                this.gstrp = gstrp;
            }

            public String getGltrp() {
                return gltrp;
            }

            public void setGltrp(String gltrp) {
                this.gltrp = gltrp;
            }

            public String getTplnr() {
                return tplnr;
            }

            public void setTplnr(String tplnr) {
                this.tplnr = tplnr;
            }

            public String getIdat2() {
                return idat2;
            }

            public void setIdat2(String idat2) {
                this.idat2 = idat2;
            }
        }
         /*For Parsing EtOrdRep*/
    }
}




