package com.enstrapp.fieldtekpro.MIS;

/**
 * Created by Sashi on 21/03/2017.
 */

public class Label_List_Object
{
    private String label = "";
    private int color;
    private String objtyp = "";

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    public void setobjtyp(String objtyp) {
        this.objtyp = objtyp;
    }

    public String getobjtyp() {
        return objtyp;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
