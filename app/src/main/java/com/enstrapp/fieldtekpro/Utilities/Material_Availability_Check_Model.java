package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.SerializedName;

public class Material_Availability_Check_Model
{
    @SerializedName("d")
    private MAC_Model1 mac_model1;
    public MAC_Model1 getMac_model1() {
        return mac_model1;
    }
    public void setMac_model1(MAC_Model1 mac_model1) {
        this.mac_model1 = mac_model1;
    }
    public Material_Availability_Check_Model(MAC_Model1 mac_model1)
    {
        this.mac_model1 = mac_model1;
    }
}