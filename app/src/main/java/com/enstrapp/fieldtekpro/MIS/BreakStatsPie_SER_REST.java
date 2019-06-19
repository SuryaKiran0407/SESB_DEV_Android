package com.enstrapp.fieldtekpro.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enstrapp on 21/02/18.
 */

public class BreakStatsPie_SER_REST
{
    @SerializedName("ES_BKDN_TOTAL")
    @Expose
    private EsBkdnTotal esBkdnTotal;
    @SerializedName("ET_BKDN_PLANT_TOTAL")
    @Expose
    private ArrayList<EtBkdnPlantTotal> etBkdnPlantTotal;
    @SerializedName("ET_BKDN_WAREA_TOTAL")
    @Expose
    private ArrayList<EtBkdnWareaTotal>  etBkdnWareaTotal;
    @SerializedName("ET_BKDN_ARBPL_TOTAL")
    @Expose
    private ArrayList<EtBkdnArbplTotal> etBkdnArbplTotal;
    @SerializedName("ET_BKDN_PMONTH_TOTAL")
    @Expose
    private ArrayList<EtBkdnPmonthTotal> etBkdnPmonthTotal;
    @SerializedName("ET_BKDN_MONTH_TOTAL")
    @Expose
    private ArrayList<EtBkdnMonthTotal> etBkdnMonthTotal;


    public ArrayList<EtBkdnWareaTotal> getEtBkdnWareaTotal() {
        return etBkdnWareaTotal;
    }

    public void setEtBkdnWareaTotal(ArrayList<EtBkdnWareaTotal> etBkdnWareaTotal) {
        this.etBkdnWareaTotal = etBkdnWareaTotal;
    }

    public ArrayList<EtBkdnArbplTotal> getEtBkdnArbplTotal() {
        return etBkdnArbplTotal;
    }

    public void setEtBkdnArbplTotal(ArrayList<EtBkdnArbplTotal> etBkdnArbplTotal) {
        this.etBkdnArbplTotal = etBkdnArbplTotal;
    }

    public ArrayList<EtBkdnPmonthTotal> getEtBkdnPmonthTotal() {
        return etBkdnPmonthTotal;
    }

    public void setEtBkdnPmonthTotal(ArrayList<EtBkdnPmonthTotal> etBkdnPmonthTotal) {
        this.etBkdnPmonthTotal = etBkdnPmonthTotal;
    }

    public ArrayList<EtBkdnMonthTotal> getEtBkdnMonthTotal() {
        return etBkdnMonthTotal;
    }

    public void setEtBkdnMonthTotal(ArrayList<EtBkdnMonthTotal> etBkdnMonthTotal) {
        this.etBkdnMonthTotal = etBkdnMonthTotal;
    }

    public ArrayList<EtBkdnPlantTotal> getEtBkdnPlantTotal() {
        return etBkdnPlantTotal;
    }

    public void setEtBkdnPlantTotal(ArrayList<EtBkdnPlantTotal> etBkdnPlantTotal) {
        this.etBkdnPlantTotal = etBkdnPlantTotal;
    }

    public EsBkdnTotal getEsBkdnTotal() {
        return esBkdnTotal;
    }

    public void setEsBkdnTotal(EsBkdnTotal esBkdnTotal) {
        this.esBkdnTotal = esBkdnTotal;
    }



    public class EsBkdnTotal {
        @SerializedName("SUNIT")
        @Expose
        private String sunit;
        @SerializedName("SMSAUS")
        @Expose
        private String smsaus;
        @SerializedName("SAUSZT")
        @Expose
        private String sauszt;
        @SerializedName("SEQTBR")
        @Expose
        private String seqtbr;
        @SerializedName("COUNT")
        @Expose
        private Integer count;
        @SerializedName("TOT_M2")
        @Expose
        private String totM2;
        @SerializedName("TOT_M3")
        @Expose
        private String totM3;
        @SerializedName("BDPMRAT")
        @Expose
        private String bdpmrat;
        @SerializedName("WIT_HRS")
        @Expose
        private String witHrs;
        @SerializedName("WITOUT_HRS")
        @Expose
        private String witoutHrs;
        @SerializedName("MTTR_HOURS")
        @Expose
        private String mttrHours;
        @SerializedName("MTBR_HOURS")
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




