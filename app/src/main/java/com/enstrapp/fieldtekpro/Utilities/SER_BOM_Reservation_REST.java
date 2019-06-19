package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SER_BOM_Reservation_REST
{
    @SerializedName("ET_MESSAGE")
    @Expose
    private List<ETMESSAGE> eTMESSAGE = null;
    @SerializedName("EV_RESNUM")
    @Expose
    private  String evresnum;


    public List<ETMESSAGE> geteTMESSAGE() {
        return eTMESSAGE;
    }

    public void seteTMESSAGE(List<ETMESSAGE> eTMESSAGE) {
        this.eTMESSAGE = eTMESSAGE;
    }

    public String getEvresnum() {
        return evresnum;
    }

    public void setEvresnum(String evresnum) {
        this.evresnum = evresnum;
    }

    public class ETMESSAGE {
        @SerializedName("MESSAGE")
        @Expose
        private String mESSAGE;

        public String getMESSAGE() {
            return mESSAGE;
        }

        public void setMESSAGE(String mESSAGE) {
            this.mESSAGE = mESSAGE;
        }
    }


}

