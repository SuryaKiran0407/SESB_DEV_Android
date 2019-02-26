package com.enstrapp.fieldtekpro.maintenance_plan;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaintenancePlan_SER
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
        @SerializedName("Abnum")
        @Expose
        private Integer abnum;
        @SerializedName("Warpl")
        @Expose
        private String warpl;
        @SerializedName("Wptxt")
        @Expose
        private String wptxt;
        @SerializedName("Wapos")
        @Expose
        private String wapos;
        @SerializedName("Pstxt")
        @Expose
        private String pstxt;
        @SerializedName("Strat")
        @Expose
        private String strat;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
        @SerializedName("Mptyp")
        @Expose
        private String mptyp;
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Gstrpe")
        @Expose
        private String gstrpe;
        @SerializedName("Arbpl")
        @Expose
        private String arbpl;
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Gstrp")
        @Expose
        private String gstrp;
        @SerializedName("Addat")
        @Expose
        private String addat;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Mityp")
        @Expose
        private String mityp;
        @SerializedName("Strno")
        @Expose
        private String strno;
        @SerializedName("Eqktx")
        @Expose
        private String eqktx;
        @SerializedName("Pltxt")
        @Expose
        private String pltxt;

        public Integer getAbnum() {
            return abnum;
        }

        public void setAbnum(Integer abnum) {
            this.abnum = abnum;
        }

        public String getWarpl() {
            return warpl;
        }

        public void setWarpl(String warpl) {
            this.warpl = warpl;
        }

        public String getWptxt() {
            return wptxt;
        }

        public void setWptxt(String wptxt) {
            this.wptxt = wptxt;
        }

        public String getWapos() {
            return wapos;
        }

        public void setWapos(String wapos) {
            this.wapos = wapos;
        }

        public String getPstxt() {
            return pstxt;
        }

        public void setPstxt(String pstxt) {
            this.pstxt = pstxt;
        }

        public String getStrat() {
            return strat;
        }

        public void setStrat(String strat) {
            this.strat = strat;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getMptyp() {
            return mptyp;
        }

        public void setMptyp(String mptyp) {
            this.mptyp = mptyp;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getGstrpe() {
            return gstrpe;
        }

        public void setGstrpe(String gstrpe) {
            this.gstrpe = gstrpe;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }

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

        public String getGstrp() {
            return gstrp;
        }

        public void setGstrp(String gstrp) {
            this.gstrp = gstrp;
        }

        public String getAddat() {
            return addat;
        }

        public void setAddat(String addat) {
            this.addat = addat;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMityp() {
            return mityp;
        }

        public void setMityp(String mityp) {
            this.mityp = mityp;
        }

        public String getStrno() {
            return strno;
        }

        public void setStrno(String strno) {
            this.strno = strno;
        }

        public String getEqktx() {
            return eqktx;
        }

        public void setEqktx(String eqktx) {
            this.eqktx = eqktx;
        }

        public String getPltxt() {
            return pltxt;
        }

        public void setPltxt(String pltxt) {
            this.pltxt = pltxt;
        }
    }


}