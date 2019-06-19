package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_SyncMap_SER
{

    @SerializedName("ET_SYNC_MAP")
    @Expose
    private List<ETSYNCMAP> eTSYNCMAP = null;

    public List<ETSYNCMAP> getETSYNCMAP() {
        return eTSYNCMAP;
    }

    public void setETSYNCMAP(List<ETSYNCMAP> eTSYNCMAP) {
        this.eTSYNCMAP = eTSYNCMAP;
    }



    public class ETSYNCMAP {

        @SerializedName("ENDPOINT")
        @Expose
        private String eNDPOINT;
        @SerializedName("SYSID")
        @Expose
        private String sYSID;
        @SerializedName("ENDPOINT_DETAILS")
        @Expose
        private List<ENDPOINTDETAIL> eNDPOINTDETAILS = null;

        public String getENDPOINT() {
            return eNDPOINT;
        }

        public void setENDPOINT(String eNDPOINT) {
            this.eNDPOINT = eNDPOINT;
        }

        public String getSYSID() {
            return sYSID;
        }

        public void setSYSID(String sYSID) {
            this.sYSID = sYSID;
        }

        public List<ENDPOINTDETAIL> getENDPOINTDETAILS() {
            return eNDPOINTDETAILS;
        }

        public void setENDPOINTDETAILS(List<ENDPOINTDETAIL> eNDPOINTDETAILS) {
            this.eNDPOINTDETAILS = eNDPOINTDETAILS;
        }

    }


    public class ENDPOINTDETAIL {

        @SerializedName("ZDOCTYPE")
        @Expose
        private String zDOCTYPE;
        @SerializedName("ZACTIVITY")
        @Expose
        private String zACTIVITY;
        @SerializedName("ZWSRV")
        @Expose
        private String zWSRV;

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

        public String getZWSRV() {
            return zWSRV;
        }

        public void setZWSRV(String zWSRV) {
            this.zWSRV = zWSRV;
        }

    }

}