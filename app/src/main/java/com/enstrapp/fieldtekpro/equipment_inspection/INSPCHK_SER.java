package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class INSPCHK_SER
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
        @SerializedName("EtMsg")
        @Expose
        private EtMsg etMsg;
        public EtMsg getEtMsg() {
            return etMsg;
        }
        public void setEtMsg(EtMsg etMsg) {
            this.etMsg = etMsg;
        }
    }


    public class EtMsg
    {
        @SerializedName("results")
        @Expose
        private List<EtMsg_Result> results = null;
        public List<EtMsg_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtMsg_Result> results)
        {
            this.results = results;
        }
    }
    public class EtMsg_Result
    {
        @SerializedName("Message")
        @Expose
        private String Message;
        public String getMessage() {
            return Message;
        }
        public void setMessage(String message) {
            Message = message;
        }
    }


}