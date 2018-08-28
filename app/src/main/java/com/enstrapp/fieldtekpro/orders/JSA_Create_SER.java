package com.enstrapp.fieldtekpro.orders;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSA_Create_SER
{

    @SerializedName("d")
    @Expose
    private D d;
    public D getD()
    {
        return d;
    }
    public void setD(D d)
    {
        this.d = d;
    }


    public class D
    {
        @SerializedName("EtJSAHdr")
        @Expose
        private EtJSAHdr EtJSAHdr;
        @SerializedName("EtJSARisks")
        @Expose
        private EtJSARisks EtJSARisks;
        @SerializedName("EtJSAPer")
        @Expose
        private EtJSAPer EtJSAPer;
        @SerializedName("EtJSAImp")
        @Expose
        private EtJSAImp EtJSAImp;
        @SerializedName("EtJSARskCtrl")
        @Expose
        private EtJSARskCtrl EtJSARskCtrl;
        @SerializedName("EtMessages")
        @Expose
        private EtMessages EtMessages;

        public JSA_Create_SER.EtMessages getEtMessages() {
            return EtMessages;
        }

        public void setEtMessages(JSA_Create_SER.EtMessages etMessages) {
            EtMessages = etMessages;
        }

        public JSA_Create_SER.EtJSARskCtrl getEtJSARskCtrl() {
            return EtJSARskCtrl;
        }
        public void setEtJSARskCtrl(JSA_Create_SER.EtJSARskCtrl etJSARskCtrl) {
            EtJSARskCtrl = etJSARskCtrl;
        }
        public JSA_Create_SER.EtJSAImp getEtJSAImp() {
            return EtJSAImp;
        }
        public void setEtJSAImp(JSA_Create_SER.EtJSAImp etJSAImp) {
            EtJSAImp = etJSAImp;
        }
        public JSA_Create_SER.EtJSAPer getEtJSAPer() {
            return EtJSAPer;
        }
        public void setEtJSAPer(JSA_Create_SER.EtJSAPer etJSAPer) {
            EtJSAPer = etJSAPer;
        }
        public JSA_Create_SER.EtJSARisks getEtJSARisks() {
            return EtJSARisks;
        }
        public void setEtJSARisks(JSA_Create_SER.EtJSARisks etJSARisks) {
            EtJSARisks = etJSARisks;
        }
        public JSA_Create_SER.EtJSAHdr getEtJSAHdr() {
            return EtJSAHdr;
        }
        public void setEtJSAHdr(JSA_Create_SER.EtJSAHdr etJSAHdr) {
            EtJSAHdr = etJSAHdr;
        }
    }



    public class EtMessages
    {
        @SerializedName("results")
        @Expose
        private List<EtMessages_Result> results = null;
        public List<EtMessages_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtMessages_Result> results)
        {
            this.results = results;
        }
    }
    public class EtMessages_Result
    {
        @SerializedName("Message")
        @Expose
        private String Message;
        public String getMessage() {
            return Message;
        }
        public void setMessage(String message) {
            Message = message;
        }
    }


    /*For EtJSARskCtrl*/
    public class EtJSARskCtrl
    {
        @SerializedName("results")
        @Expose
        private List<EtJSARskCtrl_Result> results = null;
        public List<EtJSARskCtrl_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtJSARskCtrl_Result> results)
        {
            this.results = results;
        }
    }
    public class EtJSARskCtrl_Result
    {
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Rasid")
        @Expose
        private String rasid;
        @SerializedName("StepID")
        @Expose
        private String stepID;
        @SerializedName("RiskID")
        @Expose
        private String riskID;
        @SerializedName("StepCode")
        @Expose
        private String stepCode;
        @SerializedName("StepCompletion")
        @Expose
        private String stepCompletion;
        @SerializedName("Ctrlid")
        @Expose
        private String ctrlid;
        @SerializedName("ControlCode")
        @Expose
        private String controlCode;
        @SerializedName("CatalogCode")
        @Expose
        private String catalogCode;
        @SerializedName("ImplStatus")
        @Expose
        private String implStatus;
        @SerializedName("RespID")
        @Expose
        private String respID;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("GoalTargetCode")
        @Expose
        private String goalTargetCode;
        @SerializedName("GoalObjectCode")
        @Expose
        private String goalObjectCode;
        @SerializedName("GoalCtrlEffect")
        @Expose
        private String goalCtrlEffect;
        @SerializedName("EffectDetDate")
        @Expose
        private String effectDetDate;
        @SerializedName("EffectDetCode")
        @Expose
        private String effectDetCode;
        @SerializedName("RefCategory")
        @Expose
        private String refCategory;
        @SerializedName("RefID")
        @Expose
        private String refID;
        @SerializedName("Ctrlidtxt")
        @Expose
        private String ctrlidtxt;
        @SerializedName("CatalogCodetxt")
        @Expose
        private String catalogCodetxt;
        @SerializedName("ImplStatustxt")
        @Expose
        private String implStatustxt;
        @SerializedName("StepCodetxt")
        @Expose
        private String stepCodetxt;
        @SerializedName("StepCompletiontxt")
        @Expose
        private String stepCompletiontxt;
        @SerializedName("Respidtxt")
        @Expose
        private String respidtxt;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getRasid() {
            return rasid;
        }

        public void setRasid(String rasid) {
            this.rasid = rasid;
        }

        public String getStepID() {
            return stepID;
        }

        public void setStepID(String stepID) {
            this.stepID = stepID;
        }

        public String getRiskID() {
            return riskID;
        }

        public void setRiskID(String riskID) {
            this.riskID = riskID;
        }

        public String getStepCode() {
            return stepCode;
        }

        public void setStepCode(String stepCode) {
            this.stepCode = stepCode;
        }

        public String getStepCompletion() {
            return stepCompletion;
        }

        public void setStepCompletion(String stepCompletion) {
            this.stepCompletion = stepCompletion;
        }

        public String getCtrlid() {
            return ctrlid;
        }

        public void setCtrlid(String ctrlid) {
            this.ctrlid = ctrlid;
        }

        public String getControlCode() {
            return controlCode;
        }

        public void setControlCode(String controlCode) {
            this.controlCode = controlCode;
        }

        public String getCatalogCode() {
            return catalogCode;
        }

        public void setCatalogCode(String catalogCode) {
            this.catalogCode = catalogCode;
        }

        public String getImplStatus() {
            return implStatus;
        }

        public void setImplStatus(String implStatus) {
            this.implStatus = implStatus;
        }

        public String getRespID() {
            return respID;
        }

        public void setRespID(String respID) {
            this.respID = respID;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getGoalTargetCode() {
            return goalTargetCode;
        }

        public void setGoalTargetCode(String goalTargetCode) {
            this.goalTargetCode = goalTargetCode;
        }

        public String getGoalObjectCode() {
            return goalObjectCode;
        }

        public void setGoalObjectCode(String goalObjectCode) {
            this.goalObjectCode = goalObjectCode;
        }

        public String getGoalCtrlEffect() {
            return goalCtrlEffect;
        }

        public void setGoalCtrlEffect(String goalCtrlEffect) {
            this.goalCtrlEffect = goalCtrlEffect;
        }

        public String getEffectDetDate() {
            return effectDetDate;
        }

        public void setEffectDetDate(String effectDetDate) {
            this.effectDetDate = effectDetDate;
        }

        public String getEffectDetCode() {
            return effectDetCode;
        }

        public void setEffectDetCode(String effectDetCode) {
            this.effectDetCode = effectDetCode;
        }

        public String getRefCategory() {
            return refCategory;
        }

        public void setRefCategory(String refCategory) {
            this.refCategory = refCategory;
        }

        public String getRefID() {
            return refID;
        }

        public void setRefID(String refID) {
            this.refID = refID;
        }

        public String getCtrlidtxt() {
            return ctrlidtxt;
        }

        public void setCtrlidtxt(String ctrlidtxt) {
            this.ctrlidtxt = ctrlidtxt;
        }

        public String getCatalogCodetxt() {
            return catalogCodetxt;
        }

        public void setCatalogCodetxt(String catalogCodetxt) {
            this.catalogCodetxt = catalogCodetxt;
        }

        public String getImplStatustxt() {
            return implStatustxt;
        }

        public void setImplStatustxt(String implStatustxt) {
            this.implStatustxt = implStatustxt;
        }

        public String getStepCodetxt() {
            return stepCodetxt;
        }

        public void setStepCodetxt(String stepCodetxt) {
            this.stepCodetxt = stepCodetxt;
        }

        public String getStepCompletiontxt() {
            return stepCompletiontxt;
        }

        public void setStepCompletiontxt(String stepCompletiontxt) {
            this.stepCompletiontxt = stepCompletiontxt;
        }

        public String getRespidtxt() {
            return respidtxt;
        }

        public void setRespidtxt(String respidtxt) {
            this.respidtxt = respidtxt;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For EtJSARskCtrl*/


    /*For EtJSAImp*/
    public class EtJSAImp
    {
        @SerializedName("results")
        @Expose
        private List<EtJSAImp_Result> results = null;
        public List<EtJSAImp_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtJSAImp_Result> results)
        {
            this.results = results;
        }
    }
    public class EtJSAImp_Result
    {
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Rasid")
        @Expose
        private String rasid;
        @SerializedName("StepID")
        @Expose
        private String stepID;
        @SerializedName("RiskID")
        @Expose
        private String riskID;
        @SerializedName("Impact")
        @Expose
        private String impact;
        @SerializedName("Impacttxt")
        @Expose
        private String impacttxt;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getRasid() {
            return rasid;
        }

        public void setRasid(String rasid) {
            this.rasid = rasid;
        }

        public String getStepID() {
            return stepID;
        }

        public void setStepID(String stepID) {
            this.stepID = stepID;
        }

        public String getRiskID() {
            return riskID;
        }

        public void setRiskID(String riskID) {
            this.riskID = riskID;
        }

        public String getImpact() {
            return impact;
        }

        public void setImpact(String impact) {
            this.impact = impact;
        }

        public String getImpacttxt() {
            return impacttxt;
        }

        public void setImpacttxt(String impacttxt) {
            this.impacttxt = impacttxt;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For EtJSAImp*/


    /*For EtJSAPer*/
    public class EtJSAPer
    {
        @SerializedName("results")
        @Expose
        private List<EtJSAPer_Result> results = null;
        public List<EtJSAPer_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtJSAPer_Result> results)
        {
            this.results = results;
        }
    }
    public class EtJSAPer_Result
    {
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Rasid")
        @Expose
        private String rasid;
        @SerializedName("PersonID")
        @Expose
        private String personID;
        @SerializedName("Role")
        @Expose
        private String role;
        @SerializedName("Roletxt")
        @Expose
        private String roletxt;
        @SerializedName("Persontxt")
        @Expose
        private String persontxt;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getRasid() {
            return rasid;
        }

        public void setRasid(String rasid) {
            this.rasid = rasid;
        }

        public String getPersonID() {
            return personID;
        }

        public void setPersonID(String personID) {
            this.personID = personID;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getRoletxt() {
            return roletxt;
        }

        public void setRoletxt(String roletxt) {
            this.roletxt = roletxt;
        }

        public String getPersontxt() {
            return persontxt;
        }

        public void setPersontxt(String persontxt) {
            this.persontxt = persontxt;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For EtJSAPer*/


    /*For EtJSARisks*/
    public class EtJSARisks
    {
        @SerializedName("results")
        @Expose
        private List<EtJSARisks_Result> results = null;
        public List<EtJSARisks_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtJSARisks_Result> results)
        {
            this.results = results;
        }
    }
    public class EtJSARisks_Result
    {
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Rasid")
        @Expose
        private String rasid;
        @SerializedName("StepID")
        @Expose
        private String stepID;
        @SerializedName("StepSeq")
        @Expose
        private Integer stepSeq;
        @SerializedName("RiskID")
        @Expose
        private String riskID;
        @SerializedName("StepPers")
        @Expose
        private String stepPers;
        @SerializedName("Hazard")
        @Expose
        private String hazard;
        @SerializedName("RiskLevel")
        @Expose
        private String riskLevel;
        @SerializedName("RiskType")
        @Expose
        private String riskType;
        @SerializedName("Evaluation")
        @Expose
        private String evaluation;
        @SerializedName("Likelihood")
        @Expose
        private String likelihood;
        @SerializedName("Severity")
        @Expose
        private String severity;
        @SerializedName("HazCat")
        @Expose
        private String hazCat;
        @SerializedName("Hazardtxt")
        @Expose
        private String hazardtxt;
        @SerializedName("HazCattxt")
        @Expose
        private String hazCattxt;
        @SerializedName("StepTxt")
        @Expose
        private String stepTxt;
        @SerializedName("RiskLeveltxt")
        @Expose
        private String riskLeveltxt;
        @SerializedName("RiskTypetxt")
        @Expose
        private String riskTypetxt;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getRasid() {
            return rasid;
        }

        public void setRasid(String rasid) {
            this.rasid = rasid;
        }

        public String getStepID() {
            return stepID;
        }

        public void setStepID(String stepID) {
            this.stepID = stepID;
        }

        public Integer getStepSeq() {
            return stepSeq;
        }

        public void setStepSeq(Integer stepSeq) {
            this.stepSeq = stepSeq;
        }

        public String getRiskID() {
            return riskID;
        }

        public void setRiskID(String riskID) {
            this.riskID = riskID;
        }

        public String getStepPers() {
            return stepPers;
        }

        public void setStepPers(String stepPers) {
            this.stepPers = stepPers;
        }

        public String getHazard() {
            return hazard;
        }

        public void setHazard(String hazard) {
            this.hazard = hazard;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        public String getRiskType() {
            return riskType;
        }

        public void setRiskType(String riskType) {
            this.riskType = riskType;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getLikelihood() {
            return likelihood;
        }

        public void setLikelihood(String likelihood) {
            this.likelihood = likelihood;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getHazCat() {
            return hazCat;
        }

        public void setHazCat(String hazCat) {
            this.hazCat = hazCat;
        }

        public String getHazardtxt() {
            return hazardtxt;
        }

        public void setHazardtxt(String hazardtxt) {
            this.hazardtxt = hazardtxt;
        }

        public String getHazCattxt() {
            return hazCattxt;
        }

        public void setHazCattxt(String hazCattxt) {
            this.hazCattxt = hazCattxt;
        }

        public String getStepTxt() {
            return stepTxt;
        }

        public void setStepTxt(String stepTxt) {
            this.stepTxt = stepTxt;
        }

        public String getRiskLeveltxt() {
            return riskLeveltxt;
        }

        public void setRiskLeveltxt(String riskLeveltxt) {
            this.riskLeveltxt = riskLeveltxt;
        }

        public String getRiskTypetxt() {
            return riskTypetxt;
        }

        public void setRiskTypetxt(String riskTypetxt) {
            this.riskTypetxt = riskTypetxt;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For EtJSARisks*/


    /*For EtJSAHdr*/
    public class EtJSAHdr
    {
        @SerializedName("results")
        @Expose
        private List<EtJSAHdr_Result> results = null;
        public List<EtJSAHdr_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtJSAHdr_Result> results)
        {
            this.results = results;
        }
    }
    public class EtJSAHdr_Result
    {
        @SerializedName("DbKey")
        @Expose
        private String dbKey;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Rasid")
        @Expose
        private String rasid;
        @SerializedName("Rasstatus")
        @Expose
        private String rasstatus;
        @SerializedName("Rastype")
        @Expose
        private String rastype;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Job")
        @Expose
        private String job;
        @SerializedName("Opstatus")
        @Expose
        private String opstatus;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("Statustxt")
        @Expose
        private String statustxt;
        @SerializedName("Rastypetxt")
        @Expose
        private String rastypetxt;
        @SerializedName("Opstatustxt")
        @Expose
        private String opstatustxt;
        @SerializedName("Locationtxt")
        @Expose
        private String locationtxt;
        @SerializedName("Jobtxt")
        @Expose
        private String jobtxt;
        @SerializedName("Action")
        @Expose
        private String action;

        public String getDbKey() {
            return dbKey;
        }

        public void setDbKey(String dbKey) {
            this.dbKey = dbKey;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getRasid() {
            return rasid;
        }

        public void setRasid(String rasid) {
            this.rasid = rasid;
        }

        public String getRasstatus() {
            return rasstatus;
        }

        public void setRasstatus(String rasstatus) {
            this.rasstatus = rasstatus;
        }

        public String getRastype() {
            return rastype;
        }

        public void setRastype(String rastype) {
            this.rastype = rastype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getOpstatus() {
            return opstatus;
        }

        public void setOpstatus(String opstatus) {
            this.opstatus = opstatus;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getStatustxt() {
            return statustxt;
        }

        public void setStatustxt(String statustxt) {
            this.statustxt = statustxt;
        }

        public String getRastypetxt() {
            return rastypetxt;
        }

        public void setRastypetxt(String rastypetxt) {
            this.rastypetxt = rastypetxt;
        }

        public String getOpstatustxt() {
            return opstatustxt;
        }

        public void setOpstatustxt(String opstatustxt) {
            this.opstatustxt = opstatustxt;
        }

        public String getLocationtxt() {
            return locationtxt;
        }

        public void setLocationtxt(String locationtxt) {
            this.locationtxt = locationtxt;
        }

        public String getJobtxt() {
            return jobtxt;
        }

        public void setJobtxt(String jobtxt) {
            this.jobtxt = jobtxt;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For EtJSAHdr*/


 }