package com.enstrapp.fieldtekpro.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class OrderAnalysis_SER_REST
{

    @SerializedName("ES_ORD_TOTAL")
    @Expose
    private EsOrdTotal EsOrdTotal;
    @SerializedName("ET_ORD_PLANT_TOTAL")
    @Expose
    private ArrayList<EtOrdPlantTotal> EtOrdPlantTotal;
    @SerializedName("ET_ORD_ARBPL_TOTAL")
    @Expose
    private ArrayList<EtOrdArbplTotal> EtOrdArbplTotal;
    @SerializedName("ET_ORD_TYPE_TOTAL")
    @Expose
    private ArrayList<EtOrdTypeTotal> EtOrdTypeTotal ;
    @SerializedName("ET_ORD_REP")
    @Expose
    private ArrayList<EtOrdRep> EtOrdRep ;

    public ArrayList<OrderAnalysis_SER_REST.EtOrdArbplTotal> getEtOrdArbplTotal() {
        return EtOrdArbplTotal;
    }

    public void setEtOrdArbplTotal(ArrayList<OrderAnalysis_SER_REST.EtOrdArbplTotal> etOrdArbplTotal) {
        EtOrdArbplTotal = etOrdArbplTotal;
    }

    public ArrayList<OrderAnalysis_SER_REST.EtOrdTypeTotal> getEtOrdTypeTotal() {
        return EtOrdTypeTotal;
    }

    public void setEtOrdTypeTotal(ArrayList<OrderAnalysis_SER_REST.EtOrdTypeTotal> etOrdTypeTotal) {
        EtOrdTypeTotal = etOrdTypeTotal;
    }

    public ArrayList<OrderAnalysis_SER_REST.EtOrdRep> getEtOrdRep() {
        return EtOrdRep;
    }

    public void setEtOrdRep(ArrayList<OrderAnalysis_SER_REST.EtOrdRep> etOrdRep) {
        EtOrdRep = etOrdRep;
    }

    public ArrayList<OrderAnalysis_SER_REST.EtOrdPlantTotal> getEtOrdPlantTotal() {
        return EtOrdPlantTotal;
    }

    public void setEtOrdPlantTotal(ArrayList<OrderAnalysis_SER_REST.EtOrdPlantTotal> etOrdPlantTotal) {
        EtOrdPlantTotal = etOrdPlantTotal;
    }

    public OrderAnalysis_SER_REST.EsOrdTotal getEsOrdTotal() {
        return EsOrdTotal;
    }

    public void setEsOrdTotal(OrderAnalysis_SER_REST.EsOrdTotal esOrdTotal) {
        EsOrdTotal = esOrdTotal;
    }



    public class EsOrdTotal {

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

    }



    public class EtOrdPlantTotal {

        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("SWERK")
        @Expose
        private String swerk;
        @SerializedName("NAME")
        @Expose
        private String name;
        @SerializedName("AUART")
        @Expose
        private String auart;
        @SerializedName("AUARTX")
        @Expose
        private String auartx;
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

        public String getAuart() {
            return auart;
        }

        public void setAuart(String auart) {
            this.auart = auart;
        }

        public String getAuartx() {
            return auartx;
        }

        public void setAuartx(String auartx) {
            this.auartx = auartx;
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
    }



    public class EtOrdArbplTotal {

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
        @SerializedName("AUART")
        @Expose
        private String auart;
        @SerializedName("AUARTX")
        @Expose
        private String auartx;
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

        public String getAuart() {
            return auart;
        }

        public void setAuart(String auart) {
            this.auart = auart;
        }

        public String getAuartx() {
            return auartx;
        }

        public void setAuartx(String auartx) {
            this.auartx = auartx;
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

    }




    public class EtOrdTypeTotal {

        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("SWERK")
        @Expose
        private String swerk;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("AUART")
        @Expose
        private String auart;
        @SerializedName("AUARTX")
        @Expose
        private String auartx;
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

        public String getAuart() {
            return auart;
        }

        public void setAuart(String auart) {
            this.auart = auart;
        }

        public String getAuartx() {
            return auartx;
        }

        public void setAuartx(String auartx) {
            this.auartx = auartx;
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
    }



    public class EtOrdRep {
        @SerializedName("PHASE")
        @Expose
        private String phase;
        @SerializedName("SWERK")
        @Expose
        private String swerk;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("AUART")
        @Expose
        private String auart;
        @SerializedName("AUFNR")
        @Expose
        private String aufnr;
        @SerializedName("QMART")
        @Expose
        private String qmart;
        @SerializedName("QMNUM")
        @Expose
        private String qmnum;
        @SerializedName("KTEXT")
        @Expose
        private String ktext;
        @SerializedName("ERNAM")
        @Expose
        private String ernam;
        @SerializedName("EQUNR")
        @Expose
        private String equnr;
        @SerializedName("EQKTX")
        @Expose
        private String eqktx;
        @SerializedName("PRIOK")
        @Expose
        private String priok;
        @SerializedName("GSTRP")
        @Expose
        private String gstrp;
        @SerializedName("GLTRP")
        @Expose
        private String gltrp;
        @SerializedName("TPLNR")
        @Expose
        private String tplnr;
        @SerializedName("IDAT2")
        @Expose
        private String idat2;

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
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

        public String getAuart() {
            return auart;
        }

        public void setAuart(String auart) {
            this.auart = auart;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getQmart() {
            return qmart;
        }

        public void setQmart(String qmart) {
            this.qmart = qmart;
        }

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getKtext() {
            return ktext;
        }

        public void setKtext(String ktext) {
            this.ktext = ktext;
        }

        public String getErnam() {
            return ernam;
        }

        public void setErnam(String ernam) {
            this.ernam = ernam;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getEqktx() {
            return eqktx;
        }

        public void setEqktx(String eqktx) {
            this.eqktx = eqktx;
        }

        public String getPriok() {
            return priok;
        }

        public void setPriok(String priok) {
            this.priok = priok;
        }

        public String getGstrp() {
            return gstrp;
        }

        public void setGstrp(String gstrp) {
            this.gstrp = gstrp;
        }

        public String getGltrp() {
            return gltrp;
        }

        public void setGltrp(String gltrp) {
            this.gltrp = gltrp;
        }

        public String getTplnr() {
            return tplnr;
        }

        public void setTplnr(String tplnr) {
            this.tplnr = tplnr;
        }

        public String getIdat2() {
            return idat2;
        }

        public void setIdat2(String idat2) {
            this.idat2 = idat2;
        }
    }


}




