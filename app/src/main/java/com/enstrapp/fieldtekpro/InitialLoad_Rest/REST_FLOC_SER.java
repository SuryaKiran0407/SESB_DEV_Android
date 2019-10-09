package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_FLOC_SER
{

    @SerializedName("ET_FUNC_EQUIP")
    @Expose
    private List<ET_FUNC_EQUIP> ET_FUNC_EQUIP = null;
    @SerializedName("ET_EQUI")
    @Expose
    private List<ET_EQUI> ET_EQUI = null;

    public List<REST_FLOC_SER.ET_FUNC_EQUIP> getET_FUNC_EQUIP() {
        return ET_FUNC_EQUIP;
    }

    public void setET_FUNC_EQUIP(List<REST_FLOC_SER.ET_FUNC_EQUIP> ET_FUNC_EQUIP) {
        this.ET_FUNC_EQUIP = ET_FUNC_EQUIP;
    }

    public List<REST_FLOC_SER.ET_EQUI> getET_EQUI() {
        return ET_EQUI;
    }

    public void setET_EQUI(List<REST_FLOC_SER.ET_EQUI> ET_EQUI) {
        this.ET_EQUI = ET_EQUI;
    }

    public class ET_FUNC_EQUIP
    {
        @SerializedName("TPLNR")
        @Expose
        private String tplnr;
        @SerializedName("PLTXT")
        @Expose
        private String pltxt;
        @SerializedName("WERKS")
        @Expose
        private String werks;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("KOSTL")
        @Expose
        private String kostl;
        @SerializedName("FLTYP")
        @Expose
        private String fltyp;
        @SerializedName("INGRP")
        @Expose
        private String ingrp;
        @SerializedName("TPLMA")
        @Expose
        private String tplma;
        @SerializedName("EQART")
        @Expose
        private String eqart;
        @SerializedName("RBNR")
        @Expose
        private String rbnr;
        @SerializedName("LEVEL")
        @Expose
        private String level;
        @SerializedName("STPLNR")
        @Expose
        private String stplnr;
        @SerializedName("TPLMATXT")
        @Expose
        private String tplmatxt;
        @SerializedName("INACTIVE")
        @Expose
        private String inactive;

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
    }





    public class ET_EQUI
    {
        @SerializedName("TPLNR")
        @Expose
        private String tplnr;
        @SerializedName("EQUNR")
        @Expose
        private String equnr;
        @SerializedName("EQKTX")
        @Expose
        private String eqktx;
        @SerializedName("RBNR")
        @Expose
        private String rbnr;
        @SerializedName("EQTYP")
        @Expose
        private String eqtyp;
        @SerializedName("EQART")
        @Expose
        private String eqart;
        @SerializedName("WERKS")
        @Expose
        private String werks;
        @SerializedName("ARBPL")
        @Expose
        private String arbpl;
        @SerializedName("IWERK")
        @Expose
        private String iwerk;
        @SerializedName("KOSTL")
        @Expose
        private String kostl;
        @SerializedName("INGRP")
        @Expose
        private String ingrp;
        @SerializedName("LEVEL")
        @Expose
        private String level;
        @SerializedName("STLKZ")
        @Expose
        private String stlkz;
        @SerializedName("SEQUI")
        @Expose
        private String sequi;
        @SerializedName("HEQNR")
        @Expose
        private String heqnr;
        @SerializedName("HEQUI")
        @Expose
        private String hequi;
        @SerializedName("HEQKTX")
        @Expose
        private String heqktx;
        @SerializedName("STORT")
        @Expose
        private String stort;
        @SerializedName("BEBER")
        @Expose
        private String beber;
        @SerializedName("ANLNR")
        @Expose
        private String anlnr;
        @SerializedName("ANLUN")
        @Expose
        private String anlun;
        @SerializedName("BUKRS")
        @Expose
        private String bukrs;
        @SerializedName("ANLNRTXT")
        @Expose
        private String anlnrtxt;
        @SerializedName("IVDAT")
        @Expose
        private String ivdat;
        @SerializedName("INVZU")
        @Expose
        private String invzu;
        @SerializedName("HERST")
        @Expose
        private String herst;
        @SerializedName("SERGE")
        @Expose
        private String serge;
        @SerializedName("TYPBZ")
        @Expose
        private String typbz;
        @SerializedName("MAPAR")
        @Expose
        private String mapar;
        @SerializedName("MATNR")
        @Expose
        private String matnr;
        @SerializedName("SERNR")
        @Expose
        private String sernr;
        @SerializedName("SPRAS")
        @Expose
        private String Spras;
        @SerializedName("HERLD")
        @Expose
        private String HERLD;
        @SerializedName("BAUJJ")
        @Expose
        private String BAUJJ;
        @SerializedName("BAUMM")
        @Expose
        private String BAUMM;

        public String getHERLD() {
            return HERLD;
        }

        public void setHERLD(String HERLD) {
            this.HERLD = HERLD;
        }

        public String getBAUJJ() {
            return BAUJJ;
        }

        public void setBAUJJ(String BAUJJ) {
            this.BAUJJ = BAUJJ;
        }

        public String getBAUMM() {
            return BAUMM;
        }

        public void setBAUMM(String BAUMM) {
            this.BAUMM = BAUMM;
        }

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

        public String getSpras() {
            return Spras;
        }

        public void setSpras(String spras) {
            Spras = spras;
        }
    }




}