package com.enstrapp.fieldtekpro.equipment_inspection;


import com.enstrapp.fieldtekpro.Utilities.MAC_Model1;
import com.google.gson.annotations.SerializedName;

public class Geo_Tag_Model
{
    @SerializedName("d")
    private Geo_Tag_Details_Model geo_tag_details_model;
    public Geo_Tag_Details_Model getMac_model1() {
        return geo_tag_details_model;
    }

    public Geo_Tag_Details_Model getGeo_tag_details_model() {
        return geo_tag_details_model;
    }

    public void setGeo_tag_details_model(Geo_Tag_Details_Model geo_tag_details_model) {
        this.geo_tag_details_model = geo_tag_details_model;
    }
}