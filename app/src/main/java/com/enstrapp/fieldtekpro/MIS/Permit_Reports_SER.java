package com.enstrapp.fieldtekpro.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Enstrapp on 19/02/18.
 */

public class Permit_Reports_SER {
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
        @SerializedName("EsPermitTotal")
        @Expose
        private EsPermitTotal EsPermitTotal;
        @SerializedName("EsPermitAppr")
        @Expose
        private EsPermitAppr EsPermitAppr;
        @SerializedName("EtPermitTotalWerks")
        @Expose
        private EtPermitTotalWerks EtPermitTotalWerks;
        @SerializedName("EtPermitApprWerks")
        @Expose
        private EtPermitApprWerks EtPermitApprWerks;
        @SerializedName("EtPermitTotalArbpl")
        @Expose
        private EtPermitTotalArbpl EtPermitTotalArbpl;
        @SerializedName("EtPermitApprArbpl")
        @Expose
        private EtPermitApprArbpl EtPermitApprArbpl;
        @SerializedName("EtPermitWw")
        @Expose
        private EtPermitWw EtPermitWw;
        @SerializedName("EtPermitWa")
        @Expose
        private EtPermitWa EtPermitWa;
        @SerializedName("EtPermitWd")
        @Expose
        private EtPermitWd EtPermitWd;


        public EsPermitTotal getEsPermitTotal() {
            return EsPermitTotal;
        }

        public void setEsPermitTotal(EsPermitTotal esPermitTotal) {
            EsPermitTotal = esPermitTotal;
        }


        public EsPermitAppr getEsPermitAppr() {
            return EsPermitAppr;
        }

        public void setEsPermitAppr(EsPermitAppr esPermitAppr) {
            EsPermitAppr = esPermitAppr;
        }

        public EtPermitTotalWerks getEtPermitTotalWerks() {
            return EtPermitTotalWerks;
        }

        public void setEsPermitTotalWerks(EtPermitTotalWerks etPermitTotalWerks) {
            EtPermitTotalWerks = etPermitTotalWerks;
        }

        public EtPermitApprWerks getEtPermitApprWerks() {
            return EtPermitApprWerks;
        }

        public void setEtPermitApprWerks(EtPermitApprWerks etPermitApprWerks) {
            EtPermitApprWerks = etPermitApprWerks;
        }

        public EtPermitTotalArbpl getEtPermitTotalArbpl() {
            return EtPermitTotalArbpl;
        }

        public void setEtPermitTotalArbpl(EtPermitTotalArbpl etPermitTotalArbpl) {
            EtPermitTotalArbpl = etPermitTotalArbpl;
        }

        public EtPermitApprArbpl getEtPermitApprArbpl() {
            return EtPermitApprArbpl;
        }

        public void setEtPermitApprArbpl(EtPermitApprArbpl etPermitApprArbpl) {
            EtPermitApprArbpl = etPermitApprArbpl;
        }

        public EtPermitWw getEtPermitWw() {
            return EtPermitWw;
        }

        public void setEtPermitWw(EtPermitWw etPermitWw) {
            EtPermitWw = etPermitWw;
        }

        public EtPermitWa getEtPermitWa() {
            return EtPermitWa;
        }

        public void setEtPermitWa(EtPermitWa etPermitWa) {
            EtPermitWa = etPermitWa;
        }

        public EtPermitWd getEtPermitWd() {
            return EtPermitWd;
        }

        public void setEtPermitWd(EtPermitWd etPermitWd) {
            EtPermitWd = etPermitWd;
        }



        /*For Parsing EsPermitTotal*/
        public class EsPermitTotal {
            @SerializedName("results")
            @Expose
            private List<EsPermitTotal_Result> results = null;

