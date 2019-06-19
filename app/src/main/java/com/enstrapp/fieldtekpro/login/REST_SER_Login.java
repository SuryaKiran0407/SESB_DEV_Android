
package com.enstrapp.fieldtekpro.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_SER_Login
{

    @SerializedName("ES_USER")
    @Expose
    private ESUSER eSUSER;
    @SerializedName("ET_USRWK")
    @Expose
    private List<ETUSRWK> eTUSRWK = null;

    public ESUSER getESUSER() {
        return eSUSER;
    }

    public void setESUSER(ESUSER eSUSER) {
        this.eSUSER = eSUSER;
    }

    public List<ETUSRWK> getETUSRWK() {
        return eTUSRWK;
    }

    public void setETUSRWK(List<ETUSRWK> eTUSRWK) {
        this.eTUSRWK = eTUSRWK;
    }


    public class ESUSER {

        @SerializedName("MUSER")
        @Expose
        private String mUSER;
        @SerializedName("FNAME")
        @Expose
        private String fNAME;
        @SerializedName("LNAME")
        @Expose
        private String lNAME;
        @SerializedName("KOSTL")
        @Expose
        private String kOSTL;
        @SerializedName("ARBPL")
        @Expose
        private String aRBPL;
        @SerializedName("IWERK")
        @Expose
        private String iWERK;
        @SerializedName("OUNIT")
        @Expose
        private String oUNIT;
        @SerializedName("PERNR")
        @Expose
        private String pERNR;
        @SerializedName("PARVW")
        @Expose
        private String pARVW;
        @SerializedName("USGRP")
        @Expose
        private String uSGRP;

        public String getMUSER() {
            return mUSER;
        }

        public void setMUSER(String mUSER) {
            this.mUSER = mUSER;
        }

        public String getFNAME() {
            return fNAME;
        }

        public void setFNAME(String fNAME) {
            this.fNAME = fNAME;
        }

        public String getLNAME() {
            return lNAME;
        }

        public void setLNAME(String lNAME) {
            this.lNAME = lNAME;
        }

        public String getKOSTL() {
            return kOSTL;
        }

        public void setKOSTL(String kOSTL) {
            this.kOSTL = kOSTL;
        }

        public String getARBPL() {
            return aRBPL;
        }

        public void setARBPL(String aRBPL) {
            this.aRBPL = aRBPL;
        }

        public String getIWERK() {
            return iWERK;
        }

        public void setIWERK(String iWERK) {
            this.iWERK = iWERK;
        }

        public String getOUNIT() {
            return oUNIT;
        }

        public void setOUNIT(String oUNIT) {
            this.oUNIT = oUNIT;
        }

        public String getPERNR() {
            return pERNR;
        }

        public void setPERNR(String pERNR) {
            this.pERNR = pERNR;
        }

        public String getPARVW() {
            return pARVW;
        }

        public void setPARVW(String pARVW) {
            this.pARVW = pARVW;
        }

        public String getUSGRP() {
            return uSGRP;
        }

        public void setUSGRP(String uSGRP) {
            this.uSGRP = uSGRP;
        }

    }



    public class ETUSRWK {

        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("ARBPL")
        @Expose
        private String aRBPL;

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getARBPL() {
            return aRBPL;
        }

        public void setARBPL(String aRBPL) {
            this.aRBPL = aRBPL;
        }

    }


}


