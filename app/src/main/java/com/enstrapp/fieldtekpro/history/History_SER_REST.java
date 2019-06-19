package com.enstrapp.fieldtekpro.history;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class History_SER_REST
{

    @SerializedName("ET_USER_HISTORY")
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


    public class Result
    {
        @SerializedName("ZDOCTYPE_TEXT")
        @Expose
        private String zdoctypeText;
        @SerializedName("ZACTIVITY_TEXT")
        @Expose
        private String zactivityText;
        @SerializedName("ZDATE")
        @Expose
        private String zdate;
        @SerializedName("TIME")
        @Expose
        private String time;
        @SerializedName("ZOBJID")
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