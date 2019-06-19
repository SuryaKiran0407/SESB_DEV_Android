package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class REST_LoadSettings_SER
{

    @SerializedName("ES_ILOAD")
    @Expose
    private ESILOAD eSILOAD;
    @SerializedName("ES_REFRESH")
    @Expose
    private ESREFRESH eSREFRESH;

    public ESILOAD getESILOAD() {
        return eSILOAD;
    }

    public void setESILOAD(ESILOAD eSILOAD) {
        this.eSILOAD = eSILOAD;
    }

    public ESREFRESH getESREFRESH() {
        return eSREFRESH;
    }

    public void setESREFRESH(ESREFRESH eSREFRESH) {
        this.eSREFRESH = eSREFRESH;
    }




    public class ESREFRESH {

        @SerializedName("VHLP")
        @Expose
        private Boolean vHLP;
        @SerializedName("FLOC")
        @Expose
        private Boolean fLOC;
        @SerializedName("EQUI")
        @Expose
        private Boolean eQUI;
        @SerializedName("EBOM")
        @Expose
        private Boolean eBOM;
        @SerializedName("DNOT")
        @Expose
        private Boolean dNOT;
        @SerializedName("DORD")
        @Expose
        private Boolean dORD;
        @SerializedName("AUTH")
        @Expose
        private Boolean aUTH;

        public Boolean getVHLP() {
            return vHLP;
        }

        public void setVHLP(Boolean vHLP) {
            this.vHLP = vHLP;
        }

        public Boolean getFLOC() {
            return fLOC;
        }

        public void setFLOC(Boolean fLOC) {
            this.fLOC = fLOC;
        }

        public Boolean getEQUI() {
            return eQUI;
        }

        public void setEQUI(Boolean eQUI) {
            this.eQUI = eQUI;
        }

        public Boolean getEBOM() {
            return eBOM;
        }

        public void setEBOM(Boolean eBOM) {
            this.eBOM = eBOM;
        }

        public Boolean getDNOT() {
            return dNOT;
        }

        public void setDNOT(Boolean dNOT) {
            this.dNOT = dNOT;
        }

        public Boolean getDORD() {
            return dORD;
        }

        public void setDORD(Boolean dORD) {
            this.dORD = dORD;
        }

        public Boolean getAUTH() {
            return aUTH;
        }

        public void setAUTH(Boolean aUTH) {
            this.aUTH = aUTH;
        }

    }




    public class ESILOAD {

        @SerializedName("VHLP")
        @Expose
        private Boolean vHLP;
        @SerializedName("FLOC")
        @Expose
        private Boolean fLOC;
        @SerializedName("EQUI")
        @Expose
        private Boolean eQUI;
        @SerializedName("EBOM")
        @Expose
        private Boolean eBOM;
        @SerializedName("DNOT")
        @Expose
        private Boolean dNOT;
        @SerializedName("DORD")
        @Expose
        private Boolean dORD;
        @SerializedName("AUTH")
        @Expose
        private Boolean aUTH;

        public Boolean getVHLP() {
            return vHLP;
        }

        public void setVHLP(Boolean vHLP) {
            this.vHLP = vHLP;
        }

        public Boolean getFLOC() {
            return fLOC;
        }

        public void setFLOC(Boolean fLOC) {
            this.fLOC = fLOC;
        }

        public Boolean getEQUI() {
            return eQUI;
        }

        public void setEQUI(Boolean eQUI) {
            this.eQUI = eQUI;
        }

        public Boolean getEBOM() {
            return eBOM;
        }

        public void setEBOM(Boolean eBOM) {
            this.eBOM = eBOM;
        }

        public Boolean getDNOT() {
            return dNOT;
        }

        public void setDNOT(Boolean dNOT) {
            this.dNOT = dNOT;
        }

        public Boolean getDORD() {
            return dORD;
        }

        public void setDORD(Boolean dORD) {
            this.dORD = dORD;
        }

        public Boolean getAUTH() {
            return aUTH;
        }

        public void setAUTH(Boolean aUTH) {
            this.aUTH = aUTH;
        }

    }


}