package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BOM_SER
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
        @SerializedName("EtBomHeader")
        @Expose
        private EtBomHeader EtBomHeader;
        @SerializedName("EtBomItem")
        @Expose
        private EtBomItem EtBomItem;
        @SerializedName("EtStock")
        @Expose
        private EtStock EtStock;
        public BOM_SER.EtBomHeader getEtBomHeader() {
            return EtBomHeader;
        }
        public void setEtBomHeader(BOM_SER.EtBomHeader etBomHeader) {
            EtBomHeader = etBomHeader;
        }
        public BOM_SER.EtBomItem getEtBomItem() {
            return EtBomItem;
        }
        public void setEtBomItem(BOM_SER.EtBomItem etBomItem) {
            EtBomItem = etBomItem;
        }
        public BOM_SER.EtStock getEtStock() {
            return EtStock;
        }
        public void setEtStock(BOM_SER.EtStock etStock) {
            EtStock = etStock;
        }
    }



    /*For Parsing EtStock*/
    public class EtStock
    {
        @SerializedName("results")
        @Expose
        private List<EtStock_Result> results = null;
        public List<EtStock_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtStock_Result> results)
        {
            this.results = results;
        }
    }
    public class EtStock_Result
    {
        @SerializedName("Matnr")
        @Expose
        private String matnr;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Maktx")
        @Expose
        private String maktx;
        @SerializedName("Lgort")
        @Expose
        private String lgort;
        @SerializedName("Labst")
        @Expose
        private String labst;
        @SerializedName("Speme")
        @Expose
        private String speme;
        @SerializedName("Lgpbe")
        @Expose
        private String lgpbe;
        @SerializedName("Bwtar")
        @Expose
        private String bwtar;

        public String getBwtar() {
            return bwtar;
        }

        public void setBwtar(String bwtar) {
            this.bwtar = bwtar;
        }

        public String getMatnr() {
            return matnr;
        }

        public void setMatnr(String matnr) {
            this.matnr = matnr;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getMaktx() {
            return maktx;
        }

        public void setMaktx(String maktx) {
            this.maktx = maktx;
        }

        public String getLgort() {
            return lgort;
        }

        public void setLgort(String lgort) {
            this.lgort = lgort;
        }

        public String getLabst() {
            return labst;
        }

        public void setLabst(String labst) {
            this.labst = labst;
        }

        public String getSpeme() {
            return speme;
        }

        public void setSpeme(String speme) {
            this.speme = speme;
        }

        public String getLgpbe() {
            return lgpbe;
        }

        public void setLgpbe(String lgpbe) {
            this.lgpbe = lgpbe;
        }
    }
    /*For Parsing EtStock*/


    /*For Parsing EtBomItem*/
    public class EtBomItem
    {
        @SerializedName("results")
        @Expose
        private List<EtBomItem_Result> results = null;
        public List<EtBomItem_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtBomItem_Result> results)
        {
            this.results = results;
        }
    }
    public class EtBomItem_Result
    {
        @SerializedName("Bom")
        @Expose
        private String bom;
        @SerializedName("BomComponent")
        @Expose
        private String bomComponent;
        @SerializedName("CompText")
        @Expose
        private String compText;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("Stlkz")
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
    /*For Parsing EtBomItem*/


    /*For Parsing EtBomHeader*/
    public class EtBomHeader
    {
        @SerializedName("results")
        @Expose
        private List<EtBomHeader_Result> results = null;
        public List<EtBomHeader_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtBomHeader_Result> results)
        {
            this.results = results;
        }
    }
    public class EtBomHeader_Result
    {
        @SerializedName("Bom")
        @Expose
        private String bom;
        @SerializedName("BomDesc")
        @Expose
        private String bomDesc;
        @SerializedName("Plant")
        @Expose
        private String plant;

        public String getBom() {
            return bom;
        }

        public void setBom(String bom) {
            this.bom = bom;
        }

        public String getBomDesc() {
            return bomDesc;
        }

        public void setBomDesc(String bomDesc) {
            this.bomDesc = bomDesc;
        }

        public String getPlant() {
            return plant;
        }

        public void setPlant(String plant) {
            this.plant = plant;
        }
    }
    /*For Parsing EtBomHeader*/



}