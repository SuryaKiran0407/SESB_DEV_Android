package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Material_Availability_Check_SER
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

    @SerializedName("ET_MESSAGE")
    @Expose
    private List<ETMESSAGE> eTMESSAGE = null;

    public List<ETMESSAGE> geteTMESSAGE() {
        return eTMESSAGE;
    }

    public void seteTMESSAGE(List<ETMESSAGE> eTMESSAGE) {
        this.eTMESSAGE = eTMESSAGE;
    }


    public class D
    {
        @SerializedName("EvAvailable")
        @Expose
        private EvAvailable evAvailable;
        public EvAvailable getEvAvailable()
        {
            return evAvailable;
        }
        public void setEvAvailable(EvAvailable evAvailable)
        {
            this.evAvailable = evAvailable;
        }

        @SerializedName("EV_AVAILABLE")
        @Expose
        private EvAvailable evAvailable1;

        public EvAvailable getEvAvailable1() {
            return evAvailable1;
        }

        public void setEvAvailable1(EvAvailable evAvailable1) {
            this.evAvailable1 = evAvailable1;
        }
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


    public class EvAvailable
    {
        @SerializedName("results")
        @Expose
        private List<EvAvailable_Result> results = null;
        public List<EvAvailable_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EvAvailable_Result> results)
        {
            this.results = results;
        }

        @SerializedName("MESSAGE")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


    public class EvAvailable_Result
    {
        @SerializedName("Message")
        @Expose
        private String message;
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }

}