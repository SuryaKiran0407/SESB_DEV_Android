package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_BOM_SER
{

    @SerializedName("ET_BOM_HEADER")
    @Expose
    private List<ETBOMHEADER> eTBOMHEADER = null;
    @SerializedName("ET_STOCK")
    @Expose
    private List<ETSTOCK> eTSTOCK = null;
    @SerializedName("ET_BOM_ITEM")
    @Expose
    private List<ETBOMITEM> eTBOMITEM = null;


    public List<ETBOMITEM> geteTBOMITEM() {
        return eTBOMITEM;
    }

    public void seteTBOMITEM(List<ETBOMITEM> eTBOMITEM) {
        this.eTBOMITEM = eTBOMITEM;
    }

    public List<ETBOMHEADER> getETBOMHEADER() {
        return eTBOMHEADER;
    }

    public void setETBOMHEADER(List<ETBOMHEADER> eTBOMHEADER) {
        this.eTBOMHEADER = eTBOMHEADER;
    }

    public List<ETSTOCK> getETSTOCK() {
        return eTSTOCK;
    }

    public void setETSTOCK(List<ETSTOCK> eTSTOCK) {
        this.eTSTOCK = eTSTOCK;
    }





    public class ETBOMITEM
    {
        @SerializedName("BOM")
        @Expose
        private String bom;
        @SerializedName("BOM_COMPONENT")
        @Expose
        private String bomComponent;
        @SerializedName("COMP_TEXT")
        @Expose
        private String compText;
        @SerializedName("QUANTITY")
        @Expose
        private String quantity;
        @SerializedName("UNIT")
        @Expose
        private String unit;
        @SerializedName("STLKZ")
        @Expose
        private String stlkz;

        public String getBom() {
            return bom;
        }

        public void setBom(String bom) {
            this.bom = bom;
        }

        public String getBomComponent() {
            return bomComponent;
        }

        public void setBomComponent(String bomComponent) {
            this.bomComponent = bomComponent;
        }

        public String getCompText() {
            return compText;
        }

        public void setCompText(String compText) {
            this.compText = compText;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStlkz() {
            return stlkz;
        }

        public void setStlkz(String stlkz) {
            this.stlkz = stlkz;
        }
    }




    public class ETBOMHEADER {

        @SerializedName("BOM")
        @Expose
        private String bOM;
        @SerializedName("BOM_DESC")
        @Expose
        private String bOMDESC;
        @SerializedName("PLANT")
        @Expose
        private String pLANT;

        public String getBOM() {
            return bOM;
        }

        public void setBOM(String bOM) {
            this.bOM = bOM;
        }

        public String getBOMDESC() {
            return bOMDESC;
        }

        public void setBOMDESC(String bOMDESC) {
            this.bOMDESC = bOMDESC;
        }

        public String getPLANT() {
            return pLANT;
        }

        public void setPLANT(String pLANT) {
            this.pLANT = pLANT;
        }

    }






    public class ETSTOCK {

        @SerializedName("MATNR")
        @Expose
        private String mATNR;
        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("MAKTX")
        @Expose
        private String mAKTX;
        @SerializedName("LGORT")
        @Expose
        private String lGORT;
        @SerializedName("NAME1")
        @Expose
        private String nAME1;
        @SerializedName("LGOBE")
        @Expose
        private String lGOBE;
        @SerializedName("LABST")
        @Expose
        private String Labst;
        @SerializedName("SPEME")
        @Expose
        private String Speme;
        @SerializedName("BWTAR")
        @Expose
        private String Bwtar;
        @SerializedName("LGPBE")
        @Expose
        private String Lgpbe;
        @SerializedName("EKGRP")
        @Expose
        private String Ekgrp;

        public String getLabst() {
            return Labst;
        }

        public void setLabst(String labst) {
            Labst = labst;
        }

        public String getSpeme() {
            return Speme;
        }

        public void setSpeme(String speme) {
            Speme = speme;
        }

        public String getBwtar() {
            return Bwtar;
        }

        public void setBwtar(String bwtar) {
            Bwtar = bwtar;
        }

        public String getLgpbe() {
            return Lgpbe;
        }

        public void setLgpbe(String lgpbe) {
            Lgpbe = lgpbe;
        }

        public String getEkgrp() {
            return Ekgrp;
        }

        public void setEkgrp(String ekgrp) {
            Ekgrp = ekgrp;
        }

        public String getMATNR() {
            return mATNR;
        }

        public void setMATNR(String mATNR) {
            this.mATNR = mATNR;
        }

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getMAKTX() {
            return mAKTX;
        }

        public void setMAKTX(String mAKTX) {
            this.mAKTX = mAKTX;
        }

        public String getLGORT() {
            return lGORT;
        }

        public void setLGORT(String lGORT) {
            this.lGORT = lGORT;
        }

        public String getNAME1() {
            return nAME1;
        }

        public void setNAME1(String nAME1) {
            this.nAME1 = nAME1;
        }

        public String getLGOBE() {
            return lGOBE;
        }

        public void setLGOBE(String lGOBE) {
            this.lGOBE = lGOBE;
        }

    }




}