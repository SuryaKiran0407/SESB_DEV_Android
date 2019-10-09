package com.enstrapp.fieldtekpro.orders;

import android.os.Parcel;
import android.os.Parcelable;

public class OrdrMatrlPrcbl implements Parcelable {

    private String aufnr;
    private String oprtnId;
    private String oprtnShrtTxt;
    private String partId;
    private String matrlId;
    private String matrlTxt;
    private String plantId;
    private String location;
    private String quantity;
    private String meins;
    private String plantTxt;
    private String locationTxt;
    private String status;
    private String rsnum;
    private String rspos;
    private String postp;
    private String postpTxt;
    private boolean selected;
    private String unloading;
    private String receipt;
    private String stockcat_id;
    private String stockcat_text;
    private String batch;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getStockcat_id() {
        return stockcat_id;
    }

    public void setStockcat_id(String stockcat_id) {
        this.stockcat_id = stockcat_id;
    }

    public String getStockcat_text() {
        return stockcat_text;
    }

    public void setStockcat_text(String stockcat_text) {
        this.stockcat_text = stockcat_text;
    }

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getPlantTxt() {
        return plantTxt;
    }

    public void setPlantTxt(String plantTxt) {
        this.plantTxt = plantTxt;
    }

    public String getLocationTxt() {
        return locationTxt;
    }

    public void setLocationTxt(String locationTxt) {
        this.locationTxt = locationTxt;
    }

    public String getPostpTxt() {
        return postpTxt;
    }

    public void setPostpTxt(String postpTxt) {
        this.postpTxt = postpTxt;
    }


    public String getOprtnId() {
        return oprtnId;
    }

    public void setOprtnId(String oprtnId) {
        this.oprtnId = oprtnId;
    }

    public String getOprtnShrtTxt() {
        return oprtnShrtTxt;
    }

    public void setOprtnShrtTxt(String oprtnShrtTxt) {
        this.oprtnShrtTxt = oprtnShrtTxt;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getMatrlId() {
        return matrlId;
    }

    public void setMatrlId(String matrlId) {
        this.matrlId = matrlId;
    }

    public String getMatrlTxt() {
        return matrlTxt;
    }

    public void setMatrlTxt(String matrlTxt) {
        this.matrlTxt = matrlTxt;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRsnum() {
        return rsnum;
    }

    public void setRsnum(String rsnum) {
        this.rsnum = rsnum;
    }

    public String getRspos() {
        return rspos;
    }

    public void setRspos(String rspos) {
        this.rspos = rspos;
    }

    public String getPostp() {
        return postp;
    }

    public void setPostp(String postp) {
        this.postp = postp;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getUnloading() {
        return unloading;
    }

    public void setUnloading(String unloading) {
        this.unloading = unloading;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }


    public OrdrMatrlPrcbl() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.oprtnId);
        dest.writeString(this.aufnr);
        dest.writeString(this.oprtnShrtTxt);
        dest.writeString(this.partId);
        dest.writeString(this.matrlId);
        dest.writeString(this.matrlTxt);
        dest.writeString(this.plantId);
        dest.writeString(this.location);
        dest.writeString(this.quantity);
        dest.writeString(this.meins);
        dest.writeString(this.plantTxt);
        dest.writeString(this.locationTxt);
        dest.writeString(this.status);
        dest.writeString(this.rsnum);
        dest.writeString(this.rspos);
        dest.writeString(this.postp);
        dest.writeString(this.postpTxt);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.unloading);
        dest.writeString(this.receipt);
        dest.writeString(this.stockcat_id);
        dest.writeString(this.stockcat_text);
        dest.writeString(this.batch);
    }

    protected OrdrMatrlPrcbl(Parcel in) {
        this.oprtnId = in.readString();
        this.aufnr = in.readString();
        this.oprtnShrtTxt = in.readString();
        this.partId = in.readString();
        this.matrlId = in.readString();
        this.matrlTxt = in.readString();
        this.plantId = in.readString();
        this.location = in.readString();
        this.quantity = in.readString();
        this.meins = in.readString();
        this.plantTxt = in.readString();
        this.locationTxt = in.readString();
        this.status = in.readString();
        this.rsnum = in.readString();
        this.rspos = in.readString();
        this.postp = in.readString();
        this.postpTxt = in.readString();
        this.selected = in.readByte() != 0;
        this.unloading = in.readString();
        this.receipt = in.readString();
        this.stockcat_id = in.readString();
        this.stockcat_text = in.readString();
        this.batch = in.readString();
    }

    public static final Creator<OrdrMatrlPrcbl> CREATOR = new Creator<OrdrMatrlPrcbl>() {
        @Override
        public OrdrMatrlPrcbl createFromParcel(Parcel source) {
            return new OrdrMatrlPrcbl(source);
        }

        @Override
        public OrdrMatrlPrcbl[] newArray(int size) {
            return new OrdrMatrlPrcbl[size];
        }
    };
}
