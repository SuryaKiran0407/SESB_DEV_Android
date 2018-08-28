package com.enstrapp.fieldtekpro.history;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class History_SER
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
        @SerializedName("ZdoctypeText")
        @Expose
        private String zdoctypeText;
        @SerializedName("ZactivityText")
        @Expose
        private String zactivityText;
        @SerializedName("Zdate")
        @Expose
        private String zdate;
        @SerializedName("Time")
        @Expose
        private String time;
        @SerializedName("Zobjid")
        @Expose
        private String zobjid;

        public String getZdoctypeText() {
            return zdoctypeText;
        }

        public void setZdoctypeText(String zdoctypeText) {
            this.zdoctypeText = zdoctypeText;
        }

        public String getZactivityText() {
            return zactivityText;
        }

        public void setZactivityText(String zactivityText) {
            this.zactivityText = zactivityText;
        }

        public String getZdate() {
            return zdate;
        }

        public void setZdate(String zdate) {
            this.zdate = zdate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getZobjid() {
            return zobjid;
        }

        public void setZobjid(String zobjid) {
            this.zobjid = zobjid;
        }
    }


}