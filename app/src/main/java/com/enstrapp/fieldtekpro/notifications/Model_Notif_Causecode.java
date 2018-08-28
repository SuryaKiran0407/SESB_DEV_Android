package com.enstrapp.fieldtekpro.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Causecode implements Parcelable
{
    @SerializedName("Qmnum")
    @Expose
    private String qmnum;
    @SerializedName("ItemKey")
    @Expose
    private String itemKey;
    @SerializedName("ItempartGrp")
    @Expose
    private String itempartGrp;
    @SerializedName("Partgrptext")
    @Expose
    private String partgrptext;
    @SerializedName("ItemdefectGrp")
    @Expose
    private String itemdefectGrp;
    @SerializedName("ItempartCod")
    @Expose
    private String itempartCod;
    @SerializedName("Partcodetext")
    @Expose
    private String partcodetext;
    @SerializedName("Defectgrptext")
    @Expose
    private String defectgrptext;
    @SerializedName("ItemdefectCod")
    @Expose
    private String itemdefectCod;
    @SerializedName("Defectcodetext")
    @Expose
    private String defectcodetext;
    @SerializedName("ItemdefectShtxt")
    @Expose
    private String itemdefectShtxt;
    @SerializedName("CauseKey")
    @Expose
    private String causeKey;
    @SerializedName("CauseGrp")
    @Expose
    private String causeGrp;
    @SerializedName("Causegrptext")
    @Expose
    private String causegrptext;
    @SerializedName("CauseCod")
    @Expose
    private String causeCod;
    @SerializedName("Causecodetext")
    @Expose
    private String causecodetext;
    @SerializedName("CauseShtxt")
    @Expose
    private String causeShtxt;
    @SerializedName("Usr01")
    @Expose
    private String usr01;
    @SerializedName("Usr02")
    @Expose
    private String usr02;
    @SerializedName("Usr03")
    @Expose
    private String usr03;
    @SerializedName("Usr04")
    @Expose
    private String usr04;
    @SerializedName("Usr05")
    @Expose
    private String usr05;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("ItNotifItemsFields")
    @Expose
    private List<Model_CustomInfo> itNotifItemsFields = null;


    public List<Model_CustomInfo> getItNotifItemsFields() {
        return itNotifItemsFields;
    }

    public void setItNotifItemsFields(List<Model_CustomInfo> itNotifItemsFields) {
        this.itNotifItemsFields = itNotifItemsFields;
    }

    public String getQmnum() {
        return qmnum;
    }

    public void setQmnum(String qmnum) {
        this.qmnum = qmnum;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItempartGrp() {
        return itempartGrp;
    }

    public void setItempartGrp(String itempartGrp) {
        this.itempartGrp = itempartGrp;
    }

    public String getPartgrptext() {
        return partgrptext;
    }

    public void setPartgrptext(String partgrptext) {
        this.partgrptext = partgrptext;
    }

    public String getItemdefectGrp() {
        return itemdefectGrp;
    }

    public void setItemdefectGrp(String itemdefectGrp) {
        this.itemdefectGrp = itemdefectGrp;
    }

    public String getItempartCod() {
        return itempartCod;
    }

    public void setItempartCod(String itempartCod) {
        this.itempartCod = itempartCod;
    }

    public String getPartcodetext() {
        return partcodetext;
    }

    public void setPartcodetext(String partcodetext) {
        this.partcodetext = partcodetext;
    }

    public String getDefectgrptext() {
        return defectgrptext;
    }

    public void setDefectgrptext(String defectgrptext) {
        this.defectgrptext = defectgrptext;
    }

    public String getItemdefectCod() {
        return itemdefectCod;
    }

    public void setItemdefectCod(String itemdefectCod) {
        this.itemdefectCod = itemdefectCod;
    }

    public String getDefectcodetext() {
        return defectcodetext;
    }

    public void setDefectcodetext(String defectcodetext) {
        this.defectcodetext = defectcodetext;
    }

    public String getItemdefectShtxt() {
        return itemdefectShtxt;
    }

    public void setItemdefectShtxt(String itemdefectShtxt) {
        this.itemdefectShtxt = itemdefectShtxt;
    }

    public String getCauseKey() {
        return causeKey;
    }

    public void setCauseKey(String causeKey) {
        this.causeKey = causeKey;
    }

    public String getCauseGrp() {
        return causeGrp;
    }

    public void setCauseGrp(String causeGrp) {
        this.causeGrp = causeGrp;
    }

    public String getCausegrptext() {
        return causegrptext;
    }

    public void setCausegrptext(String causegrptext) {
        this.causegrptext = causegrptext;
    }

    public String getCauseCod() {
        return causeCod;
    }

    public void setCauseCod(String causeCod) {
        this.causeCod = causeCod;
    }

    public String getCausecodetext() {
        return causecodetext;
    }

    public void setCausecodetext(String causecodetext) {
        this.causecodetext = causecodetext;
    }

    public String getCauseShtxt() {
        return causeShtxt;
    }

    public void setCauseShtxt(String causeShtxt) {
        this.causeShtxt = causeShtxt;
    }

    public String getUsr01() {
        return usr01;
    }

    public void setUsr01(String usr01) {
        this.usr01 = usr01;
    }

    public String getUsr02() {
        return usr02;
    }

    public void setUsr02(String usr02) {
        this.usr02 = usr02;
    }

    public String getUsr03() {
        return usr03;
    }

    public void setUsr03(String usr03) {
        this.usr03 = usr03;
    }

    public String getUsr04() {
        return usr04;
    }

    public void setUsr04(String usr04) {
        this.usr04 = usr04;
    }

    public String getUsr05() {
        return usr05;
    }

    public void setUsr05(String usr05) {
        this.usr05 = usr05;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.qmnum);
        dest.writeString(this.itemKey);
        dest.writeString(this.itempartGrp);
        dest.writeString(this.partgrptext);
        dest.writeString(this.itemdefectGrp);
        dest.writeString(this.itempartCod);
        dest.writeString(this.partcodetext);
        dest.writeString(this.defectgrptext);
        dest.writeString(this.itemdefectCod);
        dest.writeString(this.defectcodetext);
        dest.writeString(this.itemdefectShtxt);
        dest.writeString(this.causeKey);
        dest.writeString(this.causeGrp);
        dest.writeString(this.causegrptext);
        dest.writeString(this.causeCod);
        dest.writeString(this.causecodetext);
        dest.writeString(this.causeShtxt);
        dest.writeString(this.usr01);
        dest.writeString(this.usr02);
        dest.writeString(this.usr03);
        dest.writeString(this.usr04);
        dest.writeString(this.usr05);
        dest.writeString(this.action);
    }

    public Model_Notif_Causecode() {
    }

    protected Model_Notif_Causecode(Parcel in) {
        this.qmnum = in.readString();
        this.itemKey = in.readString();
        this.itempartGrp = in.readString();
        this.partgrptext = in.readString();
        this.itemdefectGrp = in.readString();
        this.itempartCod = in.readString();
        this.partcodetext = in.readString();
        this.defectgrptext = in.readString();
        this.itemdefectCod = in.readString();
        this.defectcodetext = in.readString();
        this.itemdefectShtxt = in.readString();
        this.causeKey = in.readString();
        this.causeGrp = in.readString();
        this.causegrptext = in.readString();
        this.causeCod = in.readString();
        this.causecodetext = in.readString();
        this.causeShtxt = in.readString();
        this.usr01 = in.readString();
        this.usr02 = in.readString();
        this.usr03 = in.readString();
        this.usr04 = in.readString();
        this.usr05 = in.readString();
        this.action = in.readString();
    }

    public static final Creator<Model_Notif_Causecode> CREATOR = new Creator<Model_Notif_Causecode>() {
        @Override
        public Model_Notif_Causecode createFromParcel(Parcel source) {
            return new Model_Notif_Causecode(source);
        }

        @Override
        public Model_Notif_Causecode[] newArray(int size) {
            return new Model_Notif_Causecode[size];
        }
    };
}