    public class EtBkdnPlantTotal {

        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("WAREA")
        @Expose
        private String warea;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("SPMON")
        @Expose
        private String spmon;
        @SerializedName("SUNIT")
        @Expose
        private String sunit;
        @SerializedName("SMSAUS")
        @Expose
        private String smsaus;
        @SerializedName("COUNT")
        @Expose
        private Integer count;
        @SerializedName("TOT_M2")
        @Expose
        private String totM2;
        @SerializedName("TOT_M3")
        @Expose
        private String totM3;
        @SerializedName("BDPMRAT")
        @Expose
        private String bdpmrat;
        @SerializedName("WIT_HRS")
        @Expose
        private String witHrs;
        @SerializedName("WITOUT_HRS")
        @Expose
        private String witoutHrs;
        @SerializedName("MTTR_HOURS")
        @Expose
        private String mttrHours;
        @SerializedName("MTBR_HOURS")
        @Expose
        private String mtbrHours;
        @SerializedName("NAME")
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




    public class EtBkdnWareaTotal {
        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("WAREA")
        @Expose
        private String warea;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("SPMON")
        @Expose
        private String spmon;
        @SerializedName("SUNIT")
        @Expose
        private String sunit;
        @SerializedName("SMSAUS")
        @Expose
        private String smsaus;
        @SerializedName("COUNT")
        @Expose
        private Integer count;
        @SerializedName("TOT_M2")
        @Expose
        private String totM2;
        @SerializedName("TOT_M3")
        @Expose
        private String totM3;
        @SerializedName("BDPMRAT")
        @Expose
        private String bdpmrat;
        @SerializedName("WIT_HRS")
        @Expose
        private String witHrs;
        @SerializedName("WITOUT_HRS")
        @Expose
        private String witoutHrs;
        @SerializedName("MTTR_HOURS")
        @Expose
        private String mttrHours;
        @SerializedName("MTBR_HOURS")
        @Expose
        private String mtbrHours;
        @SerializedName("name")
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




    public class EtBkdnArbplTotal {

        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("SPMON")
        @Expose
        private String spmon;
        @SerializedName("SUNIT")
        @Expose
        private String sunit;
        @SerializedName("SMSAUS")
        @Expose
        private String smsaus;
        @SerializedName("COUNT")
        @Expose
        private Integer count;
        @SerializedName("TOT_M2")
        @Expose
        private String totM2;
        @SerializedName("TOT_M3")
        @Expose
        private String totM3;
        @SerializedName("BDPMRAT")
        @Expose
        private String bdpmrat;
        @SerializedName("WIT_HRS")
        @Expose
        private String witHrs;
        @SerializedName("WITOUT_HRS")
        @Expose
        private String witoutHrs;
        @SerializedName("MTTR_HOURS")
        @Expose
        private String mttrHours;
        @SerializedName("MTBR_HOURS")
        @Expose
        private String mtbrHours;
        @SerializedName("NAME")
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



    public class EtBkdnPmonthTotal
    {
        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("WAREA")
        @Expose
        private String warea;
        @SerializedName("SPMON")
        @Expose
        private String spmon;
        @SerializedName("SUNIT")
        @Expose
        private String sunit;
        @SerializedName("SMSAUS")
        @Expose
        private String smsaus;
        @SerializedName("COUNT")
        @Expose
        private Integer count;
        @SerializedName("TOT_M2")
        @Expose
        private String totM2;
        @SerializedName("TOT_M3")
        @Expose
        private String totM3;
        @SerializedName("BDPMRAT")
        @Expose
        private String bdpmrat;
        @SerializedName("WIT_HRS")
        @Expose
        private String witHrs;
        @SerializedName("WITOUT_HRS")
        @Expose
        private String witoutHrs;
        @SerializedName("MTTR_HOURS")
        @Expose
        private String mttrHours;
        @SerializedName("MTBR_HOURS")
        @Expose
        private String mtbrHours;
        @SerializedName("NAME")
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



    public class EtBkdnMonthTotal {
        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("WAREA")
        @Expose
        private String warea;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("SPMON")
        @Expose
        private String spmon;
        @SerializedName("SUNIT")
        @Expose
        private String sunit;
        @SerializedName("SMSAUS")
        @Expose
        private String smsaus;
        @SerializedName("COUNT")
        @Expose
        private Integer count;
        @SerializedName("TOT_M2")
        @Expose
        private String totM2;
        @SerializedName("TOT_M3")
        @Expose
        private String totM3;
        @SerializedName("BDPMRAT")
        @Expose
        private String bdpmrat;
        @SerializedName("WIT_HRS")
        @Expose
        private String witHrs;
        @SerializedName("WITOUT_HRS")
        @Expose
        private String witoutHrs;
        @SerializedName("MTTR_HOURS")
        @Expose
        private String mttrHours;
        @SerializedName("MTBR_HOURS")
        @Expose
        private String mtbrHours;
        @SerializedName("NAME")
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
