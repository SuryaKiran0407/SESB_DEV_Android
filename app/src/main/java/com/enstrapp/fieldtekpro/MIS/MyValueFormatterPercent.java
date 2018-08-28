package com.enstrapp.fieldtekpro.MIS;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Sashi on 12/04/2017.
 */

public class MyValueFormatterPercent implements IValueFormatter {

    private DecimalFormat mFormat;

        public MyValueFormatterPercent() {
            mFormat = new DecimalFormat("###,###,###"); // use no decimals
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            String perc = mFormat.format(value);
            return perc+"%";
        }
}
