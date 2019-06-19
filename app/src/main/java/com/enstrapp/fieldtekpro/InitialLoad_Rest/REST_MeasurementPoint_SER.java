package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_MeasurementPoint_SER
{

    @SerializedName("ET_EQUI_MPTT")
    @Expose
    private List<ETEQUIMPTT> eTEQUIMPTT = null;

    public List<ETEQUIMPTT> getETEQUIMPTT() {
        return eTEQUIMPTT;
    }

    public void setETEQUIMPTT(List<ETEQUIMPTT> eTEQUIMPTT) {
        this.eTEQUIMPTT = eTEQUIMPTT;
    }






    public class ETEQUIMPTT {

        @SerializedName("EQUNR")
        @Expose
        private String eQUNR;
        @SerializedName("POINT")
        @Expose
        private String pOINT;
        @SerializedName("MPOBJ")
        @Expose
        private String mPOBJ;
        @SerializedName("PTTXT")
        @Expose
        private String pTTXT;
        @SerializedName("MPTYP")
        @Expose
        private String mPTYP;
        @SerializedName("ATINN")
        @Expose
        private String aTINN;
        @SerializedName("ATBEZ")
        @Expose
        private String aTBEZ;
        @SerializedName("MRNGU")
        @Expose
        private String mRNGU;
        @SerializedName("MSEHL")
        @Expose
        private String mSEHL;
        @SerializedName("DESIR")
        @Expose
        private String dESIR;
        @SerializedName("MRMIN")
        @Expose
        private String mRMIN;
        @SerializedName("MRMAX")
        @Expose
        private String mRMAX;
        @SerializedName("TPLNR")
        @Expose
        private String tplnr;
        @SerializedName("STRNO")
        @Expose
        private String strno;
        @SerializedName("MPOBT")
        @Expose
        private String mpobt;
        @SerializedName("PSORT")
        @Expose
        private String psort;
        @SerializedName("Cdsuf")
        @Expose
        private String cdsuf;
        @SerializedName("Codct")
        @Expose
        private String codct;
        @SerializedName("Codgr")
        @Expose
        private String codgr;

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

        public String getStrno() {
            return strno;
        }

        public void setStrno(String strno) {
            this.strno = strno;
        }

        public String getTplnr() {
            return tplnr;
        }

        public void setTplnr(String tplnr) {
            this.tplnr = tplnr;
        }

        public String getEQUNR() {
            return eQUNR;
        }

        public void setEQUNR(String eQUNR) {
            this.eQUNR = eQUNR;
        }

        public String getPOINT() {
            return pOINT;
        }

        public void setPOINT(String pOINT) {
            this.pOINT = pOINT;
        }

        public String getMPOBJ() {
            return mPOBJ;
        }

        public void setMPOBJ(String mPOBJ) {
            this.mPOBJ = mPOBJ;
        }

        public String getPTTXT() {
            return pTTXT;
        }

        public void setPTTXT(String pTTXT) {
            this.pTTXT = pTTXT;
        }

        public String getMPTYP() {
            return mPTYP;
        }

        public void setMPTYP(String mPTYP) {
            this.mPTYP = mPTYP;
        }

        public String getATINN() {
            return aTINN;
        }

        public void setATINN(String aTINN) {
            this.aTINN = aTINN;
        }

        public String getATBEZ() {
            return aTBEZ;
        }

        public void setATBEZ(String aTBEZ) {
            this.aTBEZ = aTBEZ;
        }

        public String getMRNGU() {
            return mRNGU;
        }

        public void setMRNGU(String mRNGU) {
            this.mRNGU = mRNGU;
        }

        public String getMSEHL() {
            return mSEHL;
        }

        public void setMSEHL(String mSEHL) {
            this.mSEHL = mSEHL;
        }

        public String getDESIR() {
            return dESIR;
        }

        public void setDESIR(String dESIR) {
            this.dESIR = dESIR;
        }

        public String getMRMIN() {
            return mRMIN;
        }

        public void setMRMIN(String mRMIN) {
            this.mRMIN = mRMIN;
        }

        public String getMRMAX() {
            return mRMAX;
        }

        public void setMRMAX(String mRMAX) {
            this.mRMAX = mRMAX;
        }

    }


}