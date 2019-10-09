package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Order_RELEASE_REST
{
    @SerializedName("iv_aufnr")
    @Expose
    private String iv_aufnr;
    @SerializedName("is_device")
    @Expose
    private IsDevice isDevice;
    @SerializedName("iv_transmit_type")
    @Expose
    private String IV_TRANSMIT_TYPE;
    @SerializedName("iv_commit")
    @Expose
    private Boolean IV_COMMIT;

    public String getIV_TRANSMIT_TYPE() {
        return IV_TRANSMIT_TYPE;
    }

    public void setIV_TRANSMIT_TYPE(String IV_TRANSMIT_TYPE) {
        this.IV_TRANSMIT_TYPE = IV_TRANSMIT_TYPE;
    }

    public Boolean getIV_COMMIT() {
        return IV_COMMIT;
    }

    public void setIV_COMMIT(Boolean IV_COMMIT) {
        this.IV_COMMIT = IV_COMMIT;
    }

    public String getIv_aufnr() {
        return iv_aufnr;
    }

    public void setIv_aufnr(String iv_aufnr) {
        this.iv_aufnr = iv_aufnr;
    }

    public IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public static class IsDevice {

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
        private String IV_TRANSMIT_TYPE;
        @SerializedName("IV_COMMIT")
        @Expose
        private Boolean IV_COMMIT;

        public String getIV_TRANSMIT_TYPE() {
            return IV_TRANSMIT_TYPE;
        }

        public void setIV_TRANSMIT_TYPE(String IV_TRANSMIT_TYPE) {
            this.IV_TRANSMIT_TYPE = IV_TRANSMIT_TYPE;
        }

        public Boolean getIV_COMMIT() {
            return IV_COMMIT;
        }

        public void setIV_COMMIT(Boolean IV_COMMIT) {
            this.IV_COMMIT = IV_COMMIT;
        }

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

    }


}


