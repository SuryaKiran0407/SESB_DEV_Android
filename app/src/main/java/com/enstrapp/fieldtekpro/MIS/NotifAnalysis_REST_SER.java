package com.enstrapp.fieldtekpro.MIS;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotifAnalysis_REST_SER
{

    @SerializedName("ES_NOTIF_TOTAL")
    @Expose
    private EsNotifTotal EsNotifTotal;
    @SerializedName("ET_NOTIF_PLANT_TOTAL")
    @Expose
    private ArrayList<EtNotifPlantTotal> EtNotifPlantTotal;
    @SerializedName("ET_NOTIF_ARBPL_TOTAL")
    @Expose
    private ArrayList<EtNotifArbplTotal> EtNotifArbplTotal;
    @SerializedName("ET_NOTIF_TYPE_TOTAL")
    @Expose
    private ArrayList<EtNotifTypeTotal> EtNotifTypeTotal;
    @SerializedName("ET_NOTIF_REP")
    @Expose
    private  ArrayList<EtNotifRep> EtNotifRep;


    public ArrayList<EtNotifTypeTotal> getEtNotifTypeTotal() {
        return EtNotifTypeTotal;
    }

    public void setEtNotifTypeTotal(ArrayList<EtNotifTypeTotal> etNotifTypeTotal) {
        EtNotifTypeTotal = etNotifTypeTotal;
    }

    public ArrayList<EtNotifRep> getEtNotifRep() {
        return EtNotifRep;
    }

    public void setEtNotifRep(ArrayList<EtNotifRep> etNotifRep) {
        EtNotifRep = etNotifRep;
    }

    public EsNotifTotal getEsNotifTotal() {
        return EsNotifTotal;
    }

    public void setEsNotifTotal(EsNotifTotal esNotifTotal) {
        EsNotifTotal = esNotifTotal;
    }

    public ArrayList<NotifAnalysis_REST_SER.EtNotifPlantTotal> getEtNotifPlantTotal() {
        return EtNotifPlantTotal;
    }

    public void setEtNotifPlantTotal(ArrayList<NotifAnalysis_REST_SER.EtNotifPlantTotal> etNotifPlantTotal) {
        EtNotifPlantTotal = etNotifPlantTotal;
    }

    public ArrayList<NotifAnalysis_REST_SER.EtNotifArbplTotal> getEtNotifArbplTotal() {
        return EtNotifArbplTotal;
    }

    public void setEtNotifArbplTotal(ArrayList<NotifAnalysis_REST_SER.EtNotifArbplTotal> etNotifArbplTotal) {
        EtNotifArbplTotal = etNotifArbplTotal;
    }

    public class EsNotifTotal
    {
        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOuts() {
            return outs;
        }

        public void setOuts(String outs) {
            this.outs = outs;
        }

        public String getInpr() {
            return inpr;
        }

        public void setInpr(String inpr) {
            this.inpr = inpr;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getDele() {
            return dele;
        }

        public void setDele(String dele) {
            this.dele = dele;
        }


        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("OUTS")
        @Expose
        private String outs;
        @SerializedName("INPR")
        @Expose
        private String inpr;
        @SerializedName("COMP")
        @Expose
        private String comp;
        @SerializedName("DELE")
        @Expose
        private String dele;
    }



    public class EtNotifPlantTotal {
        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSwerk() {
            return swerk;
        }

        public void setSwerk(String swerk) {
            this.swerk = swerk;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQmart() {
            return qmart;
        }

        public void setQmart(String qmart) {
            this.qmart = qmart;
        }

        public String getQmartx() {
            return qmartx;
        }

        public void setQmartx(String qmartx) {
            this.qmartx = qmartx;
        }

        public String getOuts() {
            return outs;
        }

        public void setOuts(String outs) {
            this.outs = outs;
        }

        public String getInpr() {
            return inpr;
        }

        public void setInpr(String inpr) {
            this.inpr = inpr;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getDele() {
            return dele;
        }

        public void setDele(String dele) {
            this.dele = dele;
        }


        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("SWERK")
        @Expose
        private String swerk;
        @SerializedName("NAME")
        @Expose
        private String name;
        @SerializedName("QMART")
        @Expose
        private String qmart;
        @SerializedName("QMARTX")
        @Expose
        private String qmartx;
        @SerializedName("OUTS")
        @Expose
        private String outs;
        @SerializedName("INPR")
        @Expose
        private String inpr;
        @SerializedName("COMP")
        @Expose
        private String comp;
        @SerializedName("DELE")
        @Expose
        private String dele;
    }




    public class EtNotifArbplTotal {
        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSwerk() {
            return swerk;
        }

        public void setSwerk(String swerk) {
            this.swerk = swerk;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQmart() {
            return qmart;
        }

        public void setQmart(String qmart) {
            this.qmart = qmart;
        }

        public String getQmartx() {
            return qmartx;
        }

        public void setQmartx(String qmartx) {
            this.qmartx = qmartx;
        }

        public String getOuts() {
            return outs;
        }

        public void setOuts(String outs) {
            this.outs = outs;
        }

        public String getInpr() {
            return inpr;
        }

        public void setInpr(String inpr) {
            this.inpr = inpr;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getDele() {
            return dele;
        }

        public void setDele(String dele) {
            this.dele = dele;
        }
        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("SWERK")
        @Expose
        private String swerk;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("NAME")
        @Expose
        private String name;
        @SerializedName("QMART")
        @Expose
        private String qmart;
        @SerializedName("QMARTX")
        @Expose
        private String qmartx;
        @SerializedName("OUTS")
        @Expose
        private String outs;
        @SerializedName("INPR")
        @Expose
        private String inpr;
        @SerializedName("COMP")
        @Expose
        private String comp;
        @SerializedName("DELE")
        @Expose
        private String dele;
    }




    public class EtNotifTypeTotal {
        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSwerk() {
            return swerk;
        }

        public void setSwerk(String swerk) {
            this.swerk = swerk;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }


        public String getQmart() {
            return qmart;
        }

        public void setQmart(String qmart) {
            this.qmart = qmart;
        }

        public String getQmartx() {
            return qmartx;
        }

        public void setQmartx(String qmartx) {
            this.qmartx = qmartx;
        }

        public String getOuts() {
            return outs;
        }

        public void setOuts(String outs) {
            this.outs = outs;
        }

        public String getInpr() {
            return inpr;
        }

        public void setInpr(String inpr) {
            this.inpr = inpr;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getDele() {
            return dele;
        }

        public void setDele(String dele) {
            this.dele = dele;
        }


        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("SWERK")
        @Expose
        private String swerk;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("QMART")
        @Expose
        private String qmart;
        @SerializedName("QMARTX")
        @Expose
        private String qmartx;
        @SerializedName("OUTS")
        @Expose
        private String outs;
        @SerializedName("INPR")
        @Expose
        private String inpr;
        @SerializedName("COMP")
        @Expose
        private String comp;
        @SerializedName("DELE")
        @Expose
        private String dele;
    }



    public class EtNotifRep {
        @SerializedName("PHASE")
        @Expose
        public String Phase;
        @SerializedName("SWERK")
        @Expose
        public String Swerk;
        @SerializedName("ARBPL")
        @Expose
        public String Arbpl;
        @SerializedName("QMART")
        @Expose
        public String Qmart;
        @SerializedName("QMNUM")
        @Expose
        public String Qmnum;
        @SerializedName("QMTXT")
        @Expose
        public String Qmtxt;
        @SerializedName("ERNAM")
        @Expose
        public String Ernam;
        @SerializedName("EQUNR")
        @Expose
        public String Equnr;
        @SerializedName("EQKTX")
        @Expose
        public String Eqktx;
        @SerializedName("PRIOK")
        @Expose
        public String Priok;
        @SerializedName("STRMN")
        @Expose
        public String Strmn;
        @SerializedName("LTRMN")
        @Expose
        public String Ltrmn;
        @SerializedName("AUSWK")
        @Expose
        public String Auswk;
        @SerializedName("TPLNR")
        @Expose
        public String Tplnr;
        @SerializedName("MSAUS")
        @Expose
        public String Msaus;
        @SerializedName("AUSVN")
        @Expose
        public String Ausvn;
        @SerializedName("AUSBS")
        @Expose
        public String Ausbs;

        public String getPhase() {
            return Phase;
        }

        public void setPhase(String phase) {
            Phase = phase;
        }

        public String getSwerk() {
            return Swerk;
        }

        public void setSwerk(String swerk) {
            Swerk = Swerk;
        }

        public String getArbpl() {
            return Arbpl;
        }

        public void setArbpl(String arbpl) {
            Arbpl = arbpl;
        }

        public String getQmart() {
            return Qmart;
        }

        public void setQmart(String qmart) {
            Qmart = qmart;
        }

        public String getQmnum() {
            return Qmnum;
        }

        public void setQmnum(String qmnum) {
            Qmnum = qmnum;
        }

        public String getQmtxt() {
            return Qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            Qmtxt = qmtxt;
        }

        public String getErnam() {
            return Ernam;
        }

        public void setErnam(String ernam) {
            Ernam = ernam;
        }

        public String getEqunr() {
            return Equnr;
        }

        public void setEqunr(String equnr) {
            Equnr = equnr;
        }

        public String getEqktx() {
            return Eqktx;
        }

        public void setEqktx(String eqktx) {
            Eqktx = eqktx;
        }

        public String getPriok() {
            return Priok;
        }

        public void setPriok(String priok) {
            Priok = priok;
        }

        public String getStrmn() {
            return Strmn;
        }

        public void setStrmn(String strmn) {
            Strmn = strmn;
        }

        public String getLtrmn() {
            return Ltrmn;
        }

        public void setLtrmn(String ltrmn) {
            Ltrmn = ltrmn;
        }

        public String getAuswk() {
            return Auswk;
        }

        public void setAuswk(String auswk) {
            Auswk = auswk;
        }

        public String getTplnr() {
            return Tplnr;
        }

        public void setTplnr(String tplnr) {
            Tplnr = tplnr;
        }

        public String getMsaus() {
            return Msaus;
        }

        public void setMsaus(String msaus) {
            Msaus = msaus;
        }

        public String getAusvn() {
            return Ausvn;
        }

        public void setAusvn(String ausvn) {
            Ausvn = ausvn;
        }

        public String getAusbs() {
            return Ausbs;
        }

        public void setAusbs(String ausbs) {
            Ausbs = ausbs;
        }

    }


}