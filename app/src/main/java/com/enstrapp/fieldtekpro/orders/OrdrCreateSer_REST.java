package com.enstrapp.fieldtekpro.orders;

import com.enstrapp.fieldtekpro.notifications.Model_Notif_Attachments_REST;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrdrCreateSer_REST
{
    @SerializedName("ev_message")
    @Expose
    private List EvMessage = null;
    @SerializedName("iv_transmit_type")
    @Expose
    private String IvTransmitType;
    @SerializedName("IvCommit")
    @Expose
    private Boolean IvCommit;
    @SerializedName("Operation")
    @Expose
    private String Operation;
    @SerializedName("is_device")
    @Expose
    private Model_Notif_Create_REST.IsDevice isDevice;
    @SerializedName("it_order_header")
    @Expose
    private ArrayList<OrdrCreateSer_Header_REST> it_order_header;
    @SerializedName("it_oder_longtext")
    @Expose
    private ArrayList<OrdrCreateSer_Longtext_REST> it_oder_longtext;
    @SerializedName("it_order_operations")
    @Expose
    private ArrayList<OrdrCreateSer_Operation_REST> it_order_operations;
    @SerializedName("it_order_components")
    @Expose
    private ArrayList<OrdrCreateSer_Component_REST> it_order_components;
    @SerializedName("it_order_status")
    @Expose
    private ArrayList<OrdrCreateSer_Status_REST> it_order_status;
    @SerializedName("it_confirm_order")
    @Expose
    private ArrayList<ItConfirmOrderColl_Ser_REST> it_confirm_order;
    @SerializedName("it_imrg")
    @Expose
    private ArrayList<ItImrg_Ser_REST> it_imrg;
    @SerializedName("EtDocs")
    @Expose
    private List EtDocs = null;
    @SerializedName("it_docs")
    @Expose
    private ArrayList<Model_Notif_Attachments_REST> ItDocs;


    public ArrayList<Model_Notif_Attachments_REST> getItDocs() {
        return ItDocs;
    }

    public void setItDocs(ArrayList<Model_Notif_Attachments_REST> itDocs) {
        ItDocs = itDocs;
    }

    public List getEtDocs() {
        return EtDocs;
    }

    public void setEtDocs(List etDocs) {
        EtDocs = etDocs;
    }

    public ArrayList<ItImrg_Ser_REST> getIt_imrg() {
        return it_imrg;
    }

    public void setIt_imrg(ArrayList<ItImrg_Ser_REST> it_imrg) {
        this.it_imrg = it_imrg;
    }

    public ArrayList<ItConfirmOrderColl_Ser_REST> getIt_confirm_order() {
        return it_confirm_order;
    }

    public void setIt_confirm_order(ArrayList<ItConfirmOrderColl_Ser_REST> it_confirm_order) {
        this.it_confirm_order = it_confirm_order;
    }

    public ArrayList<OrdrCreateSer_Status_REST> getIt_order_status() {
        return it_order_status;
    }

    public void setIt_order_status(ArrayList<OrdrCreateSer_Status_REST> it_order_status) {
        this.it_order_status = it_order_status;
    }

    public ArrayList<OrdrCreateSer_Component_REST> getIt_order_components() {
        return it_order_components;
    }

    public void setIt_order_components(ArrayList<OrdrCreateSer_Component_REST> it_order_components) {
        this.it_order_components = it_order_components;
    }

    public ArrayList<OrdrCreateSer_Operation_REST> getIt_order_operations() {
        return it_order_operations;
    }

    public void setIt_order_operations(ArrayList<OrdrCreateSer_Operation_REST> it_order_operations) {
        this.it_order_operations = it_order_operations;
    }

    public ArrayList<OrdrCreateSer_Longtext_REST> getIt_oder_longtext() {
        return it_oder_longtext;
    }

    public void setIt_oder_longtext(ArrayList<OrdrCreateSer_Longtext_REST> it_oder_longtext) {
        this.it_oder_longtext = it_oder_longtext;
    }

    public List getEvMessage() {
        return EvMessage;
    }

    public void setEvMessage(List evMessage) {
        EvMessage = evMessage;
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

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public Model_Notif_Create_REST.IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(Model_Notif_Create_REST.IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public ArrayList<OrdrCreateSer_Header_REST> getIt_order_header() {
        return it_order_header;
    }

    public void setIt_order_header(ArrayList<OrdrCreateSer_Header_REST> it_order_header) {
        this.it_order_header = it_order_header;
    }
}


