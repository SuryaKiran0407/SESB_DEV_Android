package com.enstrapp.fieldtekpro.Calibration;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import static android.content.Context.MODE_PRIVATE;


public class Calibration_UsageDecision_Fragment extends Fragment implements View.OnClickListener {

    String Aufnr = "", WERKS = "", VAUSWAHLMG = "", udcode_id = "", udcode_text = "", quality_score = "", Udtext = "";
    Button udcode_button;
    int udcode_intent_value = 1;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    TextView udcode_text_textview, quality_score_textview;
    EditText notes_edittext;
    ImageView valuation_imageview;

    public Calibration_UsageDecision_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calibration_usagedecision_fragment, container, false);

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Calibration_Orders_Operations_List_Activity activity = (Calibration_Orders_Operations_List_Activity) getActivity();
        Aufnr = activity.getorder_id();
        WERKS = activity.getplant_id();

        udcode_button = (Button) rootView.findViewById(R.id.udcode_button);
        udcode_text_textview = (TextView) rootView.findViewById(R.id.udcode_text_textview);
        quality_score_textview = (TextView) rootView.findViewById(R.id.quality_score_textview);
        notes_edittext = (EditText) rootView.findViewById(R.id.notes_edittext);
        valuation_imageview = (ImageView) rootView.findViewById(R.id.valuation_imageview);

        udcode_button.setOnClickListener(this);


        String vkatart = "";
        try {
            Cursor cursor1 = null;
            cursor1 = App_db.rawQuery("select * from EtQudData Where Aufnr = ?", new String[]{Aufnr});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        vkatart = cursor1.getString(8);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
                vkatart = "";
            }
        } catch (Exception e) {
            vkatart = "";
        }
        if (vkatart != null && !vkatart.equals("")) {
            udcode_button.setEnabled(false);
            notes_edittext.setEnabled(false);
        }


        new Get_UsageDecision_Data().execute();

        return rootView;
    }

    private class Get_UsageDecision_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = App_db.rawQuery("select * from EtQudData where Aufnr = ?", new String[]{Aufnr});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            VAUSWAHLMG = cursor.getString(7);
                            Udtext = cursor.getString(14);
                            String VCODE = cursor.getString(8);
                            if (VCODE != null && !VCODE.equals("")) {
                                if (VCODE.equalsIgnoreCase("null")) {
                                    udcode_id = "";
                                } else {
                                    udcode_id = VCODE;
                                    quality_score = cursor.getString(7);
                                    try {
                                        Cursor cursor1 = null;
                                        cursor1 = App_db.rawQuery("select * from EtUdecCodes Where Code = ? AND Werks = ? AND Auswahlmge = ?", new String[]{udcode_id, WERKS, VAUSWAHLMG});
                                        if (cursor1 != null && cursor1.getCount() > 0) {
                                            if (cursor1.moveToFirst()) {
                                                do {
                                                    udcode_text = cursor1.getString(6);
                                                }
                                                while (cursor1.moveToNext());
                                            }
                                        } else {
                                            cursor1.close();
                                        }
                                    } catch (Exception e) {
                                    }

                                }
                            } else {
                                udcode_id = "";
                            }
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            udcode_button.setText(udcode_id);
            udcode_text_textview.setText(udcode_text);
            quality_score_textview.setText(quality_score);
            notes_edittext.setText(Udtext);
            if (udcode_id.startsWith("A")) {
                valuation_imageview.setImageResource(R.drawable.ic_tickmark_enabled);
            } else {
                valuation_imageview.setImageResource(R.drawable.ic_tickmark_disabled);
            }
            progressDialog.dismiss_progress_dialog();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == udcode_button) {
            Intent notification_type_intent = new Intent(getActivity(), Calibration_UDCode_List_Activity.class);
            notification_type_intent.putExtra("WERKS", WERKS);
            notification_type_intent.putExtra("VAUSWAHLMG", VAUSWAHLMG);
            startActivityForResult(notification_type_intent, udcode_intent_value);
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == udcode_intent_value) {
                udcode_id = data.getStringExtra("udcode_id");
                udcode_text = data.getStringExtra("udcode_text");
                Udtext = data.getStringExtra("BEWERTUNG");
                quality_score = data.getStringExtra("QKENNZAHL");
                udcode_button.setText(udcode_id);
                udcode_text_textview.setText(udcode_text);
                quality_score_textview.setText(quality_score);
                //notes_edittext.setText(Udtext);
            }
        }
    }


    public Calibration_Usage_Decision_Object getUsageDecisionData() {
        return new Calibration_Usage_Decision_Object(udcode_id, udcode_text, quality_score, notes_edittext.getText().toString());
    }


}
