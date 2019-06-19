package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_Auth_SER
{

    @SerializedName("ET_BUSF")
    @Expose
    private List<ETBUSF> eTBUSF = null;
    @SerializedName("ET_USRF")
    @Expose
    private List<ETUSRF> eTUSRF = null;
    @SerializedName("ES_USER")
    @Expose
    private ESUSER eSUSER;
    @SerializedName("ET_MUSRF")
    @Expose
    private List<ETMUSRF> eTMUSRF = null;

    public List<ETMUSRF> geteTMUSRF() {
        return eTMUSRF;
    }

    public void seteTMUSRF(List<ETMUSRF> eTMUSRF) {
        this.eTMUSRF = eTMUSRF;
    }

    public List<ETBUSF> getETBUSF() {
        return eTBUSF;
    }

    public void setETBUSF(List<ETBUSF> eTBUSF) {
        this.eTBUSF = eTBUSF;
    }

    public List<ETUSRF> getETUSRF() {
        return eTUSRF;
    }

    public void setETUSRF(List<ETUSRF> eTUSRF) {
        this.eTUSRF = eTUSRF;
    }

    public ESUSER getESUSER() {
        return eSUSER;
    }

    public void setESUSER(ESUSER eSUSER) {
        this.eSUSER = eSUSER;
    }




    public class ETMUSRF
    {
        @SerializedName("MANDT")
        @Expose
        private String Mandt;
        @SerializedName("MUSER")
        @Expose
        private String muser;
        @SerializedName("ZDOCTYPE")
        @Expose
        private String Zdoctype;
        @SerializedName("ZACTIVITY")
        @Expose
        private String Zactivity;
        @SerializedName("INACTIVE")
        @Expose
        private String Inactive;

        public String getMandt() {
            return Mandt;
        }

        public void setMandt(String mandt) {
            Mandt = mandt;
        }

        public String getMuser() {
            return muser;
        }

        public void setMuser(String muser) {
            this.muser = muser;
        }

        public String getZdoctype() {
            return Zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            Zdoctype = zdoctype;
        }

        public String getZactivity() {
            return Zactivity;
        }

        public void setZactivity(String zactivity) {
            Zactivity = zactivity;
        }

        public String getInactive() {
            return Inactive;
        }

        public void setInactive(String inactive) {
            Inactive = inactive;
        }
    }




    public class ETUSRF {

        @SerializedName("MANDT")
        @Expose
        private String mANDT;
        @SerializedName("USGRP")
        @Expose
        private String uSGRP;
        @SerializedName("ZDOCTYPE")
        @Expose
        private String zDOCTYPE;
        @SerializedName("ZACTIVITY")
        @Expose
        private String zACTIVITY;
        @SerializedName("INACTIVE")
        @Expose
        private String iNACTIVE;

        public String getMANDT() {
            return mANDT;
        }

        public void setMANDT(String mANDT) {
            this.mANDT = mANDT;
        }

        public String getUSGRP() {
            return uSGRP;
        }

        public void setUSGRP(String uSGRP) {
            this.uSGRP = uSGRP;
        }

        public String getZDOCTYPE() {
            return zDOCTYPE;
        }

        public void setZDOCTYPE(String zDOCTYPE) {
            this.zDOCTYPE = zDOCTYPE;
        }

        public String getZACTIVITY() {
            return zACTIVITY;
        }

        public void setZACTIVITY(String zACTIVITY) {
            this.zACTIVITY = zACTIVITY;
        }

        public String getINACTIVE() {
            return iNACTIVE;
        }

        public void setINACTIVE(String iNACTIVE) {
            this.iNACTIVE = iNACTIVE;
        }

    }





    public class ETBUSF {

        @SerializedName("MANDT")
        @Expose
        private String mANDT;
        @SerializedName("USGRP")
        @Expose
        private String uSGRP;
        @SerializedName("BUSFTYPE")
        @Expose
        private String bUSFTYPE;
        @SerializedName("ACTIVE")
        @Expose
        private String aCTIVE;

        public String getMANDT() {
            return mANDT;
        }

        public void setMANDT(String mANDT) {
            this.mANDT = mANDT;
        }

        public String getUSGRP() {
            return uSGRP;
        }

        public void setUSGRP(String uSGRP) {
            this.uSGRP = uSGRP;
        }

        public String getBUSFTYPE() {
            return bUSFTYPE;
        }

        public void setBUSFTYPE(String bUSFTYPE) {
            this.bUSFTYPE = bUSFTYPE;
        }

        public String getACTIVE() {
            return aCTIVE;
        }

        public void setACTIVE(String aCTIVE) {
            this.aCTIVE = aCTIVE;
        }

    }







    public class ESUSER {

        @SerializedName("SAPUSER")
        @Expose
        private Boolean sAPUSER;
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
        private Integer oUNIT;
        @SerializedName("PERNR")
        @Expose
        private String pERNR;
        @SerializedName("PARVW")
        @Expose
        private String pARVW;
        @SerializedName("USGRP")
        @Expose
        private String uSGRP;

        public Boolean getSAPUSER() {
            return sAPUSER;
        }

        public void setSAPUSER(Boolean sAPUSER) {
            this.sAPUSER = sAPUSER;
        }

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

        public Integer getOUNIT() {
            return oUNIT;
        }

        public void setOUNIT(Integer oUNIT) {
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
}