            public List<EsPermitTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EsPermitTotal_Result> results) {
                this.results = results;
            }
        }

        public class EsPermitTotal_Result {
            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Crea")
            @Expose
            private String crea;
            @SerializedName("Prep")
            @Expose
            private String prep;
            @SerializedName("Clsd")
            @Expose
            private String clsd;
            @SerializedName("Reje")
            @Expose
            private String reje;

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getCrea() {
                return crea;
            }

            public void setCrea(String crea) {
                this.crea = crea;
            }

            public String getPrep() {
                return prep;
            }

            public void setPrep(String prep) {
                this.prep = prep;
            }

            public String getClsd() {
                return clsd;
            }

            public void setClsd(String clsd) {
                this.clsd = clsd;
            }

            public String getReje() {
                return reje;
            }

            public void setReje(String reje) {
                this.reje = reje;
            }

        }
        /*For Parsing EsPermitTotal*/

         /*For Parsing EsPermitAppr*/


        public class EsPermitAppr {
            @SerializedName("results")
            @Expose
            private List<EsPermitAppr_Result> results = null;

            public List<EsPermitAppr_Result> getResults() {
                return results;
            }

            public void setResults(List<EsPermitAppr_Result> results) {
                this.results = results;
            }
        }

        public class EsPermitAppr_Result {

            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Red")
            @Expose
            private String red;
            @SerializedName("Yellow")
            @Expose
            private String yellow;
            @SerializedName("Green")
            @Expose
            private String green;

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getRed() {
                return red;
            }

            public void setRed(String red) {
                this.red = red;
            }

            public String getYellow() {
                return yellow;
            }

            public void setYellow(String yellow) {
                this.yellow = yellow;
            }

            public String getGreen() {
                return green;
            }

            public void setGreen(String green) {
                this.green = green;
            }

        }
        /*For Parsing EsPermitAppr*/

         /*For Parsing EtPermitTotalWerks*/

        public class EtPermitTotalWerks {
            @SerializedName("results")
            @Expose
            private List<EtPermitTotalWerks_Result> results = null;

            public List<EtPermitTotalWerks_Result> getResults() {
                return results;
            }

            public void setResults(List<EtPermitTotalWerks_Result> results) {
                this.results = results;
            }
        }

        public class EtPermitTotalWerks_Result {
            @SerializedName("Iwerk")
            @Expose
            private String iwerk;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Crea")
            @Expose
            private String crea;
            @SerializedName("Prep")
            @Expose
            private String prep;
            @SerializedName("Clsd")
            @Expose
            private String clsd;
            @SerializedName("Reje")
            @Expose
            private String reje;


            public String getIwerk() {
                return iwerk;
            }

            public void setIwerk(String iwerk) {
                this.iwerk = iwerk;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getCrea() {
                return crea;
            }

            public void setCrea(String crea) {
                this.crea = crea;
            }

            public String getPrep() {
                return prep;
            }

            public void setPrep(String prep) {
                this.prep = prep;
            }

            public String getClsd() {
                return clsd;
            }

            public void setClsd(String clsd) {
                this.clsd = clsd;
            }

            public String getReje() {
                return reje;
            }

            public void setReje(String reje) {
                this.reje = reje;
            }
        }
     /*For Parsing EtPermitTotalWerks*/


        /*For Parsing EtPermitApprWerks*/
        public class EtPermitApprWerks {
            @SerializedName("results")
            @Expose
            private List<EtPermitApprWerks_Result> results = null;

            public List<EtPermitApprWerks_Result> getResults() {
                return results;
            }

            public void setResults(List<EtPermitApprWerks_Result> results) {
                this.results = results;
            }
        }

        public class EtPermitApprWerks_Result {
            @SerializedName("Iwerk")
            @Expose
            private String iwerk;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Red")
            @Expose
            private String red;
            @SerializedName("Yellow")
            @Expose
            private String yellow;
            @SerializedName("Green")
            @Expose
            private String green;


            public String getIwerk() {
                return iwerk;
            }

            public void setIwerk(String iwerk) {
                this.iwerk = iwerk;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getRed() {
                return red;
            }

            public void setRed(String red) {
                this.red = red;
            }

            public String getYellow() {
                return yellow;
            }

            public void setYellow(String yellow) {
                this.yellow = yellow;
            }

            public String getGreen() {
                return green;
            }

            public void setGreen(String green) {
                this.green = green;
            }

        }
       /*For Parsing EtPermitApprWerks*/



       /*For Parsing EtPermitTotalArbpl*/
        public class EtPermitTotalArbpl {
            @SerializedName("results")
            @Expose
            private List<EtPermitTotalArbpl_Result> results = null;

            public List<EtPermitTotalArbpl_Result> getResults() {
                return results;
            }

            public void setResults(List<EtPermitTotalArbpl_Result> results) {
                this.results = results;
            }
        }


        public class EtPermitTotalArbpl_Result {
            @SerializedName("Iwerk")
            @Expose
            private String iwerk;
            @SerializedName("Arbpl")
            @Expose
            private String arbpl;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Total")
            @Expose
            private String total;
            @SerializedName("Crea")
            @Expose
            private String crea;
            @SerializedName("Prep")
            @Expose
            private String prep;
            @SerializedName("Clsd")
            @Expose
            private String clsd;
            @SerializedName("Reje")
            @Expose
            private String reje;


            public String getIwerk() {
                return iwerk;
            }

            public void setIwerk(String iwerk) {
                this.iwerk = iwerk;
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

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getCrea() {
                return crea;
            }

            public void setCrea(String crea) {
                this.crea = crea;
            }

            public String getPrep() {
                return prep;
            }

            public void setPrep(String prep) {
                this.prep = prep;
            }

            public String getClsd() {
                return clsd;
            }

            public void setClsd(String clsd) {
                this.clsd = clsd;
            }

            public String getReje() {
                return reje;
            }

            public void setReje(String reje) {
                this.reje = reje;
            }
        }

            /*For Parsing EtPermitTotalArbpl*/

            /*For Parsing EtPermitApprArbpl*/

            public class EtPermitApprArbpl {

                @SerializedName("results")
                @Expose
                private List<EtPermitApprArbpl_Result> results = null;

                public List<EtPermitApprArbpl_Result> getResults() {
                    return results;
                }

                public void setResults(List<EtPermitApprArbpl_Result> results) {
                    this.results = results;
                }
            }

            public class EtPermitApprArbpl_Result
            {
                @SerializedName("Iwerk")
                @Expose
                private String iwerk;
                @SerializedName("Arbpl")
                @Expose
                private String arbpl;
                @SerializedName("Name")
                @Expose
                private String name;
                @SerializedName("Total")
                @Expose
                private String total;
                @SerializedName("Red")
                @Expose
                private String red;
                @SerializedName("Yellow")
                @Expose
                private String yellow;
                @SerializedName("Green")
                @Expose
                private String green;


                public String getIwerk() {
                    return iwerk;
                }

                public void setIwerk(String iwerk) {
                    this.iwerk = iwerk;
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

                public String getTotal() {
                    return total;
                }

                public void setTotal(String total) {
                    this.total = total;
                }

                public String getRed() {
                    return red;
                }

                public void setRed(String red) {
                    this.red = red;
                }

                public String getYellow() {
                    return yellow;
                }

                public void setYellow(String yellow) {
                    this.yellow = yellow;
                }

                public String getGreen() {
                    return green;
                }

                public void setGreen(String green) {
                    this.green = green;
                }
            }

/*For Parsing EtPermitApprArbpl*/

            /*For Parsing EtPermitWw*/
            public class EtPermitWw {
                @SerializedName("results")
                @Expose
                private List<EtPermitWw_Result> results = null;

                public List<EtPermitWw_Result> getResults() {
                    return results;
                }

                public void setResults(List<EtPermitWw_Result> results) {
                    this.results = results;
                }
            }

            public class EtPermitWw_Result {
                @SerializedName("Iwerk")
                @Expose
                private String iwerk;
                @SerializedName("Arbpl")
                @Expose
                private String arbpl;
                @SerializedName("Crea")
                @Expose
                private String crea;
                @SerializedName("Prep")
                @Expose
                private String prep;
                @SerializedName("Clsd")
                @Expose
                private String clsd;
                @SerializedName("Reje")
                @Expose
                private String reje;
                @SerializedName("Red")
                @Expose
                private String red;
                @SerializedName("Yellow")
                @Expose
                private String yellow;
                @SerializedName("Green")
                @Expose
                private String green;
                @SerializedName("Aufnr")
                @Expose
                private String aufnr;
                @SerializedName("Wapnr")
                @Expose
                private String wapnr;
                @SerializedName("Objart")
                @Expose
                private String objart;
                @SerializedName("Objnr")
                @Expose
                private String objnr;
                @SerializedName("Stxt")
                @Expose
                private String stxt;
                @SerializedName("Datefr")
                @Expose
                private String datefr;
                @SerializedName("Timefr")
                @Expose
                private String timefr;
                @SerializedName("Dateto")
                @Expose
                private String dateto;
                @SerializedName("Timeto")
                @Expose
                private String timeto;
                @SerializedName("Priok")
                @Expose
                private String priok;
                @SerializedName("Tplnr")
                @Expose
                private String tplnr;
                @SerializedName("Equnr")
                @Expose
                private String equnr;
                @SerializedName("Eqktx")
                @Expose
                private String eqktx;
                @SerializedName("Refobj")
                @Expose
                private String refobj;


                public String getIwerk() {
                    return iwerk;
                }

                public void setIwerk(String iwerk) {
                    this.iwerk = iwerk;
                }

                public String getArbpl() {
                    return arbpl;
                }

                public void setArbpl(String arbpl) {
                    this.arbpl = arbpl;
                }

                public String getCrea() {
                    return crea;
                }

                public void setCrea(String crea) {
                    this.crea = crea;
                }

                public String getPrep() {
                    return prep;
                }

                public void setPrep(String prep) {
                    this.prep = prep;
                }

                public String getClsd() {
                    return clsd;
                }

                public void setClsd(String clsd) {
                    this.clsd = clsd;
                }

                public String getReje() {
                    return reje;
                }

                public void setReje(String reje) {
                    this.reje = reje;
                }

                public String getRed() {
                    return red;
                }

                public void setRed(String red) {
                    this.red = red;
                }

                public String getYellow() {
                    return yellow;
                }

                public void setYellow(String yellow) {
                    this.yellow = yellow;
                }

                public String getGreen() {
                    return green;
                }

                public void setGreen(String green) {
                    this.green = green;
                }

                public String getAufnr() {
                    return aufnr;
                }

                public void setAufnr(String aufnr) {
                    this.aufnr = aufnr;
                }

                public String getWapnr() {
                    return wapnr;
                }

                public void setWapnr(String wapnr) {
                    this.wapnr = wapnr;
                }

                public String getObjart() {
                    return objart;
                }

                public void setObjart(String objart) {
                    this.objart = objart;
                }

                public String getObjnr() {
                    return objnr;
                }

                public void setObjnr(String objnr) {
                    this.objnr = objnr;
                }

                public String getStxt() {
                    return stxt;
                }

                public void setStxt(String stxt) {
                    this.stxt = stxt;
                }

                public String getDatefr() {
                    return datefr;
                }

                public void setDatefr(String datefr) {
                    this.datefr = datefr;
                }

                public String getTimefr() {
                    return timefr;
                }

                public void setTimefr(String timefr) {
                    this.timefr = timefr;
                }

                public String getDateto() {
                    return dateto;
                }

                public void setDateto(String dateto) {
                    this.dateto = dateto;
                }

                public String getTimeto() {
                    return timeto;
                }

                public void setTimeto(String timeto) {
                    this.timeto = timeto;
                }

                public String getPriok() {
                    return priok;
                }

                public void setPriok(String priok) {
                    this.priok = priok;
                }

                public String getTplnr() {
                    return tplnr;
                }

                public void setTplnr(String tplnr) {
                    this.tplnr = tplnr;
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

                public String getRefobj() {
                    return refobj;
                }

                public void setRefobj(String refobj) {
                    this.refobj = refobj;
                }
            }

/*For Parsing EtPermitWw*/

            /*For Parsing EtPermitWa*/
            public class EtPermitWa {
                @SerializedName("results")
                @Expose
                private List<EtPermitWa_Result> results = null;

                public List<EtPermitWa_Result> getResults() {
                    return results;
                }

                public void setResults(List<EtPermitWa_Result> results) {
                    this.results = results;
                }
            }

            public class EtPermitWa_Result {
                @SerializedName("Iwerk")
                @Expose
                private String iwerk;
                @SerializedName("Crea")
                @Expose
                private String crea;
                @SerializedName("Prep")
                @Expose
                private String prep;
                @SerializedName("Clsd")
                @Expose
                private String clsd;
                @SerializedName("Reje")
                @Expose
                private String reje;
                @SerializedName("Red")
                @Expose
                private String red;
                @SerializedName("Yellow")
                @Expose
                private String yellow;
                @SerializedName("Green")
                @Expose
                private String green;
                @SerializedName("Aufnr")
                @Expose
                private String aufnr;
                @SerializedName("Wapnr")
                @Expose
                private String wapnr;
                @SerializedName("Wapinr")
                @Expose
                private String wapinr;
                @SerializedName("Objart")
                @Expose
                private String objart;
                @SerializedName("Objtyp")
                @Expose
                private String objtyp;
                @SerializedName("Objnr")
                @Expose
                private String objnr;
                @SerializedName("Stxt")
                @Expose
                private String stxt;
                @SerializedName("Arbpl")
                @Expose
                private String arbpl;
                @SerializedName("Datefr")
                @Expose
                private String datefr;
                @SerializedName("Timefr")
                @Expose
                private String timefr;
                @SerializedName("Dateto")
                @Expose
                private String dateto;
                @SerializedName("Timeto")
                @Expose
                private String timeto;
                @SerializedName("Priok")
                @Expose
                private String priok;
                @SerializedName("Tplnr")
                @Expose
                private String tplnr;
                @SerializedName("Equnr")
                @Expose
                private String equnr;
                @SerializedName("Eqktx")
                @Expose
                private String eqktx;
                @SerializedName("Refobj")
                @Expose
                private String refobj;


                public String getIwerk() {
                    return iwerk;
                }

                public void setIwerk(String iwerk) {
                    this.iwerk = iwerk;
                }

                public String getCrea() {
                    return crea;
                }

                public void setCrea(String crea) {
                    this.crea = crea;
                }

                public String getPrep() {
                    return prep;
                }

                public void setPrep(String prep) {
                    this.prep = prep;
                }

                public String getClsd() {
                    return clsd;
                }

                public void setClsd(String clsd) {
                    this.clsd = clsd;
                }

                public String getReje() {
                    return reje;
                }

                public void setReje(String reje) {
                    this.reje = reje;
                }

                public String getRed() {
                    return red;
                }

                public void setRed(String red) {
                    this.red = red;
                }

                public String getYellow() {
                    return yellow;
                }

                public void setYellow(String yellow) {
                    this.yellow = yellow;
                }

                public String getGreen() {
                    return green;
                }

                public void setGreen(String green) {
                    this.green = green;
                }

                public String getAufnr() {
                    return aufnr;
                }

                public void setAufnr(String aufnr) {
                    this.aufnr = aufnr;
                }

                public String getWapnr() {
                    return wapnr;
                }

                public void setWapnr(String wapnr) {
                    this.wapnr = wapnr;
                }

                public String getWapinr() {
                    return wapinr;
                }

                public void setWapinr(String wapinr) {
                    this.wapinr = wapinr;
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

                public String getObjnr() {
                    return objnr;
                }

                public void setObjnr(String objnr) {
                    this.objnr = objnr;
                }

                public String getStxt() {
                    return stxt;
                }

                public void setStxt(String stxt) {
                    this.stxt = stxt;
                }

                public String getArbpl() {
                    return arbpl;
                }

                public void setArbpl(String arbpl) {
                    this.arbpl = arbpl;
                }

                public String getDatefr() {
                    return datefr;
                }

                public void setDatefr(String datefr) {
                    this.datefr = datefr;
                }

                public String getTimefr() {
                    return timefr;
                }

                public void setTimefr(String timefr) {
                    this.timefr = timefr;
                }

                public String getDateto() {
                    return dateto;
                }

                public void setDateto(String dateto) {
                    this.dateto = dateto;
                }

                public String getTimeto() {
                    return timeto;
                }

                public void setTimeto(String timeto) {
                    this.timeto = timeto;
                }

                public String getPriok() {
                    return priok;
                }

                public void setPriok(String priok) {
                    this.priok = priok;
                }

                public String getTplnr() {
                    return tplnr;
                }

                public void setTplnr(String tplnr) {
                    this.tplnr = tplnr;
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

                public String getRefobj() {
                    return refobj;
                }

                public void setRefobj(String refobj) {
                    this.refobj = refobj;
                }
            }


/*For Parsing EtPermitWa*/

            /*For Parsing EtPermitWd*/
            public class EtPermitWd {
                @SerializedName("results")
                @Expose
                private List<EtPermitWd_Result> results = null;

                public List<EtPermitWd_Result> getResults() {
                    return results;
                }

                public void setResults(List<EtPermitWd_Result> results) {
                    this.results = results;
                }
            }

            public class EtPermitWd_Result {
                @SerializedName("Iwerk")
                @Expose
                private String iwerk;
                @SerializedName("Crea")
                @Expose
                private String crea;
                @SerializedName("Prep")
                @Expose
                private String prep;
                @SerializedName("Clsd")
                @Expose
                private String clsd;
                @SerializedName("Reje")
                @Expose
                private String reje;
                @SerializedName("Red")
                @Expose
                private String red;
                @SerializedName("Yellow")
                @Expose
                private String yellow;
                @SerializedName("Green")
                @Expose
                private String green;
                @SerializedName("Aufnr")
                @Expose
                private String aufnr;
                @SerializedName("Wapnr")
                @Expose
                private String wapnr;
                @SerializedName("Wapinr")
                @Expose
                private String wapinr;
                @SerializedName("Wcnr")
                @Expose
                private String wcnr;
                @SerializedName("Objart")
                @Expose
                private String objart;
                @SerializedName("Objtyp")
                @Expose
                private String objtyp;
                @SerializedName("Objnr")
                @Expose
                private String objnr;
                @SerializedName("Stxt")
                @Expose
                private String stxt;
                @SerializedName("Arbpl")
                @Expose
                private String arbpl;
                @SerializedName("Datefr")
                @Expose
                private String datefr;
                @SerializedName("Timefr")
                @Expose
                private String timefr;
                @SerializedName("Dateto")
                @Expose
                private String dateto;
                @SerializedName("Timeto")
                @Expose
                private String timeto;
                @SerializedName("Priok")
                @Expose
                private String priok;
                @SerializedName("Tplnr")
                @Expose
                private String tplnr;
                @SerializedName("Equnr")
                @Expose
                private String equnr;
                @SerializedName("Eqktx")
                @Expose
                private String eqktx;
                @SerializedName("Refobj")
                @Expose
                private String refobj;


                public String getIwerk() {
                    return iwerk;
                }

                public void setIwerk(String iwerk) {
                    this.iwerk = iwerk;
                }

                public String getCrea() {
                    return crea;
                }

                public void setCrea(String crea) {
                    this.crea = crea;
                }

                public String getPrep() {
                    return prep;
                }

                public void setPrep(String prep) {
                    this.prep = prep;
                }

                public String getClsd() {
                    return clsd;
                }

                public void setClsd(String clsd) {
                    this.clsd = clsd;
                }

                public String getReje() {
                    return reje;
                }

                public void setReje(String reje) {
                    this.reje = reje;
                }

                public String getRed() {
                    return red;
                }

                public void setRed(String red) {
                    this.red = red;
                }

                public String getYellow() {
                    return yellow;
                }

                public void setYellow(String yellow) {
                    this.yellow = yellow;
                }

                public String getGreen() {
                    return green;
                }

                public void setGreen(String green) {
                    this.green = green;
                }

                public String getAufnr() {
                    return aufnr;
                }

                public void setAufnr(String aufnr) {
                    this.aufnr = aufnr;
                }

                public String getWapnr() {
                    return wapnr;
                }

                public void setWapnr(String wapnr) {
                    this.wapnr = wapnr;
                }

                public String getWapinr() {
                    return wapinr;
                }

                public void setWapinr(String wapinr) {
                    this.wapinr = wapinr;
                }

                public String getWcnr() {
                    return wcnr;
                }

                public void setWcnr(String wcnr) {
                    this.wcnr = wcnr;
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

                public String getObjnr() {
                    return objnr;
                }

                public void setObjnr(String objnr) {
                    this.objnr = objnr;
                }

                public String getStxt() {
                    return stxt;
                }

                public void setStxt(String stxt) {
                    this.stxt = stxt;
                }

                public String getArbpl() {
                    return arbpl;
                }

                public void setArbpl(String arbpl) {
                    this.arbpl = arbpl;
                }

                public String getDatefr() {
                    return datefr;
                }

                public void setDatefr(String datefr) {
                    this.datefr = datefr;
                }

                public String getTimefr() {
                    return timefr;
                }

                public void setTimefr(String timefr) {
                    this.timefr = timefr;
                }

                public String getDateto() {
                    return dateto;
                }

                public void setDateto(String dateto) {
                    this.dateto = dateto;
                }

                public String getTimeto() {
                    return timeto;
                }

                public void setTimeto(String timeto) {
                    this.timeto = timeto;
                }

                public String getPriok() {
                    return priok;
                }

                public void setPriok(String priok) {
                    this.priok = priok;
                }

                public String getTplnr() {
                    return tplnr;
                }

                public void setTplnr(String tplnr) {
                    this.tplnr = tplnr;
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

                public String getRefobj() {
                    return refobj;
                }

                public void setRefobj(String refobj) {
                    this.refobj = refobj;
                }
/*For Parsing EtPermitWd*/
            }

    }
}






