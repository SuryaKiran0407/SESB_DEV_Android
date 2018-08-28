
package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EquipmentInspection_BreakStatics_SER {

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
        @SerializedName("EsBkdneqTotal")
        @Expose
        private EsBkdneqTotal EsBkdneqTotal;
        @SerializedName("EtBkdneqMonthTotal")
        @Expose
        private EtBkdneqMonthTotal EtBkdneqMonthTotal;


        public EtBkdneqMonthTotal getEtBkdneqMonthTotal() {
            return EtBkdneqMonthTotal;
        }

        public void setEtBkdneqMonthTotal(EtBkdneqMonthTotal etBkdneqMonthTotal) {
            EtBkdneqMonthTotal = etBkdneqMonthTotal;
        }

        public EsBkdneqTotal getEsBkdneqTotal() {
            return EsBkdneqTotal;
        }

        public void setEsBkdneqTotal(EsBkdneqTotal esBkdneqTotal) {
            EsBkdneqTotal = esBkdneqTotal;
        }


        /*For Parsing EtBkdneqMonthTotal*/
        public class EtBkdneqMonthTotal {
            @SerializedName("results")
            @Expose
            private List<EtBkdneqMonthTotal_Result> results = null;

            public List<EtBkdneqMonthTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtBkdneqMonthTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtBkdneqMonthTotal_Result {
            @SerializedName("Iwerk")
            @Expose
            private String iwerk;
            @SerializedName("Warea")
            @Expose
            private String warea;
            @SerializedName("Arbpl")
            @Expose
            private String arbpl;
            @SerializedName("Spmon")
            @Expose
            private String spmon;
            @SerializedName("Sunit")
            @Expose
            private String sunit;
            @SerializedName("Smsaus")
            @Expose
            private String smsaus;
            @SerializedName("Count")
            @Expose
            private Integer count;
            @SerializedName("TotM2")
            @Expose
            private String totM2;
            @SerializedName("TotM3")
            @Expose
            private String totM3;
            @SerializedName("Bdpmrat")
            @Expose
            private String bdpmrat;
            @SerializedName("WitHrs")
            @Expose
            private String witHrs;
            @SerializedName("WitoutHrs")
            @Expose
            private String witoutHrs;
            @SerializedName("MttrHours")
            @Expose
            private String mttrHours;
            @SerializedName("MtbrHours")
            @Expose
            private String mtbrHours;
            @SerializedName("Name")
            @Expose
            private String name;

            public String getIwerk() {
                return iwerk;
            }

            public void setIwerk(String iwerk) {
                this.iwerk = iwerk;
            }

            public String getWarea() {
                return warea;
            }

            public void setWarea(String warea) {
                this.warea = warea;
            }

            public String getArbpl() {
                return arbpl;
            }

            public void setArbpl(String arbpl) {
                this.arbpl = arbpl;
            }

            public String getSpmon() {
                return spmon;
            }

            public void setSpmon(String spmon) {
                this.spmon = spmon;
            }

            public String getSunit() {
                return sunit;
            }

            public void setSunit(String sunit) {
                this.sunit = sunit;
            }

            public String getSmsaus() {
                return smsaus;
            }

            public void setSmsaus(String smsaus) {
                this.smsaus = smsaus;
            }

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public String getTotM2() {
                return totM2;
            }

            public void setTotM2(String totM2) {
                this.totM2 = totM2;
            }

            public String getTotM3() {
                return totM3;
            }

            public void setTotM3(String totM3) {
                this.totM3 = totM3;
            }

            public String getBdpmrat() {
                return bdpmrat;
            }

            public void setBdpmrat(String bdpmrat) {
                this.bdpmrat = bdpmrat;
            }

            public String getWitHrs() {
                return witHrs;
            }

            public void setWitHrs(String witHrs) {
                this.witHrs = witHrs;
            }

            public String getWitoutHrs() {
                return witoutHrs;
            }

            public void setWitoutHrs(String witoutHrs) {
                this.witoutHrs = witoutHrs;
            }

            public String getMttrHours() {
                return mttrHours;
            }

            public void setMttrHours(String mttrHours) {
                this.mttrHours = mttrHours;
            }

            public String getMtbrHours() {
                return mtbrHours;
            }

            public void setMtbrHours(String mtbrHours) {
                this.mtbrHours = mtbrHours;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    /*For Parsing EtBkdneqMonthTotal*/


        /*For Parsing EsBkdneqTotal*/
        public class EsBkdneqTotal {
            @SerializedName("results")
            @Expose
            private List<EsBkdneqTotal_Result> results = null;

            public List<EsBkdneqTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EsBkdneqTotal_Result> results) {
                this.results = results;
            }
        }

        public class EsBkdneqTotal_Result {
            @SerializedName("Sunit")
            @Expose
            private String sunit;
            @SerializedName("Smsaus")
            @Expose
            private String smsaus;
            @SerializedName("Sauszt")
            @Expose
            private String sauszt;
            @SerializedName("Seqtbr")
            @Expose
            private String seqtbr;
            @SerializedName("Count")
            @Expose
            private Integer count;
            @SerializedName("TotM2")
            @Expose
            private String totM2;
            @SerializedName("TotM3")
            @Expose
            private String totM3;
            @SerializedName("Bdpmrat")
            @Expose
            private String bdpmrat;
            @SerializedName("WitHrs")
            @Expose
            private String witHrs;
            @SerializedName("WitoutHrs")
            @Expose
            private String witoutHrs;
            @SerializedName("MttrHours")
            @Expose
            private String mttrHours;
            @SerializedName("MtbrHours")
            @Expose
            private String mtbrHours;

            public String getSunit() {
                return sunit;
            }

            public void setSunit(String sunit) {
                this.sunit = sunit;
            }

            public String getSmsaus() {
                return smsaus;
            }

            public void setSmsaus(String smsaus) {
                this.smsaus = smsaus;
            }

            public String getSauszt() {
                return sauszt;
            }

            public void setSauszt(String sauszt) {
                this.sauszt = sauszt;
            }

            public String getSeqtbr() {
                return seqtbr;
            }

            public void setSeqtbr(String seqtbr) {
                this.seqtbr = seqtbr;
            }

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public String getTotM2() {
                return totM2;
            }

            public void setTotM2(String totM2) {
                this.totM2 = totM2;
            }

            public String getTotM3() {
                return totM3;
            }

            public void setTotM3(String totM3) {
                this.totM3 = totM3;
            }

            public String getBdpmrat() {
                return bdpmrat;
            }

            public void setBdpmrat(String bdpmrat) {
                this.bdpmrat = bdpmrat;
            }

            public String getWitHrs() {
                return witHrs;
            }

            public void setWitHrs(String witHrs) {
                this.witHrs = witHrs;
            }

            public String getWitoutHrs() {
                return witoutHrs;
            }

            public void setWitoutHrs(String witoutHrs) {
                this.witoutHrs = witoutHrs;
            }

            public String getMttrHours() {
                return mttrHours;
            }

            public void setMttrHours(String mttrHours) {
                this.mttrHours = mttrHours;
            }

            public String getMtbrHours() {
                return mtbrHours;
            }

            public void setMtbrHours(String mtbrHours) {
                this.mtbrHours = mtbrHours;
            }
        }
    /*For Parsing EsBkdneqTotal*/


    }
}