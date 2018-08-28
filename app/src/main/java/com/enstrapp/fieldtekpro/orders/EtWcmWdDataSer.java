package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EtWcmWdDataSer {

    @SerializedName("EtWcmWdDataTagtext")
    @Expose
    private List EtWcmWdDataTagtext = null;

    @SerializedName("EtWcmWdDataUntagtext")
    @Expose
    private List EtWcmWdDataUntagtext = null;

    public List getEtWcmWdDataTagtext() {
        return EtWcmWdDataTagtext;
    }

    public void setEtWcmWdDataTagtext(List etWcmWdDataTagtext) {
        EtWcmWdDataTagtext = etWcmWdDataTagtext;
    }

    public List getEtWcmWdDataUntagtext() {
        return EtWcmWdDataUntagtext;
    }

    public void setEtWcmWdDataUntagtext(List etWcmWdDataUntagtext) {
        EtWcmWdDataUntagtext = etWcmWdDataUntagtext;
    }
}
