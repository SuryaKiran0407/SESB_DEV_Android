package com.enstrapp.fieldtekpro.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rest_Model_Login_Device
{
    @SerializedName("MUSER")
    @Expose
    private String MUSER;
    @SerializedName("DEVICEID")
    @Expose
    private String DEVICEID;
    @SerializedName("DEVICESNO")
    @Expose
    private String DEVICESNO;
    @SerializedName("UDID")
    @Expose
    private String UDID;
    @SerializedName("IV_TRANSMIT_TYPE")
    @Expose
    private String iVTRANSMITTYPE;
    @SerializedName("IV_COMMIT")
    @Expose
    private String iVCOMMIT;
    @SerializedName("ERROR")
    @Expose
    private String eRROR;

    public String getiVTRANSMITTYPE() {
        return iVTRANSMITTYPE;
    }

    public void setiVTRANSMITTYPE(String iVTRANSMITTYPE) {
        this.iVTRANSMITTYPE = iVTRANSMITTYPE;
    }

    public String getiVCOMMIT() {
        return iVCOMMIT;
    }

    public void setiVCOMMIT(String iVCOMMIT) {
        this.iVCOMMIT = iVCOMMIT;
    }

    public String geteRROR() {
        return eRROR;
    }

    public void seteRROR(String eRROR) {
        this.eRROR = eRROR;
    }

    public String getMUSER() {
        return MUSER;
    }

    public void setMUSER(String MUSER) {
        this.MUSER = MUSER;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getDEVICESNO() {
        return DEVICESNO;
    }

    public void setDEVICESNO(String DEVICESNO) {
        this.DEVICESNO = DEVICESNO;
    }

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }
}