package com.enstrapp.fieldtekpro.Passcode;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.DashBoard.DashBoard_Activity;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.InitialLoad_REST_Activity;
import com.enstrapp.fieldtekpro.Initialload.InitialLoad_Activity;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.login.Login_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

import in.arjsna.passcodeview.PassCodeView;

import static android.content.Context.MODE_PRIVATE;


public class Passcode_Fragment extends Fragment {

    private PassCodeView passCodeView;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    String click_status = "";
    TextView forgot_passcode_textview;
    Dialog decision_dialog;
    private static String DATABASE_NAME = "";
    private SQLiteDatabase FieldTekPro_db;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        passCodeView = (PassCodeView) mRootView.findViewById(R.id.pass_code_view);
        TextView promptView = (TextView) mRootView.findViewById(R.id.promptview);
        forgot_passcode_textview = (TextView) mRootView.findViewById(R.id.forgot_passcode_textview);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/metropolis_medium.ttf");
        passCodeView.setTypeFace(typeFace);
        passCodeView.setKeyTextColor(R.color.black_shade);
        passCodeView.setEmptyDrawable(R.drawable.empty_dot);
        passCodeView.setFilledDrawable(R.drawable.filled_dot);
        promptView.setTypeface(typeFace);

        /* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getActivity().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
        /* Initializing Shared Preferences */

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);
        if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals("")) {
            forgot_passcode_textview.setVisibility(View.VISIBLE);
        } else {
            forgot_passcode_textview.setVisibility(View.GONE);
        }

        passCodeView.setOnTextChangeListener(new PassCodeView.TextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.length() == 4) {
                    if (click_status != null && !click_status.equals("")) {
                    } else {
                        bindEvents(text);
                    }
                }
            }
        });


        forgot_passcode_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision_dialog = new Dialog(getActivity());
                decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                decision_dialog.setCancelable(false);
                decision_dialog.setCanceledOnTouchOutside(false);
                decision_dialog.setContentView(R.layout.decision_dialog);
                ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
                TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
                Glide.with(getActivity()).load(R.drawable.error_dialog_gif).into(imageview);
                Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                description_textview.setText(getString(R.string.data_erase));
                decision_dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decision_dialog.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decision_dialog.dismiss();
                        custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
                        SharedPreferences settings = getActivity().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Cursor c = FieldTekPro_db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
                        List<String> tables = new ArrayList<>();
                        while (c.moveToNext()) {
                            tables.add(c.getString(0));
                        }
                        for (String table : tables) {
                            String dropQuery = "DROP TABLE IF EXISTS " + table;
                            FieldTekPro_db.execSQL(dropQuery);
                        }
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), Login_Activity.class);
                        startActivity(intent);
                        custom_progress_dialog.dismiss_progress_dialog();
                    }
                });
            }
        });


        return mRootView;
    }

    private void bindEvents(String text)
    {
        click_status = "clicked";
        String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);

        String passcode_text = FieldTekPro_SharedPref.getString("passcode_text", null);
        String InitialLoad = FieldTekPro_SharedPref.getString("FieldTekPro_InitialLoad", null);
        String Refresh = FieldTekPro_SharedPref.getString("FieldTekPro_Refresh", null);
        String offline = FieldTekPro_SharedPref.getString("offline", null);
        if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals(""))
        {
            if (text.equals(passcode_text))
            {
                if (offline != null && offline.equals("X"))
                {
                    getActivity().finish();
                    Intent notification_intent = new Intent(getActivity(), DashBoard_Activity.class);
                    startActivity(notification_intent);
                    FieldTekPro_SharedPrefeditor.putString("offline", "");
                    FieldTekPro_SharedPrefeditor.commit();
                }
                else
                {
                    if (InitialLoad != null && !InitialLoad.equals(""))
                    {
                        getActivity().finish();
                        String webservice_type = getString(R.string.webservice_type);
                        if(webservice_type.equalsIgnoreCase("odata"))
                        {
                            Intent intialload_intent = new Intent(getActivity(), InitialLoad_Activity.class);
                            intialload_intent.putExtra("From", "LOAD");
                            startActivity(intialload_intent);
                        }
                        else  if(webservice_type.equalsIgnoreCase("rest"))
                        {
                            Intent intialload_intent = new Intent(getActivity(), InitialLoad_REST_Activity.class);
                            intialload_intent.putExtra("From","LOAD");
                            startActivity(intialload_intent);
                        }
                    }
                    else if (Refresh != null && !Refresh.equals(""))
                    {
                        getActivity().finish();
                        String webservice_type = getString(R.string.webservice_type);
                        if(webservice_type.equalsIgnoreCase("odata"))
                        {
                            Intent intialload_intent = new Intent(getActivity(), InitialLoad_Activity.class);
                            intialload_intent.putExtra("From", "REFR");
                            startActivity(intialload_intent);
                        }
                        else  if(webservice_type.equalsIgnoreCase("rest"))
                        {
                            Intent intialload_intent = new Intent(getActivity(), InitialLoad_REST_Activity.class);
                            intialload_intent.putExtra("From","REFR");
                            startActivity(intialload_intent);
                        }
                    }
                    else
                    {
                        getActivity().finish();
                        Intent notification_intent = new Intent(getActivity(), DashBoard_Activity.class);
                        startActivity(notification_intent);
                    }
                }
            }
            else
            {
                click_status = "";
                passCodeView.setError(true);
                Toast.makeText(getActivity(), getString(R.string.wrong_passcode), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            if (text.equals(passcode_text))
            {
                getActivity().finish();
                String webservice_type = getString(R.string.webservice_type);
                if(webservice_type.equalsIgnoreCase("odata"))
                {
                    Intent intialload_intent = new Intent(getActivity(), InitialLoad_Activity.class);
                    intialload_intent.putExtra("From", "LOAD");
                    startActivity(intialload_intent);
                }
                else  if(webservice_type.equalsIgnoreCase("rest"))
                {
                    Intent intialload_intent = new Intent(getActivity(), InitialLoad_REST_Activity.class);
                    intialload_intent.putExtra("From","LOAD");
                    startActivity(intialload_intent);
                }
            }
            else if (passcode_text == null || passcode_text.equals(""))
            {
                FieldTekPro_SharedPrefeditor.putString("passcode_text", text);
                FieldTekPro_SharedPrefeditor.commit();
                getActivity().finish();
                String webservice_type = getString(R.string.webservice_type);
                if(webservice_type.equalsIgnoreCase("odata"))
                {
                    Intent intialload_intent = new Intent(getActivity(), InitialLoad_Activity.class);
                    intialload_intent.putExtra("From", "LOAD");
                    startActivity(intialload_intent);
                }
                else  if(webservice_type.equalsIgnoreCase("rest"))
                {
                    Intent intialload_intent = new Intent(getActivity(), InitialLoad_REST_Activity.class);
                    intialload_intent.putExtra("From","LOAD");
                    startActivity(intialload_intent);
                }
            }
            else
            {
                click_status = "";
                passCodeView.setError(true);
                Toast.makeText(getActivity(), "You have entered wrong passcode", Toast.LENGTH_LONG).show();
            }
        }
    }
}
