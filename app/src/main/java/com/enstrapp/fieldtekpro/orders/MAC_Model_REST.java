package com.enstrapp.fieldtekpro.orders;


import com.enstrapp.fieldtekpro.Utilities.ItMatnrPost;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MAC_Model_REST
{
    @SerializedName("is_device")
    @Expose
    private Model_Notif_Create_REST.IsDevice isDevice;
    @SerializedName("it_matnr_post")
    @Expose
    private ArrayList<ItMatnrPost_REST> itMatnrPost;
    @SerializedName("EvAvailable")
    @Expose
    private List evAvailable = null;

    public Model_Notif_Create_REST.IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(Model_Notif_Create_REST.IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public ArrayList<ItMatnrPost_REST> getItMatnrPost() {
        return itMatnrPost;
    }

    public void setItMatnrPost(ArrayList<ItMatnrPost_REST> itMatnrPost) {
        this.itMatnrPost = itMatnrPost;
    }

    public List getEvAvailable() {
        return evAvailable;
    }

    public void setEvAvailable(List evAvailable) {
        this.evAvailable = evAvailable;
    }

}