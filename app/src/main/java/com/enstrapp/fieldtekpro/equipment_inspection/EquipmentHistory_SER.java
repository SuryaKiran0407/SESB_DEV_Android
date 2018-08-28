package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EquipmentHistory_SER
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
        @SerializedName("NavEquipHistory")
        @Expose
        private NavEquipHistory navEquipHistory;
        public NavEquipHistory getNavEquipHistory() {
            return navEquipHistory;
        }
        public void setNavEquipHistory(NavEquipHistory navEquipHistory) {
            this.navEquipHistory = navEquipHistory;
        }
    }


    public class NavEquipHistory
    {
        @SerializedName("results")
        @Expose
        private List<NavEquipHistory_Result> results = null;
        public List<NavEquipHistory_Result> getResults()
        {
            return results;
        }
        public void setResults(List<NavEquipHistory_Result> results)
        {
            this.results = results;
        }
    }


    public class NavEquipHistory_Result
    {
        @SerializedName("Qmart")
        @Expose
        private String Qmart;
        @SerializedName("Qmnum")
        @Expose
        private String Qmnum;
        @SerializedName("Priok")
        @Expose
        private String Priok;
        @SerializedName("Qmtxt")
        @Expose
        private String Qmtxt;
        @SerializedName("Ausvn")
        @Expose
        private String Ausvn;
        @SerializedName("Ausbs")
        @Expose
        private String Ausbs;
        @SerializedName("Aufnr")
        @Expose
        private String Aufnr;
        @SerializedName("Qmdab")
        @Expose
        private String Qmdab;
        @SerializedName("Msaus")
        @Expose
        private String Msaus;

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

        public String getPriok() {
            return Priok;
        }

        public void setPriok(String priok) {
            Priok = priok;
        }

        public String getQmtxt() {
            return Qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            Qmtxt = qmtxt;
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

        public String getAufnr() {
            return Aufnr;
        }

        public void setAufnr(String aufnr) {
            Aufnr = aufnr;
        }

        public String getQmdab() {
            return Qmdab;
        }

        public void setQmdab(String qmdab) {
            Qmdab = qmdab;
        }

        public String getMsaus() {
            return Msaus;
        }

        public void setMsaus(String msaus) {
            Msaus = msaus;
        }
    }


}