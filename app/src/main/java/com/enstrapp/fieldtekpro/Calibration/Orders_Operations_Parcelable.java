package com.enstrapp.fieldtekpro.Calibration;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by VENKATA SURYA KIRAN on 08-Jun-18.
 */

public class Orders_Operations_Parcelable implements Parcelable {
    private String operation_id;
    private String operations_shorttext;
    private String operations_longtext;
    private String duration;
    private String duration_unit;
    private String plant_id;
    private String plant_text;
    private String workcenter_id;
    private String workcenter_text;
    private String controlkey_id;
    private String controlkey_text;
    private String status;
    private String aueru;
    private String Usr01;
    private String Equnr;
    private ArrayList<Start_Calibration_Parcelable> start_calibration_parcelables;

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getOperations_shorttext() {
        return operations_shorttext;
    }

    public void setOperations_shorttext(String operations_shorttext) {
        this.operations_shorttext = operations_shorttext;
    }

    public String getOperations_longtext() {
        return operations_longtext;
    }

    public void setOperations_longtext(String operations_longtext) {
        this.operations_longtext = operations_longtext;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration_unit() {
        return duration_unit;
    }

    public void setDuration_unit(String duration_unit) {
        this.duration_unit = duration_unit;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(String plant_id) {
        this.plant_id = plant_id;
    }

    public String getPlant_text() {
        return plant_text;
    }

    public void setPlant_text(String plant_text) {
        this.plant_text = plant_text;
    }

    public String getWorkcenter_id() {
        return workcenter_id;
    }

    public void setWorkcenter_id(String workcenter_id) {
        this.workcenter_id = workcenter_id;
    }

    public String getWorkcenter_text() {
        return workcenter_text;
    }

    public void setWorkcenter_text(String workcenter_text) {
        this.workcenter_text = workcenter_text;
    }

    public String getControlkey_id() {
        return controlkey_id;
    }

    public void setControlkey_id(String controlkey_id) {
        this.controlkey_id = controlkey_id;
    }

    public String getControlkey_text() {
        return controlkey_text;
    }

    public void setControlkey_text(String controlkey_text) {
        this.controlkey_text = controlkey_text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAueru() {
        return aueru;
    }

    public void setAueru(String aueru) {
        this.aueru = aueru;
    }

    public String getUsr01() {
        return Usr01;
    }

    public void setUsr01(String usr01) {
        Usr01 = usr01;
    }

    public String getEqunr() {
        return Equnr;
    }

    public void setEqunr(String equnr) {
        Equnr = equnr;
    }

    public ArrayList<Start_Calibration_Parcelable> getStart_calibration_parcelables() {
        return start_calibration_parcelables;
    }

    public void setStart_calibration_parcelables(ArrayList<Start_Calibration_Parcelable> start_calibration_parcelables) {
        this.start_calibration_parcelables = start_calibration_parcelables;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.operation_id);
        dest.writeString(this.operations_shorttext);
        dest.writeString(this.operations_longtext);
        dest.writeString(this.duration);
        dest.writeString(this.duration_unit);
        dest.writeString(this.plant_id);
        dest.writeString(this.plant_text);
        dest.writeString(this.workcenter_id);
        dest.writeString(this.workcenter_text);
        dest.writeString(this.controlkey_id);
        dest.writeString(this.controlkey_text);
        dest.writeString(this.status);
        dest.writeString(this.aueru);
        dest.writeString(this.Usr01);
        dest.writeString(this.Equnr);
        dest.writeTypedList(this.start_calibration_parcelables);
    }

    public Orders_Operations_Parcelable() {
    }

    protected Orders_Operations_Parcelable(Parcel in) {
        this.operation_id = in.readString();
        this.operations_shorttext = in.readString();
        this.operations_longtext = in.readString();
        this.duration = in.readString();
        this.duration_unit = in.readString();
        this.plant_id = in.readString();
        this.plant_text = in.readString();
        this.workcenter_id = in.readString();
        this.workcenter_text = in.readString();
        this.controlkey_id = in.readString();
        this.controlkey_text = in.readString();
        this.status = in.readString();
        this.aueru = in.readString();
        this.Usr01 = in.readString();
        this.Equnr = in.readString();
        this.start_calibration_parcelables = in.createTypedArrayList(Start_Calibration_Parcelable.CREATOR);
    }

    public static final Creator<Orders_Operations_Parcelable> CREATOR = new Creator<Orders_Operations_Parcelable>() {
        @Override
        public Orders_Operations_Parcelable createFromParcel(Parcel source) {
            return new Orders_Operations_Parcelable(source);
        }

        @Override
        public Orders_Operations_Parcelable[] newArray(int size) {
            return new Orders_Operations_Parcelable[size];
        }
    };
}
