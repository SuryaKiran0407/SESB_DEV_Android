package com.enstrapp.fieldtekpro.MIS;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Sashi on 22/02/2017.
 */

public class MyXAxisValueFormatter implements IAxisValueFormatter {

    private String[] mValues;
    private int len;

    public MyXAxisValueFormatter(String[] values) {
        this.mValues = values;
        len = values.length;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        if ((int)value < len){
            return mValues[(int) value];
        }
        return null;
    }
}
