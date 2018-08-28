package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FLOC_SER
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
        @SerializedName("EtFuncEquip")
        @Expose
        private EtFuncEquip EtFuncEquip;
        @SerializedName("EtEqui")
        @Expose
        private EtEqui EtEqui;
        public EtFuncEquip getEtFuncEquip() {
            return EtFuncEquip;
        }
        public void setEtFuncEquip(EtFuncEquip etFuncEquip) {
            EtFuncEquip = etFuncEquip;
        }
        public EtEqui getEtEqui() {
            return EtEqui;
        }
        public void setEtEqui(EtEqui etEqui) {
            EtEqui = etEqui;
        }
    }



    /*For Parsing EtFuncEquip*/
    public class EtFuncEquip
    {
        @SerializedName("results")
        @Expose
        private List<EtFuncEquip_Result> results = null;
        public List<EtFuncEquip_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtFuncEquip_Result> results)
        {
            this.results = results;
        }
    }
    public class EtFuncEquip_Result
    {
        public String getTplnr() {
            return tplnr;
        }

        public void setTplnr(String tplnr) {
            this.tplnr = tplnr;
        }

        public String getPltxt() {
            return pltxt;
        }

        public void setPltxt(String pltxt) {
            this.pltxt = pltxt;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getKostl() {
            return kostl;
        }

        public void setKostl(String kostl) {
            this.kostl = kostl;
        }

        public String getFltyp() {
            return fltyp;
        }

        public void setFltyp(String fltyp) {
            this.fltyp = fltyp;
        }

        public String getIngrp() {
            return ingrp;
        }

        public void setIngrp(String ingrp) {
            this.ingrp = ingrp;
        }

        public String getTplma() {
            return tplma;
        }

        public void setTplma(String tplma) {
            this.tplma = tplma;
        }

        public String getEqart() {
            return eqart;
        }

        public void setEqart(String eqart) {
            this.eqart = eqart;
        }

        public String getRbnr() {
            return rbnr;
        }

        public void setRbnr(String rbnr) {
            this.rbnr = rbnr;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getStplnr() {
            return stplnr;
        }

        public void setStplnr(String stplnr) {
            this.stplnr = stplnr;
        }

        public String getTplmatxt() {
            return tplmatxt;
        }

        public void setTplmatxt(String tplmatxt) {
            this.tplmatxt = tplmatxt;
        }

        public String getInactive() {
            return inactive;
        }

        public void setInactive(String inactive) {
            this.inactive = inactive;
        }

        @SerializedName("Tplnr")
        @Expose
        private String tplnr;
        @SerializedName("Pltxt")
        @Expose
        private String pltxt;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Arbpl")
        @Expose
        private String arbpl;
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Kostl")
        @Expose
        private String kostl;
        @SerializedName("Fltyp")
        @Expose
        private String fltyp;
        @SerializedName("Ingrp")
        @Expose
        private String ingrp;
        @SerializedName("Tplma")
        @Expose
        private String tplma;
        @SerializedName("Eqart")
        @Expose
        private String eqart;
        @SerializedName("Rbnr")
        @Expose
        private String rbnr;
        @SerializedName("Level")
        @Expose
        private String level;
        @SerializedName("Stplnr")
        @Expose
        private String stplnr;
        @SerializedName("Tplmatxt")
        @Expose
        private String tplmatxt;
        @SerializedName("Inactive")
        @Expose
        private String inactive;
    }
    /*For Parsing EtFuncEquip*/


    /*For Parsing EtEqui*/
    public class EtEqui
    {
        @SerializedName("results")
        @Expose
        private List<EtEqui_Result> results = null;
        public List<EtEqui_Result> getResults()
        {
            return results;
        }
        public void setResults(List<EtEqui_Result> results)
        {
            this.results = results;
        }
    }
    public class EtEqui_Result
    {
        @SerializedName("Tplnr")
        @Expose
        private String tplnr;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
        @SerializedName("Eqktx")
        @Expose
        private String eqktx;
        @SerializedName("Rbnr")
        @Expose
        private String rbnr;
        @SerializedName("Eqtyp")
        @Expose
        private String eqtyp;
        @SerializedName("Eqart")
        @Expose
        private String eqart;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Arbpl")
        @Expose
        private String arbpl;
        @SerializedName("Iwerk")
        @Expose
        private String iwerk;
        @SerializedName("Kostl")
        @Expose
        private String kostl;
        @SerializedName("Ingrp")
        @Expose
        private String ingrp;
        @SerializedName("Level")
        @Expose
        private String level;
        @SerializedName("Stlkz")
        @Expose
        private String stlkz;
        @SerializedName("Sequi")
        @Expose
        private String sequi;
        @SerializedName("Heqnr")
        @Expose
        private String heqnr;
        @SerializedName("Hequi")
        @Expose
        private String hequi;
        @SerializedName("Heqktx")
        @Expose
        private String heqktx;
        @SerializedName("Stort")
        @Expose
        private String stort;
        @SerializedName("Beber")
        @Expose
        private String beber;
        @SerializedName("Anlnr")
        @Expose
        private String anlnr;
        @SerializedName("Anlun")
        @Expose
        private String anlun;
        @SerializedName("Bukrs")
        @Expose
        private String bukrs;
        @SerializedName("Anlnrtxt")
        @Expose
        private String anlnrtxt;
        @SerializedName("Ivdat")
        @Expose
        private String ivdat;
        @SerializedName("Invzu")
        @Expose
        private String invzu;
        @SerializedName("Herst")
        @Expose
        private String herst;
        @SerializedName("Serge")
        @Expose
        private String serge;
        @SerializedName("Typbz")
        @Expose
        private String typbz;
        @SerializedName("Mapar")
        @Expose
        private String mapar;
        @SerializedName("Matnr")
        @Expose
        private String matnr;
        @SerializedName("Sernr")
        @Expose
        private String sernr;

        public String getTplnr() {
            return tplnr;
        }

        public void setTplnr(String tplnr) {
            this.tplnr = tplnr;
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

        public String getRbnr() {
            return rbnr;
        }

        public void setRbnr(String rbnr) {
            this.rbnr = rbnr;
        }

        public String getEqtyp() {
            return eqtyp;
        }

        public void setEqtyp(String eqtyp) {
            this.eqtyp = eqtyp;
        }

        public String getEqart() {
            return eqart;
        }

        public void setEqart(String eqart) {
            this.eqart = eqart;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getArbpl() {
            return arbpl;
        }

        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getKostl() {
            return kostl;
        }

        public void setKostl(String kostl) {
            this.kostl = kostl;
        }

        public String getIngrp() {
            return ingrp;
        }

        public void setIngrp(String ingrp) {
            this.ingrp = ingrp;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getStlkz() {
            return stlkz;
        }

        public void setStlkz(String stlkz) {
            this.stlkz = stlkz;
        }

        public String getSequi() {
            return sequi;
        }

        public void setSequi(String sequi) {
            this.sequi = sequi;
        }

        public String getHeqnr() {
            return heqnr;
        }

        public void setHeqnr(String heqnr) {
            this.heqnr = heqnr;
        }

        public String getHequi() {
            return hequi;
        }

        public void setHequi(String hequi) {
            this.hequi = hequi;
        }

        public String getHeqktx() {
            return heqktx;
        }

        public void setHeqktx(String heqktx) {
            this.heqktx = heqktx;
        }

        public String getStort() {
            return stort;
        }

        public void setStort(String stort) {
            this.stort = stort;
        }

        public String getBeber() {
            return beber;
        }

        public void setBeber(String beber) {
            this.beber = beber;
        }

        public String getAnlnr() {
            return anlnr;
        }

        public void setAnlnr(String anlnr) {
            this.anlnr = anlnr;
        }

        public String getAnlun() {
            return anlun;
        }

        public void setAnlun(String anlun) {
            this.anlun = anlun;
        }

        public String getBukrs() {
            return bukrs;
        }

        public void setBukrs(String bukrs) {
            this.bukrs = bukrs;
        }

        public String getAnlnrtxt() {
            return anlnrtxt;
        }

        public void setAnlnrtxt(String anlnrtxt) {
            this.anlnrtxt = anlnrtxt;
        }

        public String getIvdat() {
            return ivdat;
        }

        public void setIvdat(String ivdat) {
            this.ivdat = ivdat;
        }

        public String getInvzu() {
            return invzu;
        }

        public void setInvzu(String invzu) {
            this.invzu = invzu;
        }

        public String getHerst() {
            return herst;
        }

        public void setHerst(String herst) {
            this.herst = herst;
        }

        public String getSerge() {
            return serge;
        }

        public void setSerge(String serge) {
            this.serge = serge;
        }

        public String getTypbz() {
            return typbz;
        }

        public void setTypbz(String typbz) {
            this.typbz = typbz;
        }

        public String getMapar() {
            return mapar;
        }

        public void setMapar(String mapar) {
            this.mapar = mapar;
        }

        public String getMatnr() {
            return matnr;
        }

        public void setMatnr(String matnr) {
            this.matnr = matnr;
        }

        public String getSernr() {
            return sernr;
        }

        public void setSernr(String sernr) {
            this.sernr = sernr;
        }
    }
    /*For Parsing EtBomItem*/

}