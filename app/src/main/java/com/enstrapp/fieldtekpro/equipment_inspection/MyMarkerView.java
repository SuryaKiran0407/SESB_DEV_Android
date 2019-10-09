package com.enstrapp.fieldtekpro.equipment_inspection;

import android.content.Context;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by RAKSHITHA V SUVARNA on 12-03-2018.
 */

public class MyMarkerView extends MarkerView {
    private TextView textViewValue = null;
    private MPPointF mOffset;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        this.textViewValue = (TextView) this.findViewById(R.id.line_fragment_marker_textview_value);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        this.textViewValue.setText("" + e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}
