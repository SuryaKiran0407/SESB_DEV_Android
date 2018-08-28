package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SER_BOM_Reservation
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
        @SerializedName("EtMessage")
        @Expose
        private EtMessage etMessage;
        public EtMessage getEtMessage() {
            return etMessage;
        }
        public void setEtMessage(EtMessage etMessage) {
            this.etMessage = etMessage;
        }
    }


    /*For Parsing EtMessage*/
    public class EtMessage
    {
        @SerializedName("results")
        @Expose
        private List<EtMessage_Result> results = null;
        public List<EtMessage_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtMessage_Result> results)
        {
            this.results = results;
        }
    }
    public class EtMessage_Result
    {
        @SerializedName("Message")
        @Expose
        private String Message;
        @SerializedName("Resnum")
        @Expose
        private String Resnum;
        public String getMessage() {
            return Message;
        }
        public void setMessage(String message) {
            Message = message;
        }
        public String getResnum() {
            return Resnum;
        }
        public void setResnum(String Resnum) {
            this.Resnum = Resnum;
        }
    }
    /*For Parsing EtMessage*/
}

