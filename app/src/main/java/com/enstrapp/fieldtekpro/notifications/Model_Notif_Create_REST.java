package com.enstrapp.fieldtekpro.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model_Notif_Create_REST
{
    @SerializedName("iv_user")
    @Expose
    private String IvUser;
    @SerializedName("Muser")
    @Expose
    private String Muser;
    @SerializedName("Deviceid")
    @Expose
    private String Deviceid;
    @SerializedName("Devicesno")
    @Expose
    private String Devicesno;
    @SerializedName("Udid")
    @Expose
    private String Udid;
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
    private IsDevice isDevice;
    @SerializedName("it_notif_header")
    @Expose
    private ArrayList<Model_Notif_Header_REST> ItNotifHeader;
    @SerializedName("it_notif_items")
    @Expose
    private ArrayList<Model_Notif_Causecode_REST> ItNotifItems;
    @SerializedName("it_notif_actvs")
    @Expose
    private ArrayList<Model_Notif_Activity_REST> ItNotifActvs;
    @SerializedName("it_docs")
    @Expose
    private ArrayList<Model_Notif_Attachments_REST> ItDocs;
    @SerializedName("it_notif_longtext")
    @Expose
    private ArrayList<Model_Notif_Longtext_REST> ItNotifLongtext;
    @SerializedName("ItNotifStatus")
    @Expose
    private ArrayList<Model_Notif_Status_REST> ItNotifStatus;
    @SerializedName("it_notif_tasks")
    @Expose
    private ArrayList<Model_Notif_Task_REST> ItNotifTasks;
    @SerializedName("EvMessage")
    @Expose
    private List EvMessage = null;
    @SerializedName("EtNotifHeader")
    @Expose
    private List<Model_Notif_Header_Custominfo> etNotifHeader = null;
    @SerializedName("EtNotifItems")
    @Expose
    private List EtNotifItems = null;
    @SerializedName("EtNotifActvs")
    @Expose
    private List EtNotifActvs = null;
    @SerializedName("EtDocs")
    @Expose
    private List EtDocs = null;
    @SerializedName("EtNotifStatus")
    @Expose
    private List EtNotifStatus = null;
    @SerializedName("EtNotifDup")
    @Expose
    private List EtNotifDup = null;
    @SerializedName("EtNotifLongtext")
    @Expose
    private List EtNotifLongtext = null;
    @SerializedName("EtNotifTasks")
    @Expose
    private List EtNotifTasks = null;

    public IsDevice getIsDevice() {
        return isDevice;
    }

    public void setIsDevice(IsDevice isDevice) {
        this.isDevice = isDevice;
    }

    public List<Model_Notif_Header_Custominfo> getEtNotifHeader() {
        return etNotifHeader;
    }

    public void setEtNotifHeader(List<Model_Notif_Header_Custominfo> etNotifHeader) {
        this.etNotifHeader = etNotifHeader;
    }

    public ArrayList<Model_Notif_Task_REST> getItNotifTasks() {
        return ItNotifTasks;
    }

    public void setItNotifTasks(ArrayList<Model_Notif_Task_REST> itNotifTasks) {
        ItNotifTasks = itNotifTasks;
    }

    public List getEtNotifTasks() {
        return EtNotifTasks;
    }

    public void setEtNotifTasks(List etNotifTasks) {
        EtNotifTasks = etNotifTasks;
    }

    public ArrayList<Model_Notif_Status_REST> getItNotifStatus() {
        return ItNotifStatus;
    }
    public void setItNotifStatus(ArrayList<Model_Notif_Status_REST> itNotifStatus) {
        ItNotifStatus = itNotifStatus;
    }
    public List getEtNotifDup() {
        return EtNotifDup;
    }
    public void setEtNotifDup(List etNotifDup) {
        EtNotifDup = etNotifDup;
    }
    public List getEtNotifLongtext() {
        return EtNotifLongtext;
    }
    public void setEtNotifLongtext(List etNotifLongtext) {
        EtNotifLongtext = etNotifLongtext;
    }
    public ArrayList<Model_Notif_Longtext_REST> getItNotifLongtext() {
        return ItNotifLongtext;
    }
    public void setItNotifLongtext(ArrayList<Model_Notif_Longtext_REST> itNotifLongtext) {
        ItNotifLongtext = itNotifLongtext;
    }
    public List getEtNotifStatus() {
        return EtNotifStatus;
    }
    public void setEtNotifStatus(List etNotifStatus) {
        EtNotifStatus = etNotifStatus;
    }
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
    public ArrayList<Model_Notif_Activity_REST> getItNotifActvs() {
        return ItNotifActvs;
    }
    public void setItNotifActvs(ArrayList<Model_Notif_Activity_REST> itNotifActvs) {
        ItNotifActvs = itNotifActvs;
    }
    public List getEtNotifActvs() {
        return EtNotifActvs;
    }
    public void setEtNotifActvs(List etNotifActvs) {
        EtNotifActvs = etNotifActvs;
    }
    public ArrayList<Model_Notif_Causecode_REST> getItNotifItems() {
        return ItNotifItems;
    }
    public void setItNotifItems(ArrayList<Model_Notif_Causecode_REST> itNotifItems) {
        ItNotifItems = itNotifItems;
    }
    public List getEtNotifItems() {
        return EtNotifItems;
    }
    public void setEtNotifItems(List etNotifItems) {
        EtNotifItems = etNotifItems;
    }


    public List getEvMessage() {
        return EvMessage;
    }

    public void setEvMessage(List evMessage) {
        EvMessage = evMessage;
    }

    public ArrayList<Model_Notif_Header_REST> getItNotifHeader() {
        return ItNotifHeader;
    }

    public void setItNotifHeader(ArrayList<Model_Notif_Header_REST> itNotifHeader) {
        ItNotifHeader = itNotifHeader;
    }

    public String getIvUser() {
        return IvUser;
    }

    public void setIvUser(String ivUser) {
        IvUser = ivUser;
    }

    public String getMuser() {
        return Muser;
    }

    public void setMuser(String muser) {
        Muser = muser;
    }

    public String getDeviceid() {
        return Deviceid;
    }

    public void setDeviceid(String deviceid) {
        Deviceid = deviceid;
    }

    public String getDevicesno() {
        return Devicesno;
    }

    public void setDevicesno(String devicesno) {
        Devicesno = devicesno;
    }

    public String getUdid() {
        return Udid;
    }

    public void setUdid(String udid) {
        Udid = udid;
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


    public class EtNotifHeader
    {
        @SerializedName("EtNotifHeaderFields")
        @Expose
        private List<EtNotifHeaderField> etNotifHeaderFields = null;
        public List<EtNotifHeaderField> getEtNotifHeaderFields() {
            return etNotifHeaderFields;
        }
        public void setEtNotifHeaderFields(List<EtNotifHeaderField> etNotifHeaderFields) {
            this.etNotifHeaderFields = etNotifHeaderFields;
        }
    }

    /*For Parsing EtCustomFields*/
    public class EtNotifHeaderField
    {
        @SerializedName("results")
        @Expose
        private List<EtNotifHeaderFields_Result> results = null;
        public List<EtNotifHeaderFields_Result> getResults() {
            return results;
        }
        public void setResults(List<EtNotifHeaderFields_Result> results) {
            this.results = results;
        }
    }
    public class EtNotifHeaderFields_Result
    {
        @SerializedName("Zdoctype")
        @Expose
        private String zdoctype;
        @SerializedName("ZdoctypeItem")
        @Expose
        private String zdoctypeItem;
        @SerializedName("Tabname")
        @Expose
        private String tabname;
        @SerializedName("Fieldname")
        @Expose
        private String fieldname;
        @SerializedName("Datatype")
        @Expose
        private String datatype;
        @SerializedName("Value")
        @Expose
        private String value;
        @SerializedName("Flabel")
        @Expose
        private String flabel;
        @SerializedName("Sequence")
        @Expose
        private String sequence;
        @SerializedName("Length")
        @Expose
        private String length;

        public String getZdoctype() {
            return zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            this.zdoctype = zdoctype;
        }

        public String getZdoctypeItem() {
            return zdoctypeItem;
        }

        public void setZdoctypeItem(String zdoctypeItem) {
            this.zdoctypeItem = zdoctypeItem;
        }

        public String getTabname() {
            return tabname;
        }

        public void setTabname(String tabname) {
            this.tabname = tabname;
        }

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
        }

        public String getDatatype() {
            return datatype;
        }

        public void setDatatype(String datatype) {
            this.datatype = datatype;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getFlabel() {
            return flabel;
        }

        public void setFlabel(String flabel) {
            this.flabel = flabel;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }
    /*For Parsing EtCustomFields*/



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


