package com.enstrapp.fieldtekpro.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model_Notif_RELEASE_REST
{
    @SerializedName("iv_qmnum")
    @Expose
    private String iv_qmnum;
    @SerializedName("is_device")
    @Expose
    private IsDevice isDevice;

    public String getIv_qmnum() {
        return iv_qmnum;
    }

    public void setIv_qmnum(String iv_qmnum) {
        this.iv_qmnum = iv_qmnum;
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


