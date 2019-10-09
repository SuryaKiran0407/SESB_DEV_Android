package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class INSPCHK_SER_REST
{

    @SerializedName("EV_MESSAGE")
    @Expose
    private  String eVMESSAGE;


    public String geteVMESSAGE() {
        return eVMESSAGE;
    }

    public void seteVMESSAGE(String eVMESSAGE) {
        this.eVMESSAGE = eVMESSAGE;
    }
}