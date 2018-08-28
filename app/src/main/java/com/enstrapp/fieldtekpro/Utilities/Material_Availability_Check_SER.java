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