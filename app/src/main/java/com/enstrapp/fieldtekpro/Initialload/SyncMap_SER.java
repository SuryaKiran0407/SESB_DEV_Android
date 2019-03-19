package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SyncMap_SER {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }


    public class D {
        @SerializedName("results")
        @Expose
        private List<Result> results = null;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }


    public class Result {
        @SerializedName("Sysid")
        @Expose
        private String sysid;
        @SerializedName("Zdoctype")
        @Expose
        private String zdoctype;
        @SerializedName("Zactivity")
        @Expose
        private String zactivity;
        @SerializedName("Endpoint")
        @Expose
        private String endpoint;
        @SerializedName("Zwsrv")
        @Expose
        private String zwsrv;
        @SerializedName("Wsname")
        @Expose
        private String Wsname;

        public String getWsname() {
            return Wsname;
        }

        public void setWsname(String wsname) {
            Wsname = wsname;
        }

        public String getSysid() {
            return sysid;
        }

        public void setSysid(String sysid) {
            this.sysid = sysid;
        }

        public String getZdoctype() {
            return zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            this.zdoctype = zdoctype;
        }

        public String getZactivity() {
            return zactivity;
        }

        public void setZactivity(String zactivity) {
            this.zactivity = zactivity;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getZwsrv() {
            return zwsrv;
        }

        public void setZwsrv(String zwsrv) {
            this.zwsrv = zwsrv;
        }
    }

}