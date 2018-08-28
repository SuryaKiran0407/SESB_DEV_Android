package com.enstrapp.fieldtekpro.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Enstrapp on 21/02/18.
 */

public class BreakStatsPie_SER {
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


        @SerializedName("EsBkdnTotal")
        @Expose
        private EsBkdnTotal esBkdnTotal;
        @SerializedName("EtBkdnPlantTotal")
        @Expose
        private EtBkdnPlantTotal etBkdnPlantTotal;
        @SerializedName("EtBkdnWareaTotal")
        @Expose
        private EtBkdnWareaTotal etBkdnWareaTotal;
        @SerializedName("EtBkdnArbplTotal")
        @Expose
        private EtBkdnArbplTotal etBkdnArbplTotal;
        @SerializedName("EtBkdnPmonthTotal")
        @Expose
        private EtBkdnPmonthTotal etBkdnPmonthTotal;
        @SerializedName("EtBkdnMonthTotal")
        @Expose
        private EtBkdnMonthTotal etBkdnMonthTotal;


        public EsBkdnTotal getEsBkdnTotal() {
            return esBkdnTotal;
        }

        public void setEsBkdnTotal(EsBkdnTotal esBkdnTotal) {
            this.esBkdnTotal = esBkdnTotal;
        }

        public EtBkdnPlantTotal getEtBkdnPlantTotal() {
            return etBkdnPlantTotal;
        }

        public void setEtBkdnPlantTotal(EtBkdnPlantTotal etBkdnPlantTotal) {
            this.etBkdnPlantTotal = etBkdnPlantTotal;
        }

        public EtBkdnWareaTotal getEtBkdnWareaTotal() {
            return etBkdnWareaTotal;
        }

        public void setEtBkdnWareaTotal(EtBkdnWareaTotal etBkdnWareaTotal) {
            this.etBkdnWareaTotal = etBkdnWareaTotal;
        }

        public EtBkdnArbplTotal getEtBkdnArbplTotal() {
            return etBkdnArbplTotal;
        }

        public void setEtBkdnArbplTotal(EtBkdnArbplTotal etBkdnArbplTotal) {
            this.etBkdnArbplTotal = etBkdnArbplTotal;
        }

        public EtBkdnPmonthTotal getEtBkdnPmonthTotal() {
            return etBkdnPmonthTotal;
        }

        public void setEtBkdnPmonthTotal(EtBkdnPmonthTotal etBkdnPmonthTotal) {
            this.etBkdnPmonthTotal = etBkdnPmonthTotal;
        }

        public EtBkdnMonthTotal getEtBkdnMonthTotal() {
            return etBkdnMonthTotal;
        }

        public void setEtBkdnMonthTotal(EtBkdnMonthTotal etBkdnMonthTotal) {
            this.etBkdnMonthTotal = etBkdnMonthTotal;
        }

        /*For Parsing EsBkdnTotal*/
        public class EsBkdnTotal {
            @SerializedName("results")
            @Expose
            private List<EsBkdnTotal_Result> results = null;

            public List<EsBkdnTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EsBkdnTotal_Result> results) {
                this.results = results;
            }
        }


        public class EsBkdnTotal_Result {
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
/*For Parsing EsBkdnTotal*/

        /*For Parsing  EtBkdnPlantTotal */

        public class EtBkdnPlantTotal {
            @SerializedName("results")
            @Expose
            private List<EtBkdnPlantTotal_Result> results = null;

            public List<EtBkdnPlantTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtBkdnPlantTotal_Result> results) {
                this.results = results;
            }
        }


        public class EtBkdnPlantTotal_Result {

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
            private final static long serialVersionUID = -6225379931150898655L;

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

        /*For Parsing  EtBkdnPlantTotal */

        /*For Parsing  EtBkdnWareaTotal */
        public class EtBkdnWareaTotal {
            @SerializedName("results")
            @Expose
            private List<EtBkdnWareaTotal_Result> results = null;

            public List<EtBkdnWareaTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtBkdnWareaTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtBkdnWareaTotal_Result {
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
            private final static long serialVersionUID = -6225379931150898655L;

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

        /*For Parsing  EtBkdnWareaTotal */

                /*For Parsing  EtBkdnArbplTotal */

        public class EtBkdnArbplTotal {
            @SerializedName("results")
            @Expose
            private List<EtBkdnArbplTotal_Result> results = null;

            public List<EtBkdnArbplTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtBkdnArbplTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtBkdnArbplTotal_Result {

            @SerializedName("Iwerk")
            @Expose
            private String iwerk;
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
            private final static long serialVersionUID = -3740149924289147146L;

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
                /*For Parsing  EtBkdnArbplTotal */


        /*For Parsing  EtBkdnPmonthTotal */
        public class EtBkdnPmonthTotal {
            @SerializedName("results")
            @Expose
            private List<EtBkdnPmonthTotal_Result> results = null;

            public List<EtBkdnPmonthTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtBkdnPmonthTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtBkdnPmonthTotal_Result

        {
            @SerializedName("Iwerk")
            @Expose
            private String iwerk;
            @SerializedName("Warea")
            @Expose
            private String warea;
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
            private final static long serialVersionUID = -2859152840120096260L;

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

/*For Parsing  EtBkdnPmonthTotal */


/*For Parsing  EtBkdnMonthTotal */

        public class EtBkdnMonthTotal {
            @SerializedName("results")
            @Expose
            private List<EtBkdnMonthTotal_Result> results = null;

            public List<EtBkdnMonthTotal_Result> getResults() {
                return results;
            }

            public void setResults(List<EtBkdnMonthTotal_Result> results) {
                this.results = results;
            }
        }

        public class EtBkdnMonthTotal_Result {
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
            private final static long serialVersionUID = -6225379931150898655L;

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


    }
}
