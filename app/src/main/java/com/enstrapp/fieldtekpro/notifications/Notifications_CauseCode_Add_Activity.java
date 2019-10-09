package com.enstrapp.fieldtekpro.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.HashMap;

public class Notifications_CauseCode_Add_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText causedescription_edittext, eventdescription_edittext;
    String cause_id = "", cause_text = "", causecode_id = "", causecode_text = "",
            eventcode_id = "", eventcode_text = "", event_id = "", event_text = "",
            objectpartcode_id = "", objectpartcode_text = "", objectpart_id = "",
            objectpart_text = "", functionlocation_id = "", equipment_id = "", catelog_profile = "";
    int request_id = 0;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Error_Dialog error_dialog = new Error_Dialog();
    int causecode_type = 6, cause_type = 5,
            eventcode_type = 4, event_type = 3, objectpart_type = 1, objectpartcode_type = 2;
    Button cancel_button, add_button;
    ImageView back_imageview;
    String position = "", itemkey = "", causekey = "", status = "I";
    ArrayList<HashMap<String, String>> selected_object_custom_info_arraylist = new ArrayList<>();
    ArrayList<HashMap<String, String>> selected_cause_custom_info_arraylist = new ArrayList<>();
    Button causecode_button,cause_button,eventcode_button,event_button,objpartcode_button,objpart_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_cause_code_add_activity);

        eventdescription_edittext = (EditText) findViewById(R.id.eventdescription_edittext);
        causedescription_edittext = (EditText) findViewById(R.id.causedescription_edittext);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        objpart_button = (Button) findViewById(R.id.objpart_button);
        objpartcode_button = (Button) findViewById(R.id.objpartcode_button);
        event_button = (Button) findViewById(R.id.event_button);
        eventcode_button = (Button) findViewById(R.id.eventcode_button);
        cause_button = (Button) findViewById(R.id.cause_button);
        causecode_button = (Button) findViewById(R.id.causecode_button);


        selected_object_custom_info_arraylist.clear();
        selected_cause_custom_info_arraylist.clear();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            functionlocation_id = extras.getString("functionlocation_id");
            equipment_id = extras.getString("equipment_id");
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
            position = extras.getString("position");
            if (position != null && !position.equals("")) {
                add_button.setText("Update");
                status = extras.getString("status");
                itemkey = extras.getString("itemkey");
                causekey = extras.getString("causekey");
                objectpart_id = extras.getString("objectpart_id");
                objectpart_text = extras.getString("objectpart_text");
                objpart_button.setText(objectpart_id + " - " + objectpart_text);
                objectpartcode_id = extras.getString("objectpartcode_id");
                objectpartcode_text = extras.getString("objectpartcode_text");
                objpartcode_button.setText(objectpartcode_id + " - " + objectpartcode_text);
                event_id = extras.getString("event_id");
                event_text = extras.getString("event_text");
                event_button.setText(event_id + " - " + event_text);
                eventcode_id = extras.getString("eventcode_id");
                eventcode_text = extras.getString("eventcode_text");
                eventcode_button.setText(eventcode_id + " - " + eventcode_text);
                String event_descritpion = extras.getString("event_descritpion");
                eventdescription_edittext.setText(event_descritpion);
                cause_id = extras.getString("cause_id");
                cause_text = extras.getString("cause_text");
                cause_button.setText(cause_id + " - " + cause_text);
                causecode_id = extras.getString("causecode_id");
                causecode_text = extras.getString("causecode_text");
                causecode_button.setText(causecode_id + " - " + causecode_text);
                String cause_descritpion = extras.getString("cause_descritpion");
                causedescription_edittext.setText(cause_descritpion);
            }
        }


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        try {
            if (equipment_id != null && !equipment_id.equals("")) {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Equnr = ?",
                        new String[]{equipment_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            catelog_profile = cursor.getString(6);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } else if (functionlocation_id != null && !functionlocation_id.equals("")) {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where" +
                        " Tplnr = ?", new String[]{functionlocation_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            catelog_profile = cursor.getString(10);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } else {
                catelog_profile = "";
            }
        } catch (Exception e) {
        }

        objpart_button.setOnClickListener(this);
        objpartcode_button.setOnClickListener(this);
        event_button.setOnClickListener(this);
        eventcode_button.setOnClickListener(this);
        cause_button.setOnClickListener(this);
        causecode_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == add_button)
        {
            if (objectpart_id != null && !objectpart_id.equals(""))
            {
                if (objectpartcode_id != null && !objectpartcode_id.equals(""))
                {
                    Intent intent = new Intent();
                    intent.putExtra("objectpart_id", objectpart_id);
                    intent.putExtra("objectpart_text", objectpart_text);
                    intent.putExtra("objectpartcode_id", objectpartcode_id);
                    intent.putExtra("objectpartcode_text", objectpartcode_text);
                    intent.putExtra("event_id", event_id);
                    intent.putExtra("event_text", event_text);
                    intent.putExtra("eventcode_id", eventcode_id);
                    intent.putExtra("eventcode_text", eventcode_text);
                    intent.putExtra("event_descritpion",
                            eventdescription_edittext.getText().toString());
                    intent.putExtra("cause_id", cause_id);
                    intent.putExtra("cause_text", cause_text);
                    intent.putExtra("causecode_id", causecode_id);
                    intent.putExtra("causecode_text", causecode_text);
                    intent.putExtra("cause_descritpion",
                            causedescription_edittext.getText().toString());
                    intent.putExtra("itemkey", itemkey);
                    intent.putExtra("causekey", causekey);
                    intent.putExtra("position", position);
                    intent.putExtra("status", status);
                    intent.putExtra("selected_object_custom_info_arraylist",
                            selected_object_custom_info_arraylist);
                    intent.putExtra("selected_cause_custom_info_arraylist",
                            selected_cause_custom_info_arraylist);
                    setResult(request_id, intent);
                    Notifications_CauseCode_Add_Activity.this.finish();
                }
                else
                {
                    error_dialog.show_error_dialog(Notifications_CauseCode_Add_Activity.this,
                            getString(R.string.notifcause_selectobjprtcode));
                }
            } else {
                error_dialog.show_error_dialog(Notifications_CauseCode_Add_Activity.this,
                        getString(R.string.notifcause_selectobjprt));
            }
        } else if (v == back_imageview) {
            Notifications_CauseCode_Add_Activity.this.finish();
        } else if (v == cancel_button) {
            Notifications_CauseCode_Add_Activity.this.finish();
        } else if (v == objpart_button) {
            Intent objectpart_intent =
                    new Intent(Notifications_CauseCode_Add_Activity.this,
                            Notifications_ObjectPart_Activity.class);
            objectpart_intent.putExtra("catelog_profile", catelog_profile);
            objectpart_intent.putExtra("request_id", Integer.toString(objectpart_type));
            startActivityForResult(objectpart_intent, objectpart_type);
        } else if (v == objpartcode_button) {
            if (objectpart_id != null && !objectpart_id.equals("")) {
                Intent objectpart_intent =
                        new Intent(Notifications_CauseCode_Add_Activity.this,
                                Notifications_ObjectPart_Code_Activity.class);
                objectpart_intent.putExtra("objectpart_id", objectpart_id);
                objectpart_intent.putExtra("catelog_profile", catelog_profile);
                objectpart_intent.putExtra("request_id", Integer.toString(objectpartcode_type));
                startActivityForResult(objectpart_intent, objectpartcode_type);
            } else {
                error_dialog.show_error_dialog(Notifications_CauseCode_Add_Activity.this,
                        getString(R.string.notifcause_selectobjprt));
            }
        } else if (v == event_button) {
            Intent objectpart_intent =
                    new Intent(Notifications_CauseCode_Add_Activity.this,
                            Notifications_Event_Activity.class);
            objectpart_intent.putExtra("catelog_profile", catelog_profile);
            objectpart_intent.putExtra("request_id", Integer.toString(event_type));
            startActivityForResult(objectpart_intent, event_type);
        } else if (v == eventcode_button) {
            if (event_id != null && !event_id.equals("")) {
                Intent objectpart_intent =
                        new Intent(Notifications_CauseCode_Add_Activity.this,
                                Notifications_Event_Code_Activity.class);
                objectpart_intent.putExtra("event_id", event_id);
                objectpart_intent.putExtra("catelog_profile", catelog_profile);
                objectpart_intent.putExtra("request_id", Integer.toString(eventcode_type));
                startActivityForResult(objectpart_intent, eventcode_type);
            } else {
                error_dialog.show_error_dialog(Notifications_CauseCode_Add_Activity.this,
                        getString(R.string.notifcause_selectdamage));
            }
        } else if (v == cause_button) {
            if (eventcode_id != null && !eventcode_id.equals("")) {
                Intent objectpart_intent =
                        new Intent(Notifications_CauseCode_Add_Activity.this,
                                Notifications_Cause_Activity.class);
                objectpart_intent.putExtra("catelog_profile", catelog_profile);
                objectpart_intent.putExtra("request_id", Integer.toString(cause_type));
                startActivityForResult(objectpart_intent, cause_type);
            } else {
                error_dialog.show_error_dialog(Notifications_CauseCode_Add_Activity.this,
                        getString(R.string.notifcause_selectdamagecode));
            }
        } else if (v == causecode_button) {
            if (cause_id != null && !cause_id.equals("")) {
                Intent objectpart_intent =
                        new Intent(Notifications_CauseCode_Add_Activity.this,
                                Notifications_Cause_Code_Activity.class);
                objectpart_intent.putExtra("cause_id", cause_id);
                objectpart_intent.putExtra("catelog_profile", catelog_profile);
                objectpart_intent.putExtra("request_id", Integer.toString(causecode_type));
                startActivityForResult(objectpart_intent, causecode_type);
            } else {
                error_dialog.show_error_dialog(Notifications_CauseCode_Add_Activity.this,
                        getString(R.string.notifcause_selectcause));
            }
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == objectpart_type) {
                objectpart_id = data.getStringExtra("objectpart_id");
                objectpart_text = data.getStringExtra("objectpart_text");
                objpart_button.setText(objectpart_id + " - " + objectpart_text);
                objectpartcode_id = "";
                objectpartcode_text = "";
                objpartcode_button.setText("");
            } else if (requestCode == objectpartcode_type) {
                objectpartcode_id = data.getStringExtra("objectpartcode_id");
                objectpartcode_text = data.getStringExtra("objectpartcode_text");
                objpartcode_button.setText(objectpartcode_id + " - " + objectpartcode_text);
            } else if (requestCode == event_type) {
                event_id = data.getStringExtra("event_id");
                event_text = data.getStringExtra("event_text");
                event_button.setText(event_id + " - " + event_text);
                eventcode_id = "";
                eventcode_text = "";
                eventcode_button.setText("");
            } else if (requestCode == eventcode_type) {
                eventcode_id = data.getStringExtra("eventcode_id");
                eventcode_text = data.getStringExtra("eventcode_text");
                eventcode_button.setText(eventcode_id + " - " + eventcode_text);
            } else if (requestCode == cause_type) {
                cause_id = data.getStringExtra("cause_id");
                cause_text = data.getStringExtra("cause_text");
                cause_button.setText(cause_id + " - " + cause_text);
                causecode_id = "";
                causecode_text = "";
                causecode_button.setText("");
            } else if (requestCode == causecode_type) {
                causecode_id = data.getStringExtra("causecode_id");
                causecode_text = data.getStringExtra("causecode_text");
                causecode_button.setText(causecode_id + " - " + causecode_text);
            }
        }
    }
}
