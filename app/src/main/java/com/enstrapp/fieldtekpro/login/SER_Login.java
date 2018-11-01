
package com.enstrapp.fieldtekpro.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SER_Login {

    /*For Login Success and Response Message*/
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
        @SerializedName("EvFailed")
        @Expose
        private Boolean evFailed;
        @SerializedName("Message")
        @Expose
        private String message;

        public Boolean getEvFailed() {
            return evFailed;
        }

        public void setEvFailed(Boolean evFailed) {
            this.evFailed = evFailed;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    /*For Login Success and Response Message*/


    /*For Login Failed and Error Response Message*/
    @SerializedName("error")
    @Expose
    private Errorr error;

    public Errorr getError() {
        return error;
    }

    public void setError(Errorr error) {
        this.error = error;
    }

    public class Errorr {
        @SerializedName("message")
        @Expose
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }


    public class Message {
        @SerializedName("value")
        @Expose
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    /*For Login Failed and Error Response Message*/


}
