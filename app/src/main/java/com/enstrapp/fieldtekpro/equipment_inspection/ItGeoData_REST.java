package com.enstrapp.fieldtekpro.equipment_inspection;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItGeoData_REST
{
    @SerializedName("TPLNR")
    @Expose
    private String Tplnr;
    @SerializedName("EQUNR")
    @Expose
    private String EqupNum;
    @SerializedName("LATITUDE")
    @Expose
    private String Latitude;
    @SerializedName("LONGITUDE")
    @Expose
    private String Longitude;
    @SerializedName("ALTITUDE")
    @Expose
    private String Altitude;


    public String getTplnr() {
        return Tplnr;
    }

    public void setTplnr(String tplnr) {
        Tplnr = tplnr;
    }

    public String getEqupNum() {
        return EqupNum;
    }

    public void setEqupNum(String equpNum) {
        EqupNum = equpNum;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }
}