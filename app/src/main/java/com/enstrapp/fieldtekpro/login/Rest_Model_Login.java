package com.enstrapp.fieldtekpro.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rest_Model_Login
{
    @SerializedName("iv_username")
    @Expose
    private String iv_username;
    @SerializedName("iv_password")
    @Expose
    private String iv_password;
    @SerializedName("iv_language")
    @Expose
    private String iv_language;
    @SerializedName("iv_transmit_type")
    @Expose
    private String iv_transmit_type;
    @SerializedName("iv_user")
    @Expose
    private String iv_user;
    @SerializedName("iv_equnr")
    @Expose
    private String iv_equnr;
    @SerializedName("is_device")
    @Expose
    private Rest_Model_Login_Device is_device = null;
    @SerializedName("iv_tokenid")
    @Expose
    private String iv_tokenid;
    @SerializedName("iv_devicetype")
    @Expose
    private String iv_devicetype;
    @SerializedName("iv_appid")
    @Expose
    private String iv_appid;
    @SerializedName("iv_eq_no")
    @Expose
    private String iv_eqno;

    public String getIv_equnr() {
        return iv_equnr;
    }

    public void setIv_equnr(String iv_equnr) {
        this.iv_equnr = iv_equnr;
    }

    public String getIv_eqno() {
        return iv_eqno;
    }

    public void setIv_eqno(String iv_eqno) {
        this.iv_eqno = iv_eqno;
    }

    public String getIv_tokenid() {
        return iv_tokenid;
    }

    public void setIv_tokenid(String iv_tokenid) {
        this.iv_tokenid = iv_tokenid;
    }

    public String getIv_devicetype() {
        return iv_devicetype;
    }

    public void setIv_devicetype(String iv_devicetype) {
        this.iv_devicetype = iv_devicetype;
    }

    public String getIv_appid() {
        return iv_appid;
    }

    public void setIv_appid(String iv_appid) {
        this.iv_appid = iv_appid;
    }

    public String getIv_user() {
        return iv_user;
    }

    public void setIv_user(String iv_user) {
        this.iv_user = iv_user;
    }

    public String getIv_transmit_type() {
        return iv_transmit_type;
    }

    public void setIv_transmit_type(String iv_transmit_type) {
        this.iv_transmit_type = iv_transmit_type;
    }

    public String getIv_username() {
        return iv_username;
    }

    public void setIv_username(String iv_username) {
        this.iv_username = iv_username;
    }

    public String getIv_password() {
        return iv_password;
    }

    public void setIv_password(String iv_password) {
        this.iv_password = iv_password;
    }

    public String getIv_language() {
        return iv_language;
    }

    public void setIv_language(String iv_language) {
        this.iv_language = iv_language;
    }

    public Rest_Model_Login_Device getIs_device() {
        return is_device;
    }

    public void setIs_device(Rest_Model_Login_Device is_device) {
        this.is_device = is_device;
    }
}