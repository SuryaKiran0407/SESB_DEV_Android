package com.enstrapp.fieldtekpro.equipment_inspection;

import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model_INSP_CHK_REST
{
    @SerializedName("is_device")
    @Expose
    private Model_Notif_Create_REST.IsDevice isDevice;
    @SerializedName("iv_transmit_type")
    @Expose
    private String IvTransmitType;
    @SerializedName("iv_commit")
    @Expose
    private Boolean IvCommit;
    @SerializedName("it_imrg")
    @Expose
    private ArrayList<Model_INSP_Imrg_REST> it_imrg;

    public Model_Notif_Create_REST.IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(Model_Notif_Create_REST.IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public String getIvTransmitType() {
        return IvTransmitType;
    }

    public void setIvTransmitType(String ivTransmitType) {
        IvTransmitType = ivTransmitType;
    }

    public Boolean getIvCommit() {
        return IvCommit;
    }

    public void setIvCommit(Boolean ivCommit) {
        IvCommit = ivCommit;
    }

    public ArrayList<Model_INSP_Imrg_REST> getIt_imrg() {
        return it_imrg;
    }

    public void setIt_imrg(ArrayList<Model_INSP_Imrg_REST> it_imrg) {
        this.it_imrg = it_imrg;
    }
}
