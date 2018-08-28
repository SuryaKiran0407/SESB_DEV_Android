package com.enstrapp.fieldtekpro.MIS;

/**
 * Created by Sashi on 02/03/2017.
 */

public class Mis_EtNotifRep_Object /*implements Parcelable*/ {

    private int mNo_re;
    private String mPhase_re;
    private String mSwerk_re;
    private String mArbpl_re;
    private String mQmart_re;
    private String mQmnum_re;
    private String mQmtxt_re;
    private String mErnam_re;
    private String mEqunr_re;
    private String mEqktx_re;
    private String mPriok_re;
    private String mStrmn_re;
    private String mLtrmn_re;
    private String mAuswk_re;
    private String mTplnr_re;
    private String mMsaus_re;
    private String mAusvn_re;
    private String mAusbs_re;

    public void setmNo_re(int mNo_re) {
        this.mNo_re = mNo_re;
    }

    public int getmNo_re() {
        return mNo_re;
    }

    public void setmPhase_re(String mPhase_re) {
        this.mPhase_re = mPhase_re;
    }

    public String getmPhase_re() {
        return mPhase_re;
    }

    public void setmSwerk_re(String mSwerk_re) {
        this.mSwerk_re = mSwerk_re;
    }

    public String getmSwerk_re() {
        return mSwerk_re;
    }

    public void setmQmart_re(String mQmart_re) {
        this.mQmart_re = mQmart_re;
    }

    public String getmQmart_re() {
        return mQmart_re;
    }

    public void setmQmnum_re(String mQmnum_re) {
        this.mQmnum_re = mQmnum_re;
    }

    public String getmQmnum_re() {
        return mQmnum_re;
    }

    public void setmQmtxt_re(String mQmtxt_re) {
        this.mQmtxt_re = mQmtxt_re;
    }

    public String getmQmtxt_re() {
        return mQmtxt_re;
    }

    public void setmErnam_re(String mErnam_re) {
        this.mErnam_re = mErnam_re;
    }

    public String getmErnam_re() {
        return mErnam_re;
    }

    public void setmEqunr_re(String mEqunr_re) {
        this.mEqunr_re = mEqunr_re;
    }

    public String getmEqunr_re() {
        return mEqunr_re;
    }

    public void setmEqktx_re(String mEqktx_re) {
        this.mEqktx_re = mEqktx_re;
    }

    public String getmEqktx_re() {
        return mEqktx_re;
    }

    public void setmPriok_re(String mPriok_re) {
        this.mPriok_re = mPriok_re;
    }

    public String getmPriok_re() {
        return mPriok_re;
    }

    public void setmStrmn_re(String mStrmn_re) {
        this.mStrmn_re = mStrmn_re;
    }

    public String getmStrmn_re() {
        return mStrmn_re;
    }

    public void setmLtrmn_re(String mLtrmn_re) {
        this.mLtrmn_re = mLtrmn_re;
    }

    public String getmLtrmn_re() {
        return mLtrmn_re;
    }

    public void setmAuswk_re(String mAuswk_re) {
        this.mAuswk_re = mAuswk_re;
    }

    public String getmAuswk_re() {
        return mAuswk_re;
    }

    public void setmTplnr_re(String mTplnr_re) {
        this.mTplnr_re = mTplnr_re;
    }

    public String getmTplnr_re() {
        return mTplnr_re;
    }

    public void setmMsaus_re(String mMsaus_re) {
        this.mMsaus_re = mMsaus_re;
    }

    public String getmMsaus_re() {
        return mMsaus_re;
    }

    public void setmAusvn_re(String mAusvn_re) {
        this.mAusvn_re = mAusvn_re;
    }

    public String getmAusvn_re() {
        return mAusvn_re;
    }

    public void setmAusbs_re(String mAusbs_re) {
        this.mAusbs_re = mAusbs_re;
    }

    public String getmAusbs_re() {
        return mAusbs_re;
    }

    public String getmArbpl_re() {
        return mArbpl_re;
    }

    public void setmArbpl_re(String mArbpl_re) {
        this.mArbpl_re = mArbpl_re;
    }

    /*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhase_re);
        dest.writeString(mSwerk_re);
        dest.writeString(mQmart_re);
        dest.writeString(mQmnum_re);
        dest.writeString(mQmtxt_re);
        dest.writeString(mErnam_re);
        dest.writeString(mEqunr_re);
        dest.writeString(mEqktx_re);
        dest.writeString(mPriok_re);
        dest.writeString(mStrmn_re);
        dest.writeString(mLtrmn_re);
        dest.writeString(mAuswk_re);
        dest.writeString(mTplnr_re);
        dest.writeString(mMsaus_re);
        dest.writeString(mAusvn_re);
        dest.writeString(mAusbs_re);
    }

    private Mis_EtNotifRep_Object (Parcel in){
        this.mPhase_re = in.readString();
        this.mSwerk_re = in.readString();
        this.mQmart_re = in.readString();
        this.mQmnum_re = in.readString();
        this.mQmtxt_re = in.readString();
        this.mErnam_re = in.readString();
        this.mEqunr_re = in.readString();
        this.mEqktx_re = in.readString();
        this.mPriok_re = in.readString();
        this.mStrmn_re = in.readString();
        this.mLtrmn_re = in.readString();
        this.mAuswk_re = in.readString();
        this.mTplnr_re = in.readString();
        this.mMsaus_re = in.readString();
        this.mAusvn_re = in.readString();
        this.mAusbs_re = in.readString();
    }

    public static final Creator<Mis_EtNotifRep_Object> CREATOR = new Creator<Mis_EtNotifRep_Object>(){
        @Override
        public Mis_EtNotifRep_Object[] newArray(int size) {
            return new Mis_EtNotifRep_Object[size];
        }

        @Override
        public Mis_EtNotifRep_Object createFromParcel(Parcel source) {
            return new Mis_EtNotifRep_Object(source);
        }
    };*/
}
