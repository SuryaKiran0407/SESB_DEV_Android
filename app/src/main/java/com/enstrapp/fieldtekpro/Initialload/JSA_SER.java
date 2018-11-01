package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSA_SER {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }


    public class D {
        @SerializedName("results")
        @Expose
        private List<Result> results = null;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }


    public class Result {
        @SerializedName("EtEHSOpstat")
        @Expose
        private EtEHSOpstat EtEHSOpstat;
        @SerializedName("EtEHSHazcat")
        @Expose
        private EtEHSHazcat EtEHSHazcat;
        @SerializedName("EtEHSHazard")
        @Expose
        private EtEHSHazard EtEHSHazard;
        @SerializedName("EtEHSHazimp")
        @Expose
        private EtEHSHazimp EtEHSHazimp;
        @SerializedName("EtEHSHazctrl")
        @Expose
        private EtEHSHazctrl EtEHSHazctrl;
        @SerializedName("EtEHSLocTyp")
        @Expose
        private EtEHSLocTyp EtEHSLocTyp;
        @SerializedName("EtEHSLocRev")
        @Expose
        private EtEHSLocRev EtEHSLocRev;
        @SerializedName("EtEHSJobTyp")
        @Expose
        private EtEHSJobTyp EtEHSJobTyp;
        @SerializedName("EtEHSReason")
        @Expose
        private EtEHSReason EtEHSReason;
        @SerializedName("EtEHSRasrole")
        @Expose
        private EtEHSRasrole EtEHSRasrole;
        @SerializedName("EtEHSRasstep")
        @Expose
        private EtEHSRasstep EtEHSRasstep;

        public EtEHSRasrole getEtEHSRasrole() {
            return EtEHSRasrole;
        }

        public void setEtEHSRasrole(EtEHSRasrole etEHSRasrole) {
            EtEHSRasrole = etEHSRasrole;
        }

        public EtEHSRasstep getEtEHSRasstep() {
            return EtEHSRasstep;
        }

        public void setEtEHSRasstep(EtEHSRasstep etEHSRasstep) {
            EtEHSRasstep = etEHSRasstep;
        }

        public EtEHSReason getEtEHSReason() {
            return EtEHSReason;
        }

        public void setEtEHSReason(EtEHSReason etEHSReason) {
            EtEHSReason = etEHSReason;
        }

        public EtEHSJobTyp getEtEHSJobTyp() {
            return EtEHSJobTyp;
        }

        public void setEtEHSJobTyp(EtEHSJobTyp etEHSJobTyp) {
            EtEHSJobTyp = etEHSJobTyp;
        }

        public EtEHSLocRev getEtEHSLocRev() {
            return EtEHSLocRev;
        }

        public void setEtEHSLocRev(EtEHSLocRev etEHSLocRev) {
            EtEHSLocRev = etEHSLocRev;
        }

        public EtEHSLocTyp getEtEHSLocTyp() {
            return EtEHSLocTyp;
        }

        public void setEtEHSLocTyp(EtEHSLocTyp etEHSLocTyp) {
            EtEHSLocTyp = etEHSLocTyp;
        }

        public JSA_SER.EtEHSHazctrl getEtEHSHazctrl() {
            return EtEHSHazctrl;
        }

        public void setEtEHSHazctrl(JSA_SER.EtEHSHazctrl etEHSHazctrl) {
            EtEHSHazctrl = etEHSHazctrl;
        }

        public JSA_SER.EtEHSHazimp getEtEHSHazimp() {
            return EtEHSHazimp;
        }

        public void setEtEHSHazimp(JSA_SER.EtEHSHazimp etEHSHazimp) {
            EtEHSHazimp = etEHSHazimp;
        }

        public EtEHSHazard getEtEHSHazard() {
            return EtEHSHazard;
        }

        public void setEtEHSHazard(EtEHSHazard etEHSHazard) {
            EtEHSHazard = etEHSHazard;
        }

        public EtEHSHazcat getEtEHSHazcat() {
            return EtEHSHazcat;
        }

        public void setEtEHSHazcat(EtEHSHazcat etEHSHazcat) {
            EtEHSHazcat = etEHSHazcat;
        }

        public JSA_SER.EtEHSOpstat getEtEHSOpstat() {
            return EtEHSOpstat;
        }

        public void setEtEHSOpstat(JSA_SER.EtEHSOpstat etEHSOpstat) {
            EtEHSOpstat = etEHSOpstat;
        }
    }


    /*For EtEHSRasrole*/
    public class EtEHSRasrole {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSRasrole*/


    /*For EtEHSRasstep*/
    public class EtEHSRasstep {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSRasstep*/


    /*For EtEHSReason*/
    public class EtEHSReason {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSReason*/


    /*For EtEHSJobTyp*/
    public class EtEHSJobTyp {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSJobTyp*/


    /*For EtEHSLocRev*/
    public class EtEHSLocRev {
        @SerializedName("results")
        @Expose
        private List<EtEHSLocRev_Result> results = null;

        public List<EtEHSLocRev_Result> getResults() {
            return results;
        }

        public void setResults(List<EtEHSLocRev_Result> results) {
            this.results = results;
        }
    }

    public class EtEHSLocRev_Result {
        @SerializedName("Type")
        @Expose
        private String Type;
        @SerializedName("Status")
        @Expose
        private String Status;
        @SerializedName("FunctLocID")
        @Expose
        private String FunctLocID;
        @SerializedName("EquipmentID")
        @Expose
        private String EquipmentID;
        @SerializedName("PlantID")
        @Expose
        private String PlantID;
        @SerializedName("DbKey")
        @Expose
        private String DbKey;
        @SerializedName("ParentKey")
        @Expose
        private String ParentKey;
        @SerializedName("Text")
        @Expose
        private String Text;
        @SerializedName("LocRootRefKey")
        @Expose
        private String LocRootRefKey;
        @SerializedName("ParRootRefKey")
        @Expose
        private String ParRootRefKey;
        @SerializedName("RefID")
        @Expose
        private String RefID;

        public String getLocRootRefKey() {
            return LocRootRefKey;
        }

        public void setLocRootRefKey(String locRootRefKey) {
            LocRootRefKey = locRootRefKey;
        }

        public String getParRootRefKey() {
            return ParRootRefKey;
        }

        public void setParRootRefKey(String parRootRefKey) {
            ParRootRefKey = parRootRefKey;
        }

        public String getRefID() {
            return RefID;
        }

        public void setRefID(String refID) {
            RefID = refID;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getFunctLocID() {
            return FunctLocID;
        }

        public void setFunctLocID(String functLocID) {
            FunctLocID = functLocID;
        }

        public String getEquipmentID() {
            return EquipmentID;
        }

        public void setEquipmentID(String equipmentID) {
            EquipmentID = equipmentID;
        }

        public String getPlantID() {
            return PlantID;
        }

        public void setPlantID(String plantID) {
            PlantID = plantID;
        }

        public String getDbKey() {
            return DbKey;
        }

        public void setDbKey(String dbKey) {
            DbKey = dbKey;
        }

        public String getParentKey() {
            return ParentKey;
        }

        public void setParentKey(String parentKey) {
            ParentKey = parentKey;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }
    }
    /*For EtEHSLocRev*/


    /*For EtEHSLocTyp*/
    public class EtEHSLocTyp {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSLocTyp*/


    /*For EtEHSHazctrl*/
    public class EtEHSHazctrl {
        @SerializedName("results")
        @Expose
        private List<EtEHSHazctrl_Result> results = null;

        public List<EtEHSHazctrl_Result> getResults() {
            return results;
        }

        public void setResults(List<EtEHSHazctrl_Result> results) {
            this.results = results;
        }
    }

    public class EtEHSHazctrl_Result {
        @SerializedName("HazardCode")
        @Expose
        private String HazardCode;
        @SerializedName("ControlCode")
        @Expose
        private String ControlCode;
        @SerializedName("Type")
        @Expose
        private String Type;
        @SerializedName("Subject")
        @Expose
        private String Subject;
        @SerializedName("Description")
        @Expose
        private String Description;
        @SerializedName("Ctrlid")
        @Expose
        private String Ctrlid;

        public String getCtrlid() {
            return Ctrlid;
        }

        public void setCtrlid(String ctrlid) {
            Ctrlid = ctrlid;
        }

        public String getHazardCode() {
            return HazardCode;
        }

        public void setHazardCode(String hazardCode) {
            HazardCode = hazardCode;
        }

        public String getControlCode() {
            return ControlCode;
        }

        public void setControlCode(String controlCode) {
            ControlCode = controlCode;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }
    }
    /*For EtEHSHazctrl*/


    /*For EtEHSHazimp*/
    public class EtEHSHazimp {
        @SerializedName("results")
        @Expose
        private List<EtEHSHazimp_Result> results = null;

        public List<EtEHSHazimp_Result> getResults() {
            return results;
        }

        public void setResults(List<EtEHSHazimp_Result> results) {
            this.results = results;
        }
    }

    public class EtEHSHazimp_Result {
        @SerializedName("HazardCode")
        @Expose
        private String HazardCode;
        @SerializedName("ImpactCode")
        @Expose
        private String ImpactCode;
        @SerializedName("Description")
        @Expose
        private String Description;
        @SerializedName("Type")
        @Expose
        private String Type;

        public String getHazardCode() {
            return HazardCode;
        }

        public void setHazardCode(String hazardCode) {
            HazardCode = hazardCode;
        }

        public String getImpactCode() {
            return ImpactCode;
        }

        public void setImpactCode(String impactCode) {
            ImpactCode = impactCode;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }
    }
    /*For EtEHSHazimp*/


    /*For EtEHSHazard*/
    public class EtEHSHazard {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSHazard*/


    /*For EtEHSHazcat*/
    public class EtEHSHazcat {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSHazcat*/


    /*For EtEHSOpstat*/
    public class EtEHSOpstat {
        @SerializedName("results")
        @Expose
        private List<Result1> results = null;

        public List<Result1> getResults() {
            return results;
        }

        public void setResults(List<Result1> results) {
            this.results = results;
        }
    }
    /*For EtEHSOpstat*/


    public class Result1 {
        @SerializedName("Code")
        @Expose
        private String Code;
        @SerializedName("Description")
        @Expose
        private String Description;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }
    }


}