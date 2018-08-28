package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IvMchkSer {
    @SerializedName("IvAufnr")
    @Expose
    private String ivAufnr;
    @SerializedName("IvTransmitType")
    @Expose
    private String ivTransmitType;
    @SerializedName("IvCommit")
    @Expose
    private Boolean ivCommit;
    @SerializedName("Fcode")
    @Expose
    private String fcode;

    public String getIvAufnr() {
        return ivAufnr;
    }

    public void setIvAufnr(String ivAufnr) {
        this.ivAufnr = ivAufnr;
    }

    public String getIvTransmitType() {
        return ivTransmitType;
    }

    public void setIvTransmitType(String ivTransmitType) {
        this.ivTransmitType = ivTransmitType;
    }

    public Boolean getIvCommit() {
        return ivCommit;
    }

    public void setIvCommit(Boolean ivCommit) {
        this.ivCommit = ivCommit;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }
}
