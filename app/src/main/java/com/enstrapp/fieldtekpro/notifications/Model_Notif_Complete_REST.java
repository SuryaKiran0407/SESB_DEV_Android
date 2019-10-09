package com.enstrapp.fieldtekpro.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_Notif_Complete_REST {


    @SerializedName("it_notif_complete")
    @Expose
    private List<ItNotifComplete_REST> itNotifComplete = null;
    @SerializedName("is_device")
    @Expose
    private Model_Notif_RELEASE_REST.IsDevice isDevice;
    @SerializedName("iv_transmit_type")
    @Expose
    private String ivTransmitType;
    @SerializedName("iv_commit")
    @Expose
    private String ivCommit;

    public List<ItNotifComplete_REST> getItNotifComplete() {
        return itNotifComplete;
    }

    public void setItNotifComplete(List<ItNotifComplete_REST> itNotifComplete) {
        this.itNotifComplete = itNotifComplete;
    }

    public Model_Notif_RELEASE_REST.IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(Model_Notif_RELEASE_REST.IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public void setIvTransmitType(String ivTransmitType) {
        this.ivTransmitType = ivTransmitType;
    }

    public String getIvCommit() {
        return ivCommit;
    }

    public void setIvCommit(String ivCommit) {
        this.ivCommit = ivCommit;
    }




}
