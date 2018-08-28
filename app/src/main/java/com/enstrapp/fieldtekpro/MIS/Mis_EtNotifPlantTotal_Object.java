package com.enstrapp.fieldtekpro.MIS;

/**
 * Created by Sashi on 02/03/2017.
 */

public class Mis_EtNotifPlantTotal_Object /*implements Parcelable*/{

    private String mTotal_pt;
    private String mSwerk_pt;
    private String mName_pt;
    private String mQmart_pt;
    private String mQmartx_pt;
    private String mOuts_pt;
    private String mInpr_pt;
    private String mComp_pt;
    private String mDele_pt;
    private String mChecked;

    public void setmTotal_pt(String mTotal_pt) {
        this.mTotal_pt = mTotal_pt;
    }

    public String getmTotal_pt() {
        return mTotal_pt;
    }

    public void setmSwerk_pt(String mSwerk_pt) {
        this.mSwerk_pt = mSwerk_pt;
    }

    public String getmSwerk_pt() {
        return mSwerk_pt;
    }

    public void setmName_pt(String mName_pt) {
        this.mName_pt = mName_pt;
    }

    public String getmName_pt() {
        return mName_pt;
    }

    public void setmQmart_pt(String mQmart_pt) {
        this.mQmart_pt = mQmart_pt;
    }

    public String getmQmart_pt() {
        return mQmart_pt;
    }

    public void setmQmartx_pt(String mQmartx_pt) {
        this.mQmartx_pt = mQmartx_pt;
    }

    public String getmQmartx_pt() {
        return mQmartx_pt;
    }

    public void setmOuts_pt(String mOuts_pt) {
        this.mOuts_pt = mOuts_pt;
    }

    public String getmOuts_pt() {
        return mOuts_pt;
    }

    public void setmInpr_pt(String mInpr_pt) {
        this.mInpr_pt = mInpr_pt;
    }

    public String getmInpr_pt() {
        return mInpr_pt;
    }

    public void setmComp_pt(String mComp_pt) {
        this.mComp_pt = mComp_pt;
    }

    public String getmComp_pt() {
        return mComp_pt;
    }

    public void setmDele_pt(String mDele_pt) {
        this.mDele_pt = mDele_pt;
    }

    public String getmDele_pt() {
        return mDele_pt;
    }

    public void setmChecked(String mChecked) {
        this.mChecked = mChecked;
    }

    public String getmChecked() {
        return mChecked;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTotal_pt);
        dest.writeString(mSwerk_pt);
        dest.writeString(mName_pt);
        dest.writeString(mQmart_pt);
        dest.writeString(mQmartx_pt);
        dest.writeString(mOuts_pt);
        dest.writeString(mInpr_pt);
        dest.writeString(mComp_pt);
        dest.writeString(mDele_pt);
        dest.writeString(mChecked);
    }

    private Mis_EtNotifPlantTotal_Object(Parcel in){
        this.mTotal_pt = in.readString();
        this.mSwerk_pt = in.readString();
        this.mName_pt = in.readString();
        this.mQmart_pt = in.readString();
        this.mQmartx_pt = in.readString();
        this.mOuts_pt = in.readString();
        this.mInpr_pt = in.readString();
        this.mComp_pt = in.readString();
        this.mDele_pt = in.readString();
        this.mChecked = in.readString();
    }

    public static final Creator<Mis_EtNotifPlantTotal_Object> CREATOR = new Creator<Mis_EtNotifPlantTotal_Object>(){
        @Override
        public Mis_EtNotifPlantTotal_Object createFromParcel(Parcel source) {
            return new Mis_EtNotifPlantTotal_Object(source);
        }

        @Override
        public Mis_EtNotifPlantTotal_Object[] newArray(int size) {
            return new Mis_EtNotifPlantTotal_Object[size];
        }
    };*/
}
