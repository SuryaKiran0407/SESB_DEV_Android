package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VHLP_SER
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
        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        public List<Result> getResults()
        {
            return results;
        }
        public void setResults(List<Result> results)
        {
            this.results = results;
        }
    }


    public class Result
    {
        @SerializedName("EtUsers")
        @Expose
        private EtUsers EtUsers;
        @SerializedName("EtTq80")
        @Expose
        private EtTq80 EtTq80;
        @SerializedName("EtNotifEffect")
        @Expose
        private EtNotifEffect EtNotifEffect;
        @SerializedName("EtOrdSyscond")
        @Expose
        private EtOrdSyscond EtOrdSyscond;
        @SerializedName("EtIngrp")
        @Expose
        private EtIngrp EtIngrp;
        @SerializedName("EtConfReason")
        @Expose
        private EtConfReason EtConfReason;
        @SerializedName("EtSteus")
        @Expose
        private EtSteus EtSteus;
        @SerializedName("EtStloc")
        @Expose
        private EtStloc EtStloc;
        @SerializedName("EtPlants")
        @Expose
        private EtPlants EtPlants;
        @SerializedName("EtUnits")
        @Expose
        private EtUnits EtUnits;
        @SerializedName("EtPernr")
        @Expose
        private EtPernr EtPernr;
        @SerializedName("EtWkctrPlant")
        @Expose
        private EtWkctrPlant EtWkctrPlant;
        @SerializedName("EtKostl")
        @Expose
        private EtKostl EtKostl;
        @SerializedName("EtOrdPriority")
        @Expose
        private EtOrdPriority EtOrdPriority;
        @SerializedName("EtOrdTypes")
        @Expose
        private EtOrdTypes EtOrdTypes;
        @SerializedName("EtMovementTypes")
        @Expose
        private EtMovementTypes EtMovementTypes;
        @SerializedName("EtNotifPriority")
        @Expose
        private EtNotifPriority EtNotifPriority;
        @SerializedName("EtNotifTypes")
        @Expose
        private EtNotifTypes EtNotifTypes;
        @SerializedName("EtNotifCodes")
        @Expose
        private EtNotifCodes EtNotifCodes;
        @SerializedName("EtMeasCodes")
        @Expose
        private EtMeasCodes EtMeasCodes;
        @SerializedName("EtWbs")
        @Expose
        private EtWbs EtWbs;
        @SerializedName("EtRevnr")
        @Expose
        private EtRevnr EtRevnr;
        @SerializedName("EtInspCodes")
        @Expose
        private EtInspCodes EtInspCodes;
        @SerializedName("EtUdecCodes")
        @Expose
        private EtUdecCodes EtUdecCodes;
        @SerializedName("EtFields")
        @Expose
        private EtFields EtFields;

        public VHLP_SER.EtFields getEtFields() {
            return EtFields;
        }

        public void setEtFields(VHLP_SER.EtFields etFields) {
            EtFields = etFields;
        }

        public VHLP_SER.EtUdecCodes getEtUdecCodes() {
            return EtUdecCodes;
        }

        public void setEtUdecCodes(VHLP_SER.EtUdecCodes etUdecCodes) {
            EtUdecCodes = etUdecCodes;
        }

        public VHLP_SER.EtInspCodes getEtInspCodes() {
            return EtInspCodes;
        }

        public void setEtInspCodes(VHLP_SER.EtInspCodes etInspCodes) {
            EtInspCodes = etInspCodes;
        }

        public VHLP_SER.EtWbs getEtWbs() {
            return EtWbs;
        }

        public void setEtWbs(VHLP_SER.EtWbs etWbs) {
            EtWbs = etWbs;
        }

        public VHLP_SER.EtRevnr getEtRevnr() {
            return EtRevnr;
        }

        public void setEtRevnr(VHLP_SER.EtRevnr etRevnr) {
            EtRevnr = etRevnr;
        }

        public VHLP_SER.EtMeasCodes getEtMeasCodes() {
            return EtMeasCodes;
        }

        public void setEtMeasCodes(VHLP_SER.EtMeasCodes etMeasCodes) {
            EtMeasCodes = etMeasCodes;
        }

        public VHLP_SER.EtNotifCodes getEtNotifCodes() {
            return EtNotifCodes;
        }
        public void setEtNotifCodes(VHLP_SER.EtNotifCodes etNotifCodes) {
            EtNotifCodes = etNotifCodes;
        }
        public VHLP_SER.EtNotifTypes getEtNotifTypes() {
            return EtNotifTypes;
        }
        public void setEtNotifTypes(VHLP_SER.EtNotifTypes etNotifTypes) {
            EtNotifTypes = etNotifTypes;
        }
        public VHLP_SER.EtNotifPriority getEtNotifPriority() {
            return EtNotifPriority;
        }
        public void setEtNotifPriority(VHLP_SER.EtNotifPriority etNotifPriority) {
            EtNotifPriority = etNotifPriority;
        }
        public VHLP_SER.EtMovementTypes getEtMovementTypes() {
            return EtMovementTypes;
        }
        public void setEtMovementTypes(VHLP_SER.EtMovementTypes etMovementTypes) {
            EtMovementTypes = etMovementTypes;
        }
        public EtOrdTypes getEtOrdTypes() {
            return EtOrdTypes;
        }
        public void setEtOrdTypes(EtOrdTypes etOrdTypes) {
            EtOrdTypes = etOrdTypes;
        }
        public VHLP_SER.EtOrdPriority getEtOrdPriority() {
            return EtOrdPriority;
        }
        public void setEtOrdPriority(VHLP_SER.EtOrdPriority etOrdPriority) {
            EtOrdPriority = etOrdPriority;
        }
        public VHLP_SER.EtKostl getEtKostl() {
            return EtKostl;
        }
        public void setEtKostl(VHLP_SER.EtKostl etKostl) {
            EtKostl = etKostl;
        }
        public VHLP_SER.EtWkctrPlant getEtWkctrPlant() {
            return EtWkctrPlant;
        }
        public void setEtWkctrPlant(VHLP_SER.EtWkctrPlant etWkctrPlant) {
            EtWkctrPlant = etWkctrPlant;
        }
        public VHLP_SER.EtPernr getEtPernr() {
            return EtPernr;
        }
        public void setEtPernr(VHLP_SER.EtPernr etPernr) {
            EtPernr = etPernr;
        }
        public VHLP_SER.EtIngrp getEtIngrp() {
            return EtIngrp;
        }
        public void setEtIngrp(VHLP_SER.EtIngrp etIngrp) {
            EtIngrp = etIngrp;
        }
        public VHLP_SER.EtUsers getEtUsers() {
            return EtUsers;
        }
        public void setEtUsers(VHLP_SER.EtUsers etUsers) {
            EtUsers = etUsers;
        }
        public VHLP_SER.EtTq80 getEtTq80() {
            return EtTq80;
        }
        public void setEtTq80(VHLP_SER.EtTq80 etTq80) {
            EtTq80 = etTq80;
        }
        public VHLP_SER.EtNotifEffect getEtNotifEffect() {
            return EtNotifEffect;
        }
        public void setEtNotifEffect(VHLP_SER.EtNotifEffect etNotifEffect) {
            EtNotifEffect = etNotifEffect;
        }
        public VHLP_SER.EtOrdSyscond getEtOrdSyscond() {
            return EtOrdSyscond;
        }
        public void setEtOrdSyscond(VHLP_SER.EtOrdSyscond etOrdSyscond) {
            EtOrdSyscond = etOrdSyscond;
        }
        public VHLP_SER.EtConfReason getEtConfReason() {
            return EtConfReason;
        }
        public void setEtConfReason(VHLP_SER.EtConfReason etConfReason) {
            EtConfReason = etConfReason;
        }
        public VHLP_SER.EtSteus getEtSteus() {
            return EtSteus;
        }
        public void setEtSteus(VHLP_SER.EtSteus etSteus) {
            EtSteus = etSteus;
        }
        public VHLP_SER.EtStloc getEtStloc() {
            return EtStloc;
        }
        public void setEtStloc(VHLP_SER.EtStloc etStloc) {
            EtStloc = etStloc;
        }
        public VHLP_SER.EtPlants getEtPlants() {
            return EtPlants;
        }
        public void setEtPlants(VHLP_SER.EtPlants etPlants) {
            EtPlants = etPlants;
        }
        public VHLP_SER.EtUnits getEtUnits() {
            return EtUnits;
        }
        public void setEtUnits(VHLP_SER.EtUnits etUnits) {
            EtUnits = etUnits;
        }
    }



    /*EtUdecCodes*/
    public class EtUdecCodes
    {
        @SerializedName("results")
        @Expose
        private List<EtUdecCodes_Result> results = null;
        public List<EtUdecCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtUdecCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtUdecCodes_Result
    {
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Katalogart")
        @Expose
        private String katalogart;
        @SerializedName("Auswahlmge")
        @Expose
        private String auswahlmge;
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getKatalogart() {
            return katalogart;
        }

        public void setKatalogart(String katalogart) {
            this.katalogart = katalogart;
        }

        public String getAuswahlmge() {
            return auswahlmge;
        }

        public void setAuswahlmge(String auswahlmge) {
            this.auswahlmge = auswahlmge;
        }

        public String getCodegruppe() {
            return codegruppe;
        }

        public void setCodegruppe(String codegruppe) {
            this.codegruppe = codegruppe;
        }

        public String getKurztext() {
            return kurztext;
        }

        public void setKurztext(String kurztext) {
            this.kurztext = kurztext;
        }
        @SerializedName("UdecCodes")
        @Expose
        private UdecCodes UdecCodes;
        public VHLP_SER.UdecCodes getUdecCodes() {
            return UdecCodes;
        }
        public void setUdecCodes(VHLP_SER.UdecCodes udecCodes) {
            UdecCodes = udecCodes;
        }
    }
    public class UdecCodes
    {
        @SerializedName("results")
        @Expose
        private List<UdecCodes_Result> results = null;
        public List<UdecCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<UdecCodes_Result> results) {
            this.results = results;
        }
    }
    public class UdecCodes_Result
    {
        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("Kurztext1")
        @Expose
        private String kurztext1;
        @SerializedName("Bewertung")
        @Expose
        private String bewertung;
        @SerializedName("Fehlklasse")
        @Expose
        private String fehlklasse;
        @SerializedName("Qkennzahl")
        @Expose
        private Integer qkennzahl;
        @SerializedName("Folgeakti")
        @Expose
        private String folgeakti;
        @SerializedName("Fehlklassetxt")
        @Expose
        private String fehlklassetxt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getKurztext1() {
            return kurztext1;
        }

        public void setKurztext1(String kurztext1) {
            this.kurztext1 = kurztext1;
        }

        public String getBewertung() {
            return bewertung;
        }

        public void setBewertung(String bewertung) {
            this.bewertung = bewertung;
        }

        public String getFehlklasse() {
            return fehlklasse;
        }

        public void setFehlklasse(String fehlklasse) {
            this.fehlklasse = fehlklasse;
        }

        public Integer getQkennzahl() {
            return qkennzahl;
        }

        public void setQkennzahl(Integer qkennzahl) {
            this.qkennzahl = qkennzahl;
        }

        public String getFolgeakti() {
            return folgeakti;
        }

        public void setFolgeakti(String folgeakti) {
            this.folgeakti = folgeakti;
        }

        public String getFehlklassetxt() {
            return fehlklassetxt;
        }

        public void setFehlklassetxt(String fehlklassetxt) {
            this.fehlklassetxt = fehlklassetxt;
        }
    }
    /*EtUdecCodes*/


    /*EtInspCodes*/
    public class EtInspCodes
    {
        @SerializedName("results")
        @Expose
        private List<EtInspCodes_Result> results = null;
        public List<EtInspCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtInspCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtInspCodes_Result
    {
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Katalogart")
        @Expose
        private String katalogart;
        @SerializedName("Auswahlmge")
        @Expose
        private String auswahlmge;
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("InspCodes")
        @Expose
        private InspCodes inspCodes;
        public String getWerks() {
            return werks;
        }
        public void setWerks(String werks) {
            this.werks = werks;
        }
        public String getKatalogart() {
            return katalogart;
        }
        public void setKatalogart(String katalogart) {
            this.katalogart = katalogart;
        }
        public String getAuswahlmge() {
            return auswahlmge;
        }
        public void setAuswahlmge(String auswahlmge) {
            this.auswahlmge = auswahlmge;
        }
        public String getCodegruppe() {
            return codegruppe;
        }
        public void setCodegruppe(String codegruppe) {
            this.codegruppe = codegruppe;
        }
        public String getKurztext() {
            return kurztext;
        }
        public void setKurztext(String kurztext) {
            this.kurztext = kurztext;
        }
        public InspCodes getInspCodes() {
            return inspCodes;
        }
        public void setInspCodes(InspCodes inspCodes) {
            this.inspCodes = inspCodes;
        }
    }
    public class InspCodes
    {
        @SerializedName("results")
        @Expose
        private List<InspCodes_Codes_Result> results = null;
        public List<InspCodes_Codes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<InspCodes_Codes_Result> results) {
            this.results = results;
        }
    }
    public class InspCodes_Codes_Result {
        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("Kurztext1")
        @Expose
        private String kurztext1;
        @SerializedName("Bewertung")
        @Expose
        private String bewertung;
        @SerializedName("Fehlklasse")
        @Expose
        private String fehlklasse;
        @SerializedName("Qkennzahl")
        @Expose
        private Integer qkennzahl;
        @SerializedName("Folgeakti")
        @Expose
        private String folgeakti;
        @SerializedName("Fehlklassetxt")
        @Expose
        private String fehlklassetxt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getKurztext1() {
            return kurztext1;
        }

        public void setKurztext1(String kurztext1) {
            this.kurztext1 = kurztext1;
        }

        public String getBewertung() {
            return bewertung;
        }

        public void setBewertung(String bewertung) {
            this.bewertung = bewertung;
        }

        public String getFehlklasse() {
            return fehlklasse;
        }

        public void setFehlklasse(String fehlklasse) {
            this.fehlklasse = fehlklasse;
        }

        public Integer getQkennzahl() {
            return qkennzahl;
        }

        public void setQkennzahl(Integer qkennzahl) {
            this.qkennzahl = qkennzahl;
        }

        public String getFolgeakti() {
            return folgeakti;
        }

        public void setFolgeakti(String folgeakti) {
            this.folgeakti = folgeakti;
        }

        public String getFehlklassetxt() {
            return fehlklassetxt;
        }

        public void setFehlklassetxt(String fehlklassetxt) {
            this.fehlklassetxt = fehlklassetxt;
        }
    }
    /*EtInspCodes*/


    /*EtWbs*/
    public class EtWbs
    {
        @SerializedName("results")
        @Expose
        private List<EtWbs_Result> results = null;
        public List<EtWbs_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtWbs_Result> results)
        {
            this.results = results;
        }
    }
    public class EtWbs_Result
    {
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Gsber")
        @Expose
        private String gsber;
        @SerializedName("Posid")
        @Expose
        private String posid;
        @SerializedName("Poski")
        @Expose
        private String poski;
        @SerializedName("Post1")
        @Expose
        private String post1;
        @SerializedName("Pspnr")
        @Expose
        private String pspnr;
        @SerializedName("Pspid")
        @Expose
        private String pspid;

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getGsber() {
            return gsber;
        }

        public void setGsber(String gsber) {
            this.gsber = gsber;
        }

        public String getPosid() {
            return posid;
        }

        public void setPosid(String posid) {
            this.posid = posid;
        }

        public String getPoski() {
            return poski;
        }

        public void setPoski(String poski) {
            this.poski = poski;
        }

        public String getPost1() {
            return post1;
        }

        public void setPost1(String post1) {
            this.post1 = post1;
        }

        public String getPspnr() {
            return pspnr;
        }

        public void setPspnr(String pspnr) {
            this.pspnr = pspnr;
        }

        public String getPspid() {
            return pspid;
        }

        public void setPspid(String pspid) {
            this.pspid = pspid;
        }
    }
    /*EtWbs*/


    /*EtRevnr*/
    public class EtRevnr
    {
        @SerializedName("results")
        @Expose
        private List<EtRevnr_Result> results = null;
        public List<EtRevnr_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtRevnr_Result> results)
        {
            this.results = results;
        }
    }
    public class EtRevnr_Result
    {
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Revnr")
        @Expose
        private String revnr;
        @SerializedName("Revtx")
        @Expose
        private String revtx;

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getRevnr() {
            return revnr;
        }

        public void setRevnr(String revnr) {
            this.revnr = revnr;
        }

        public String getRevtx() {
            return revtx;
        }

        public void setRevtx(String revtx) {
            this.revtx = revtx;
        }
    }
    /*EtRevnr*/



    /*EtMeasCodes*/
    public class EtMeasCodes
    {
        @SerializedName("results")
        @Expose
        private List<EtMeasCodes_Result> results = null;
        public List<EtMeasCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtMeasCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtMeasCodes_Result
    {
        @SerializedName("Codegruppe")
        @Expose
        private String Codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String Kurztext;
        @SerializedName("MCodes")
        @Expose
        private Codes EtMeasCodesCodes;
        public Codes getEtMeasCodesCodes() {
            return EtMeasCodesCodes;
        }
        public void setEtMeasCodesCodes(Codes etMeasCodesCodes) {
            EtMeasCodesCodes = etMeasCodesCodes;
        }
        public String getCodegruppe() {
            return Codegruppe;
        }
        public void setCodegruppe(String codegruppe) {
            Codegruppe = codegruppe;
        }
        public String getKurztext() {
            return Kurztext;
        }
        public void setKurztext(String kurztext) {
            Kurztext = kurztext;
        }
    }
     /*EtMeasCodes*/


    /*EtNotifCodes*/
    public class EtNotifCodes
    {
        @SerializedName("results")
        @Expose
        private List<EtNotifCodes_Result> results = null;
        public List<EtNotifCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtNotifCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtNotifCodes_Result
    {
        @SerializedName("NotifType")
        @Expose
        private String notifType;
        @SerializedName("Rbnr")
        @Expose
        private String rbnr;
        @SerializedName("ItemCodes")
        @Expose
        private ItemCodes itemCodes;
        @SerializedName("CauseCodes")
        @Expose
        private CauseCodes CauseCodes;
        @SerializedName("ObjectCodes")
        @Expose
        private ObjectCodes ObjectCodes;
        @SerializedName("ActCodes")
        @Expose
        private ActCodes ActCodes;
        @SerializedName("TaskCodes")
        @Expose
        private TaskCodes TaskCodes;
        public VHLP_SER.TaskCodes getTaskCodes() {
            return TaskCodes;
        }
        public void setTaskCodes(VHLP_SER.TaskCodes taskCodes) {
            TaskCodes = taskCodes;
        }
        public String getNotifType() {
            return notifType;
        }
        public void setNotifType(String notifType) {
            this.notifType = notifType;
        }
        public String getRbnr() {
            return rbnr;
        }
        public void setRbnr(String rbnr) {
            this.rbnr = rbnr;
        }
        public ItemCodes getItemCodes() {
            return itemCodes;
        }
        public void setItemCodes(ItemCodes itemCodes) {
            this.itemCodes = itemCodes;
        }
        public VHLP_SER.CauseCodes getCauseCodes() {
            return CauseCodes;
        }
        public void setCauseCodes(VHLP_SER.CauseCodes causeCodes) {
            CauseCodes = causeCodes;
        }
        public VHLP_SER.ObjectCodes getObjectCodes() {
            return ObjectCodes;
        }
        public void setObjectCodes(VHLP_SER.ObjectCodes objectCodes) {
            ObjectCodes = objectCodes;
        }
        public VHLP_SER.ActCodes getActCodes() {
            return ActCodes;
        }
        public void setActCodes(VHLP_SER.ActCodes actCodes) {
            ActCodes = actCodes;
        }
    }
    /*EtNotifCodes*/



    /*TaskCodes*/
    public class TaskCodes
    {
        @SerializedName("results")
        @Expose
        private List<TaskCodes_Result> results = null;
        public List<TaskCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<TaskCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class TaskCodes_Result
    {
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("TCall")
        @Expose
        private Codes ACall;
        public String getCodegruppe()
        {
            return codegruppe;
        }
        public void setCodegruppe(String codegruppe)
        {
            this.codegruppe = codegruppe;
        }
        public String getKurztext()
        {
            return kurztext;
        }
        public void setKurztext(String kurztext)
        {
            this.kurztext = kurztext;
        }
        public Codes getACall() {
            return ACall;
        }
        public void setACall(Codes ACall) {
            this.ACall = ACall;
        }
    }
    /*TaskCodes*/


    /*ActCodes*/
    public class ActCodes
    {
        @SerializedName("results")
        @Expose
        private List<ActCodes_Result> results = null;
        public List<ActCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<ActCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class ActCodes_Result
    {
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("ACall")
        @Expose
        private Codes ACall;
        public String getCodegruppe()
        {
            return codegruppe;
        }
        public void setCodegruppe(String codegruppe)
        {
            this.codegruppe = codegruppe;
        }
        public String getKurztext()
        {
            return kurztext;
        }
        public void setKurztext(String kurztext)
        {
            this.kurztext = kurztext;
        }
        public Codes getACall() {
            return ACall;
        }
        public void setACall(Codes ACall) {
            this.ACall = ACall;
        }
    }
    /*ActCodes*/


    /*ObjectCodes*/
    public class ObjectCodes
    {
        @SerializedName("results")
        @Expose
        private List<ObjectCodes_Result> results = null;
        public List<ObjectCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<ObjectCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class ObjectCodes_Result
    {
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("OCall")
        @Expose
        private Codes iCodes;
        public String getCodegruppe()
        {
            return codegruppe;
        }
        public void setCodegruppe(String codegruppe)
        {
            this.codegruppe = codegruppe;
        }
        public String getKurztext()
        {
            return kurztext;
        }
        public void setKurztext(String kurztext)
        {
            this.kurztext = kurztext;
        }
        public Codes getICodes()
        {
            return iCodes;
        }
        public void setICodes(Codes iCodes)
        {
            this.iCodes = iCodes;
        }
    }
    /*ObjectCodes*/


    /*CauseCodes*/
    public class CauseCodes
    {
        @SerializedName("results")
        @Expose
        private List<CauseCodes_Result> results = null;
        public List<CauseCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<CauseCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class CauseCodes_Result
    {
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("CCall")
        @Expose
        private Codes iCodes;
        public String getCodegruppe()
        {
            return codegruppe;
        }
        public void setCodegruppe(String codegruppe)
        {
            this.codegruppe = codegruppe;
        }
        public String getKurztext()
        {
            return kurztext;
        }
        public void setKurztext(String kurztext)
        {
            this.kurztext = kurztext;
        }
        public Codes getICodes()
        {
            return iCodes;
        }
        public void setICodes(Codes iCodes)
        {
            this.iCodes = iCodes;
        }
    }
    /*CauseCodes*/


    /*ItemCodes*/
    public class ItemCodes
    {
        @SerializedName("results")
        @Expose
        private List<ItemCodes_Result> results = null;
        public List<ItemCodes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<ItemCodes_Result> results)
        {
            this.results = results;
        }
    }
    public class ItemCodes_Result
    {
        @SerializedName("Codegruppe")
        @Expose
        private String codegruppe;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("ICodes")
        @Expose
        private Codes iCodes;
        public String getCodegruppe()
        {
            return codegruppe;
        }
        public void setCodegruppe(String codegruppe)
        {
            this.codegruppe = codegruppe;
        }
        public String getKurztext()
        {
            return kurztext;
        }
        public void setKurztext(String kurztext)
        {
            this.kurztext = kurztext;
        }
        public Codes getICodes()
        {
            return iCodes;
        }
        public void setICodes(Codes iCodes)
        {
            this.iCodes = iCodes;
        }
    }
    public class Codes
    {
        @SerializedName("results")
        @Expose
        private List<Codes_Result> results = null;
        public List<Codes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<Codes_Result> results)
        {
            this.results = results;
        }
    }
    public class Codes_Result
    {
        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("Kurztext1")
        @Expose
        private String kurztext1;
        public String getCode()
        {
            return code;
        }
        public void setCode(String code)
        {
            this.code = code;
        }
        public String getKurztext1()
        {
            return kurztext1;
        }
        public void setKurztext1(String kurztext1)
        {
            this.kurztext1 = kurztext1;
        }
    }
     /*ItemCodes*/

    /*EtNotifCodes*/


    /*EtNotifTypes*/
    public class EtNotifTypes
    {
        @SerializedName("results")
        @Expose
        private List<EtNotifTypes_Result> results = null;
        public List<EtNotifTypes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtNotifTypes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtNotifTypes_Result
    {
        @SerializedName("Qmart")
        @Expose
        private String Qmart;
        @SerializedName("Qmartx")
        @Expose
        private String Qmartx;
        public String getQmart() {
            return Qmart;
        }
        public void setQmart(String qmart) {
            Qmart = qmart;
        }
        public String getQmartx() {
            return Qmartx;
        }
        public void setQmartx(String qmartx) {
            Qmartx = qmartx;
        }
    }
    /*EtNotifTypes*/


    /*EtNotifPriority*/
    public class EtNotifPriority
    {
        @SerializedName("results")
        @Expose
        private List<EtNotifPriority_Result> results = null;
        public List<EtNotifPriority_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtNotifPriority_Result> results)
        {
            this.results = results;
        }
    }
    public class EtNotifPriority_Result
    {
        @SerializedName("Priok")
        @Expose
        private String Priok;
        @SerializedName("Priokx")
        @Expose
        private String Priokx;
        public String getPriok() {
            return Priok;
        }
        public void setPriok(String priok) {
            Priok = priok;
        }
        public String getPriokx() {
            return Priokx;
        }
        public void setPriokx(String priokx) {
            Priokx = priokx;
        }
    }
    /*EtNotifPriority*/


    /*EtMovementTypes*/
    public class EtMovementTypes
    {
        @SerializedName("results")
        @Expose
        private List<EtMovementTypes_Result> results = null;
        public List<EtMovementTypes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtMovementTypes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtMovementTypes_Result
    {
        @SerializedName("Bwart")
        @Expose
        private String Bwart;
        @SerializedName("Btext")
        @Expose
        private String Btext;
        public String getBwart() {
            return Bwart;
        }
        public void setBwart(String bwart) {
            Bwart = bwart;
        }
        public String getBtext() {
            return Btext;
        }
        public void setBtext(String btext) {
            Btext = btext;
        }
    }
    /*EtMovementTypes*/


    /*EtOrdTypes*/
    public class EtOrdTypes
    {
        @SerializedName("results")
        @Expose
        private List<EtOrdTypes_Result> results = null;
        public List<EtOrdTypes_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtOrdTypes_Result> results)
        {
            this.results = results;
        }
    }
    public class EtOrdTypes_Result
    {
        @SerializedName("Auart")
        @Expose
        private String Auart;
        @SerializedName("Txt")
        @Expose
        private String Txt;
        public String getAuart() {
            return Auart;
        }
        public void setAuart(String auart) {
            Auart = auart;
        }
        public String getTxt() {
            return Txt;
        }
        public void setTxt(String txt) {
            Txt = txt;
        }
    }
    /*EtOrdTypes*/


    /*EtOrdPriority*/
    public class EtOrdPriority
    {
        @SerializedName("results")
        @Expose
        private List<EtOrdPriority_Result> results = null;
        public List<EtOrdPriority_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtOrdPriority_Result> results)
        {
            this.results = results;
        }
    }
    public class EtOrdPriority_Result
    {
        @SerializedName("Priok")
        @Expose
        private String Priok;
        @SerializedName("Priokx")
        @Expose
        private String Priokx;
        public String getPriok() {
            return Priok;
        }
        public void setPriok(String priok) {
            Priok = priok;
        }
        public String getPriokx() {
            return Priokx;
        }
        public void setPriokx(String priokx) {
            Priokx = priokx;
        }
    }
    /*EtOrdPriority*/


    /*EtKostl*/
    public class EtKostl
    {
        @SerializedName("results")
        @Expose
        private List<EtKostl_Result> results = null;
        public List<EtKostl_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtKostl_Result> results)
        {
            this.results = results;
        }
    }
    public class EtKostl_Result
    {
        @SerializedName("Bukrs")
        @Expose
        private String Bukrs;
        @SerializedName("Kokrs")
        @Expose
        private String Kokrs;
        @SerializedName("Werks")
        @Expose
        private String Werks;
        @SerializedName("Warea")
        @Expose
        private String Warea;
        @SerializedName("Kostl")
        @Expose
        private String Kostl;
        @SerializedName("Ktext")
        @Expose
        private String Ktext;
        public String getBukrs() {
            return Bukrs;
        }
        public void setBukrs(String bukrs) {
            Bukrs = bukrs;
        }
        public String getKokrs() {
            return Kokrs;
        }
        public void setKokrs(String kokrs) {
            Kokrs = kokrs;
        }
        public String getWerks() {
            return Werks;
        }
        public void setWerks(String werks) {
            Werks = werks;
        }
        public String getWarea() {
            return Warea;
        }
        public void setWarea(String warea) {
            Warea = warea;
        }
        public String getKostl() {
            return Kostl;
        }
        public void setKostl(String kostl) {
            Kostl = kostl;
        }
        public String getKtext() {
            return Ktext;
        }
        public void setKtext(String ktext) {
            Ktext = ktext;
        }
    }
    /*EtKostl*/


    /*EtWkctrPlant*/
    public class EtWkctrPlant
    {
        @SerializedName("results")
        @Expose
        private List<EtWkctrPlant_Result> results = null;
        public List<EtWkctrPlant_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtWkctrPlant_Result> results)
        {
            this.results = results;
        }
    }
    public class EtWkctrPlant_Result
    {
        @SerializedName("Bukrs")
        @Expose
        private String Bukrs;
        @SerializedName("Kokrs")
        @Expose
        private String Kokrs;
        @SerializedName("Objid")
        @Expose
        private String Objid;
        @SerializedName("Steus")
        @Expose
        private String Steus;
        @SerializedName("Werks")
        @Expose
        private String Werks;
        @SerializedName("Name1")
        @Expose
        private String Name1;
        @SerializedName("Arbpl")
        @Expose
        private String Arbpl;
        @SerializedName("Ktext")
        @Expose
        private String Ktext;
        public String getBukrs() {
            return Bukrs;
        }
        public void setBukrs(String bukrs) {
            Bukrs = bukrs;
        }
        public String getKokrs() {
            return Kokrs;
        }
        public void setKokrs(String kokrs) {
            Kokrs = kokrs;
        }
        public String getObjid() {
            return Objid;
        }
        public void setObjid(String objid) {
            Objid = objid;
        }
        public String getSteus() {
            return Steus;
        }
        public void setSteus(String steus) {
            Steus = steus;
        }
        public String getWerks() {
            return Werks;
        }
        public void setWerks(String werks) {
            Werks = werks;
        }
        public String getName1() {
            return Name1;
        }
        public void setName1(String name1) {
            Name1 = name1;
        }
        public String getArbpl() {
            return Arbpl;
        }
        public void setArbpl(String arbpl) {
            Arbpl = arbpl;
        }
        public String getKtext() {
            return Ktext;
        }
        public void setKtext(String ktext) {
            Ktext = ktext;
        }
    }
    /*EtWkctrPlant*/


    /*EtPernr*/
    public class EtPernr
    {
        @SerializedName("results")
        @Expose
        private List<EtPernr_Result> results = null;
        public List<EtPernr_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtPernr_Result> results)
        {
            this.results = results;
        }
    }
    public class EtPernr_Result
    {
        @SerializedName("Werks")
        @Expose
        private String Werks;
        @SerializedName("Arbpl")
        @Expose
        private String Arbpl;
        @SerializedName("Objid")
        @Expose
        private String Objid;
        @SerializedName("Lastname")
        @Expose
        private String Lastname;
        @SerializedName("Firstname")
        @Expose
        private String Firstname;
        public String getWerks() {
            return Werks;
        }
        public void setWerks(String werks) {
            Werks = werks;
        }
        public String getArbpl() {
            return Arbpl;
        }
        public void setArbpl(String arbpl) {
            Arbpl = arbpl;
        }
        public String getObjid() {
            return Objid;
        }
        public void setObjid(String objid) {
            Objid = objid;
        }
        public String getLastname() {
            return Lastname;
        }
        public void setLastname(String lastname) {
            Lastname = lastname;
        }
        public String getFirstname() {
            return Firstname;
        }
        public void setFirstname(String firstname) {
            Firstname = firstname;
        }
    }
    /*EtUnits*/


    /*EtUnits*/
    public class EtUnits
    {
        @SerializedName("results")
        @Expose
        private List<EtUnits_Result> results = null;
        public List<EtUnits_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtUnits_Result> results)
        {
            this.results = results;
        }
    }
    public class EtUnits_Result
    {
        @SerializedName("UnitType")
        @Expose
        private String UnitType;
        @SerializedName("Meins")
        @Expose
        private String Meins;
        public String getUnitType() {
            return UnitType;
        }
        public void setUnitType(String unitType) {
            UnitType = unitType;
        }
        public String getMeins() {
            return Meins;
        }
        public void setMeins(String meins) {
            Meins = meins;
        }
    }
    /*EtUnits*/


    /*EtPlants*/
    public class EtPlants
    {
        @SerializedName("results")
        @Expose
        private List<EtPlants_Result> results = null;
        public List<EtPlants_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtPlants_Result> results)
        {
            this.results = results;
        }
    }
    public class EtPlants_Result
    {
        @SerializedName("Werks")
        @Expose
        private String Werks;
        @SerializedName("Name1")
        @Expose
        private String Name1;
        public String getWerks() {
            return Werks;
        }
        public void setWerks(String werks) {
            Werks = werks;
        }
        public String getName1() {
            return Name1;
        }
        public void setName1(String name1) {
            Name1 = name1;
        }
    }
    /*EtPlants*/


    /*EtStloc*/
    public class EtStloc
    {
        @SerializedName("results")
        @Expose
        private List<EtStloc_Result> results = null;
        public List<EtStloc_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtStloc_Result> results)
        {
            this.results = results;
        }
    }
    public class EtStloc_Result
    {
        @SerializedName("Werks")
        @Expose
        private String Werks;
        @SerializedName("Name1")
        @Expose
        private String Name1;
        @SerializedName("Lgort")
        @Expose
        private String Lgort;
        @SerializedName("Lgobe")
        @Expose
        private String Lgobe;
        public String getWerks() {
            return Werks;
        }
        public void setWerks(String werks) {
            Werks = werks;
        }
        public String getName1() {
            return Name1;
        }
        public void setName1(String name1) {
            Name1 = name1;
        }
        public String getLgort() {
            return Lgort;
        }
        public void setLgort(String lgort) {
            Lgort = lgort;
        }
        public String getLgobe() {
            return Lgobe;
        }
        public void setLgobe(String lgobe) {
            Lgobe = lgobe;
        }
    }
    /*EtSteus*/


    /*EtSteus*/
    public class EtSteus
    {
        @SerializedName("results")
        @Expose
        private List<EtSteus_Result> results = null;
        public List<EtSteus_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtSteus_Result> results)
        {
            this.results = results;
        }
    }
    public class EtSteus_Result
    {
        @SerializedName("Steus")
        @Expose
        private String Steus;
        @SerializedName("Txt")
        @Expose
        private String Txt;
        public String getSteus() {
            return Steus;
        }
        public void setSteus(String steus) {
            Steus = steus;
        }
        public String getTxt() {
            return Txt;
        }
        public void setTxt(String txt) {
            Txt = txt;
        }
    }
    /*EtSteus*/


    /*EtConfReason*/
    public class EtConfReason
    {
        @SerializedName("results")
        @Expose
        private List<EtConfReason_Result> results = null;
        public List<EtConfReason_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtConfReason_Result> results)
        {
            this.results = results;
        }
    }
    public class EtConfReason_Result
    {
        @SerializedName("Werks")
        @Expose
        private String Werks;
        @SerializedName("Grund")
        @Expose
        private String Grund;
        @SerializedName("Grdtx")
        @Expose
        private String Grdtx;
        public String getWerks() {
            return Werks;
        }
        public void setWerks(String werks) {
            Werks = werks;
        }
        public String getGrund() {
            return Grund;
        }
        public void setGrund(String grund) {
            Grund = grund;
        }
        public String getGrdtx() {
            return Grdtx;
        }
        public void setGrdtx(String grdtx) {
            Grdtx = grdtx;
        }
    }
    /*EtConfReason*/


    /*EtLstar*/
    public class EtLstar
    {
        @SerializedName("results")
        @Expose
        private List<EtLstar_Result> results = null;
        public List<EtLstar_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtLstar_Result> results)
        {
            this.results = results;
        }
    }
    public class EtLstar_Result
    {
        @SerializedName("Kokrs")
        @Expose
        private String Kokrs;
        @SerializedName("Latyp")
        @Expose
        private String Latyp;
        @SerializedName("Lstar")
        @Expose
        private String Lstar;
        @SerializedName("Ktext")
        @Expose
        private String Ktext;
        @SerializedName("Kostl")
        @Expose
        private String Kostl;
        public String getKokrs() {
            return Kokrs;
        }
        public void setKokrs(String kokrs) {
            Kokrs = kokrs;
        }
        public String getLatyp() {
            return Latyp;
        }
        public void setLatyp(String latyp) {
            Latyp = latyp;
        }
        public String getLstar() {
            return Lstar;
        }
        public void setLstar(String lstar) {
            Lstar = lstar;
        }
        public String getKtext() {
            return Ktext;
        }
        public void setKtext(String ktext) {
            Ktext = ktext;
        }
        public String getKostl() {
            return Kostl;
        }
        public void setKostl(String kostl) {
            Kostl = kostl;
        }
    }
    /*EtLstar*/


    /*EtIngrp*/
    public class EtIngrp
    {
        @SerializedName("results")
        @Expose
        private List<EtIngrp_Result> results = null;
        public List<EtIngrp_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtIngrp_Result> results)
        {
            this.results = results;
        }
    }
    public class EtIngrp_Result
    {
        @SerializedName("Iwerk")
        @Expose
        private String Iwerk;
        @SerializedName("Ingrp")
        @Expose
        private String Ingrp;
        @SerializedName("Innam")
        @Expose
        private String Innam;
        public String getIwerk() {
            return Iwerk;
        }
        public void setIwerk(String iwerk) {
            Iwerk = iwerk;
        }
        public String getIngrp() {
            return Ingrp;
        }
        public void setIngrp(String ingrp) {
            Ingrp = ingrp;
        }
        public String getInnam() {
            return Innam;
        }
        public void setInnam(String innam) {
            Innam = innam;
        }
    }
    /*EtIngrp*/


    /*EtFields*/
    public class EtFields
    {
        @SerializedName("results")
        @Expose
        private List<EtFields_Result> results = null;
        public List<EtFields_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtFields_Result> results)
        {
            this.results = results;
        }
    }
    public class EtFields_Result
    {
        @SerializedName("Fieldname")
        @Expose
        private String Fieldname;
        @SerializedName("ZdoctypeItem")
        @Expose
        private String ZdoctypeItem;
        @SerializedName("Datatype")
        @Expose
        private String Datatype;
        @SerializedName("Tabname")
        @Expose
        private String Tabname;
        @SerializedName("Zdoctype")
        @Expose
        private String Zdoctype;
        @SerializedName("Sequence")
        @Expose
        private String Sequence;
        @SerializedName("Flabel")
        @Expose
        private String Flabel;
        @SerializedName("Spras")
        @Expose
        private String Spras;
        @SerializedName("Length")
        @Expose
        private String Length;
        public String getFieldname() {
            return Fieldname;
        }
        public void setFieldname(String fieldname) {
            Fieldname = fieldname;
        }
        public String getZdoctypeItem() {
            return ZdoctypeItem;
        }
        public void setZdoctypeItem(String zdoctypeItem) {
            ZdoctypeItem = zdoctypeItem;
        }
        public String getDatatype() {
            return Datatype;
        }
        public void setDatatype(String datatype) {
            Datatype = datatype;
        }
        public String getTabname() {
            return Tabname;
        }
        public void setTabname(String tabname) {
            Tabname = tabname;
        }
        public String getZdoctype() {
            return Zdoctype;
        }
        public void setZdoctype(String zdoctype) {
            Zdoctype = zdoctype;
        }
        public String getSequence() {
            return Sequence;
        }
        public void setSequence(String sequence) {
            Sequence = sequence;
        }
        public String getFlabel() {
            return Flabel;
        }
        public void setFlabel(String flabel) {
            Flabel = flabel;
        }
        public String getSpras() {
            return Spras;
        }
        public void setSpras(String spras) {
            Spras = spras;
        }
        public String getLength() {
            return Length;
        }
        public void setLength(String length) {
            Length = length;
        }
    }
    /*EtFields*/


    /*EtOrdSyscond*/
    public class EtOrdSyscond
    {
        @SerializedName("results")
        @Expose
        private List<EtOrdSyscond_Result> results = null;
        public List<EtOrdSyscond_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtOrdSyscond_Result> results)
        {
            this.results = results;
        }
    }
    public class EtOrdSyscond_Result
    {
        @SerializedName("Anlzu")
        @Expose
        private String Anlzu;
        @SerializedName("Anlzux")
        @Expose
        private String Anlzux;
        public String getAnlzu() {
            return Anlzu;
        }
        public void setAnlzu(String anlzu) {
            Anlzu = anlzu;
        }
        public String getAnlzux() {
            return Anlzux;
        }
        public void setAnlzux(String anlzux) {
            Anlzux = anlzux;
        }
    }
    /*EtOrdSyscond*/


    /*EtUsers*/
    public class EtUsers
    {
        @SerializedName("results")
        @Expose
        private List<EtUsers_Result> results = null;
        public List<EtUsers_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtUsers_Result> results)
        {
            this.results = results;
        }
    }
    public class EtUsers_Result
    {
        @SerializedName("Muser")
        @Expose
        private String muser;
        @SerializedName("Fname")
        @Expose
        private String fname;
        @SerializedName("Lname")
        @Expose
        private String lname;
        @SerializedName("Tokenid")
        @Expose
        private String tokenid;
        public String getMuser()
        {
            return muser;
        }
        public void setMuser(String muser)
        {
            this.muser = muser;
        }
        public String getFname()
        {
            return fname;
        }
        public void setFname(String fname)
        {
            this.fname = fname;
        }
        public String getLname()
        {
            return lname;
        }
        public void setLname(String lname)
        {
            this.lname = lname;
        }
        public String getTokenid()
        {
            return tokenid;
        }
        public void setTokenid(String tokenid)
        {
            this.tokenid = tokenid;
        }
    }
    /*EtUsers*/


    /*EtTq80*/
    public class EtTq80
    {
        @SerializedName("results")
        @Expose
        private List<EtTq80_Result> results = null;
        public List<EtTq80_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtTq80_Result> results)
        {
            this.results = results;
        }
    }
    public class EtTq80_Result
    {
        @SerializedName("Qmart")
        @Expose
        private String Qmart;
        @SerializedName("Auart")
        @Expose
        private String Auart;
        public String getQmart() {
            return Qmart;
        }
        public void setQmart(String qmart) {
            Qmart = qmart;
        }
        public String getAuart() {
            return Auart;
        }
        public void setAuart(String auart) {
            Auart = auart;
        }
    }
    /*EtTq80*/


    /*EtNotifEffect*/
    public class EtNotifEffect
    {
        @SerializedName("results")
        @Expose
        private List<EtNotifEffect_Result> results = null;
        public List<EtNotifEffect_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtNotifEffect_Result> results)
        {
            this.results = results;
        }
    }
    public class EtNotifEffect_Result
    {
        @SerializedName("Auswk")
        @Expose
        private String Auswk;
        @SerializedName("Auswkt")
        @Expose
        private String Auswkt;
        public String getAuswk() {
            return Auswk;
        }
        public void setAuswk(String auswk) {
            Auswk = auswk;
        }
        public String getAuswkt() {
            return Auswkt;
        }
        public void setAuswkt(String auswkt) {
            Auswkt = auswkt;
        }
    }
    /*EtNotifEffect*/


}