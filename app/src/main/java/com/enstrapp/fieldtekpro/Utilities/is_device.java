package com.enstrapp.fieldtekpro.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class is_device
{

    @SerializedName("MUSER")
    @Expose
    private String mUSER;
    @SerializedName("DEVICEID")
    @Expose
    private String dEVICEID;
    @SerializedName("DEVICESNO")
    @Expose
    private String dEVICESNO;
    @SerializedName("UDID")
    @Expose
    private String uDID;
    @SerializedName("IV_TRANSMIT_TYPE")
    @Expose
    private String iVTRANSMITTYPE;
    @SerializedName("IV_COMMIT")
    @Expose
    private String iVCOMMIT;
    @SerializedName("ERROR")
    @Expose
    private String eRROR;

    public String getMUSER() {
        return mUSER;
    }

    public void setMUSER(String mUSER) {
        this.mUSER = mUSER;
    }

    public String getDEVICEID() {
        return dEVICEID;
    }

    public void setDEVICEID(String dEVICEID) {
        this.dEVICEID = dEVICEID;
    }

    public String getDEVICESNO() {
        return dEVICESNO;
    }

    public void setDEVICESNO(String dEVICESNO) {
        this.dEVICESNO = dEVICESNO;
    }

    public String getUDID() {
        return uDID;
    }

    public void setUDID(String uDID) {
        this.uDID = uDID;
    }

    public String getIVTRANSMITTYPE() {
        return iVTRANSMITTYPE;
    }

    public void setIVTRANSMITTYPE(String iVTRANSMITTYPE) {
        this.iVTRANSMITTYPE = iVTRANSMITTYPE;
    }

    public String getIVCOMMIT() {
        return iVCOMMIT;
    }

    public void setIVCOMMIT(String iVCOMMIT) {
        this.iVCOMMIT = iVCOMMIT;
    }

    public String getERROR() {
        return eRROR;
    }

    public void setERROR(String eRROR) {
        this.eRROR = eRROR;
    }

}


