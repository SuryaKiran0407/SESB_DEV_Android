package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadSettings_SER
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
        @SerializedName("EsIload")
        @Expose
        private EsIload esIload;
        @SerializedName("EsRefresh")
        @Expose
        private EsRefresh esRefresh;
        public void setEsIload(EsIload esIload)
        {
            this.esIload = esIload;
        }
        public EsIload getEsIload()
        {
            return esIload;
        }
        public EsRefresh getEsRefresh()
        {
            return esRefresh;
        }
        public void setEsRefresh(EsRefresh esRefresh)
        {
            this.esRefresh = esRefresh;
        }
    }


    /*For Parsing EsIload*/
    public class EsIload
    {
        @SerializedName("results")
        @Expose
        private List<Result_> results = null;
        public List<Result_> getResults()
        {
            return results;
        }
        public void setResults(List<Result_> results)
        {
            this.results = results;
        }
    }

    public class Result_
    {
        @SerializedName("Vhlp")
        @Expose
        private String vhlp;
        @SerializedName("Floc")
        @Expose
        private String floc;
        @SerializedName("Equi")
        @Expose
        private String equi;
        @SerializedName("Mat")
        @Expose
        private String mat;
        @SerializedName("Stock")
        @Expose
        private String stock;
        @SerializedName("Ebom")
        @Expose
        private String ebom;
        @SerializedName("Dnot")
        @Expose
        private String dnot;
        @SerializedName("Dord")
        @Expose
        private String dord;
        @SerializedName("Auth")
        @Expose
        private String auth;
        @SerializedName("Sett")
        @Expose
        private String sett;
        @SerializedName("Nfcd")
        @Expose
        private String nfcd;
        public String getVhlp()
        {
            return vhlp;
        }
        public void setVhlp(String vhlp)
        {
            this.vhlp = vhlp;
        }
        public String getFloc()
        {
            return floc;
        }
        public void setFloc(String floc)
        {
            this.floc = floc;
        }
        public String getEqui()
        {
            return equi;
        }
        public void setEqui(String equi)
        {
            this.equi = equi;
        }
        public String getMat()
        {
            return mat;
        }
        public void setMat(String mat)
        {
            this.mat = mat;
        }
        public String getStock()
        {
            return stock;
        }
        public void setStock(String stock)
        {
            this.stock = stock;
        }
        public String getEbom()
        {
            return ebom;
        }
        public void setEbom(String ebom)
        {
            this.ebom = ebom;
        }
        public String getDnot()
        {
            return dnot;
        }
        public void setDnot(String dnot)
        {
            this.dnot = dnot;
        }
        public String getDord()
        {
            return dord;
        }
        public void setDord(String dord)
        {
            this.dord = dord;
        }
        public String getAuth()
        {
            return auth;
        }
        public void setAuth(String auth)
        {
            this.auth = auth;
        }
        public String getSett()
        {
            return sett;
        }
        public void setSett(String sett)
        {
            this.sett = sett;
        }
        public String getNfcd()
        {
            return nfcd;
        }
        public void setNfcd(String nfcd)
        {
            this.nfcd = nfcd;
        }
    }
    /*For Parsing EsIload*/



    /*For Parsing EsRefresh*/
    public class EsRefresh
    {
        @SerializedName("results")
        @Expose
        private List<Result__> results = null;
        public List<Result__> getResults()
        {
            return results;
        }
        public void setResults(List<Result__> results)
        {
            this.results = results;
        }
    }

    public class Result__
    {
        @SerializedName("Vhlp")
        @Expose
        private String vhlp;
        @SerializedName("Floc")
        @Expose
        private String floc;
        @SerializedName("Equi")
        @Expose
        private String equi;
        @SerializedName("Mat")
        @Expose
        private String mat;
        @SerializedName("Stock")
        @Expose
        private String stock;
        @SerializedName("Ebom")
        @Expose
        private String ebom;
        @SerializedName("Dnot")
        @Expose
        private String dnot;
        @SerializedName("Dord")
        @Expose
        private String dord;
        @SerializedName("Auth")
        @Expose
        private String auth;
        @SerializedName("Sett")
        @Expose
        private String sett;
        @SerializedName("Nfcd")
        @Expose
        private String nfcd;
        public String getVhlp()
        {
            return vhlp;
        }
        public void setVhlp(String vhlp)
        {
            this.vhlp = vhlp;
        }
        public String getFloc()
        {
            return floc;
        }
        public void setFloc(String floc)
        {
            this.floc = floc;
        }
        public String getEqui()
        {
            return equi;
        }
        public void setEqui(String equi)
        {
            this.equi = equi;
        }
        public String getMat()
        {
            return mat;
        }
        public void setMat(String mat)
        {
            this.mat = mat;
        }
        public String getStock()
        {
            return stock;
        }
        public void setStock(String stock)
        {
            this.stock = stock;
        }
        public String getEbom()
        {
            return ebom;
        }
        public void setEbom(String ebom)
        {
            this.ebom = ebom;
        }
        public String getDnot()
        {
            return dnot;
        }

        public void setDnot(String dnot)
        {
            this.dnot = dnot;
        }

        public String getDord()
        {
            return dord;
        }
        public void setDord(String dord)
        {
            this.dord = dord;
        }
        public String getAuth()
        {
            return auth;
        }
        public void setAuth(String auth)
        {
            this.auth = auth;
        }
        public String getSett()
        {
            return sett;
        }
        public void setSett(String sett)
        {
            this.sett = sett;
        }
        public String getNfcd()
        {
            return nfcd;
        }
        public void setNfcd(String nfcd)
        {
            this.nfcd = nfcd;
        }
    }
    /*For Parsing EsRefresh*/


}