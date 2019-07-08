package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EquipmentHistory_SER_REST
{


    @SerializedName("ET_EQUI_NOTIF")
    @Expose
    private ArrayList<ET_EQUI_NOTIF> ET_EQUI_NOTIF;

    public ArrayList<EquipmentHistory_SER_REST.ET_EQUI_NOTIF> getET_EQUI_NOTIF() {
        return ET_EQUI_NOTIF;
    }

    public void setET_EQUI_NOTIF(ArrayList<EquipmentHistory_SER_REST.ET_EQUI_NOTIF> ET_EQUI_NOTIF) {
        this.ET_EQUI_NOTIF = ET_EQUI_NOTIF;
    }

    public class ET_EQUI_NOTIF
    {
        @SerializedName("QMART")
        @Expose
        private String Qmart;
        @SerializedName("QMNUM")
        @Expose
        private String Qmnum;
        @SerializedName("PRIOK")
        @Expose
        private String Priok;
        @SerializedName("QMTXT")
        @Expose
        private String Qmtxt;
        @SerializedName("AUSVN")
        @Expose
        private String Ausvn;
        @SerializedName("AUSBS")
        @Expose
        private String Ausbs;
        @SerializedName("AUFNR")
        @Expose
        private String Aufnr;
        @SerializedName("QMDAB")
        @Expose
        private String Qmdab;
        @SerializedName("MSAUS")
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