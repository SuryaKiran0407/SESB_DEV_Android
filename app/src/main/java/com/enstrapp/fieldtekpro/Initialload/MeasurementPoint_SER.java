package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasurementPoint_SER
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
        @SerializedName("EquiMPs")
        @Expose
        private EquiMPs EquiMPs;
        public MeasurementPoint_SER.EquiMPs getEquiMPs() {
            return EquiMPs;
        }
        public void setEquiMPs(MeasurementPoint_SER.EquiMPs equiMPs) {
            EquiMPs = equiMPs;
        }
    }



    public class EquiMPs
    {
        @SerializedName("results")
        @Expose
        private List<EquiMPs_Result> results = null;
        public List<EquiMPs_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EquiMPs_Result> results)
        {
            this.results = results;
        }
    }
    public class EquiMPs_Result
    {
        @SerializedName("Tplnr")
        @Expose
        private String tplnr;
        @SerializedName("Strno")
        @Expose
        private String strno;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
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
        @SerializedName("Mptyp")
        @Expose
        private String mptyp;
        @SerializedName("Atinn")
        @Expose
        private String atinn;
        @SerializedName("Atbez")
        @Expose
        private String atbez;
        @SerializedName("Mrngu")
        @Expose
        private String mrngu;
        @SerializedName("Msehl")
        @Expose
        private String msehl;
        @SerializedName("Desir")
        @Expose
        private String desir;
        @SerializedName("Mrmin")
        @Expose
        private String mrmin;
        @SerializedName("Mrmax")
        @Expose
        private String mrmax;
        @SerializedName("Cdsuf")
        @Expose
        private String cdsuf;
        @SerializedName("Codct")
        @Expose
        private String codct;
        @SerializedName("Codgr")
        @Expose
        private String codgr;

        public String getTplnr() {
            return tplnr;
        }

        public void setTplnr(String tplnr) {
            this.tplnr = tplnr;
        }

        public String getStrno() {
            return strno;
        }

        public void setStrno(String strno) {
            this.strno = strno;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
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

        public String getMptyp() {
            return mptyp;
        }

        public void setMptyp(String mptyp) {
            this.mptyp = mptyp;
        }

        public String getAtinn() {
            return atinn;
        }

        public void setAtinn(String atinn) {
            this.atinn = atinn;
        }

        public String getAtbez() {
            return atbez;
        }

        public void setAtbez(String atbez) {
            this.atbez = atbez;
        }

        public String getMrngu() {
            return mrngu;
        }

        public void setMrngu(String mrngu) {
            this.mrngu = mrngu;
        }

        public String getMsehl() {
            return msehl;
        }

        public void setMsehl(String msehl) {
            this.msehl = msehl;
        }

        public String getDesir() {
            return desir;
        }

        public void setDesir(String desir) {
            this.desir = desir;
        }

        public String getMrmin() {
            return mrmin;
        }

        public void setMrmin(String mrmin) {
            this.mrmin = mrmin;
        }

        public String getMrmax() {
            return mrmax;
        }

        public void setMrmax(String mrmax) {
            this.mrmax = mrmax;
        }

        public String getCdsuf() {
            return cdsuf;
        }

        public void setCdsuf(String cdsuf) {
            this.cdsuf = cdsuf;
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
    }


}