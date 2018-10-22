package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

public class Order_LongText_Activity extends Activity implements View.OnClickListener
{

    Button send_button;
    EditText message_edittext;
    TextView textView;
    ImageView back_imageview;
    String longtext_new = "", aufnr = "", operation_id = "", tdid = "";
    Error_Dialog error_dialog = new Error_Dialog();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_longtext_activity);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        send_button = (Button)findViewById(R.id.send_button);
        message_edittext = (EditText)findViewById(R.id.message_edittext);
        textView = (TextView)findViewById(R.id.textView);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            longtext_new = extras.getString("longtext_new");
            aufnr = extras.getString("aufnr");
            operation_id = extras.getString("operation_id");
            tdid = extras.getString("tdid");
            if (tdid != null && !tdid.equals(""))
            {
                if (aufnr != null && !aufnr.equals(""))
                {
                    StringBuilder longtext_sBuilder = new StringBuilder();
                    try
                    {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ? and Tdid = ? order by id", new String[]{aufnr, operation_id, "RMEL"});
                        if (cursor != null && cursor.getCount() > 0)
                        {
                            if (cursor.moveToFirst())
                            {
                                do
                                {
                                    longtext_sBuilder.append(cursor.getString(4));
                                    longtext_sBuilder.append("\n");
                                }
                                while (cursor.moveToNext());
                            }
                        }
                        else
                        {
                            cursor.close();
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    textView.setText(longtext_sBuilder.toString());
                }
            }
            else if (operation_id != null && !operation_id.equals(""))
            {
                if (aufnr != null && !aufnr.equals(""))
                {
                    StringBuilder longtext_sBuilder = new StringBuilder();
                    try
                    {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ? and Tdid = ? order by id", new String[]{aufnr, operation_id, ""});
                        if (cursor != null && cursor.getCount() > 0)
                        {
                            if (cursor.moveToFirst())
                            {
                                do
                                {
                                    longtext_sBuilder.append(cursor.getString(4));
                                    longtext_sBuilder.append("\n");
                                }
                                while (cursor.moveToNext());
                            }
                        }
                        else
                        {
                            cursor.close();
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    textView.setText(longtext_sBuilder.toString());
                }
            }
            else
            {
                StringBuilder longtext_sBuilder = new StringBuilder();
                try
                {
                    Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ? and Tdid = ? order by id", new String[]{aufnr, "", ""});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                longtext_sBuilder.append(cursor.getString(4));
                                longtext_sBuilder.append("\n");
                            }
                            while (cursor.moveToNext());
                        }
                    }
                    else
                    {
                        cursor.close();
                    }
                }
                catch (Exception e)
                {
                }
                textView.setText(longtext_sBuilder.toString());
            }
        }


        if (longtext_new != null && !longtext_new.equals(""))
        {
            message_edittext.setText(longtext_new);
        }
        else
        {
            message_edittext.setText("");
        }


        send_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v == send_button)
        {
            if (message_edittext.getText().toString().length() > 0)
            {
                Intent intent = new Intent();
                intent.putExtra("longtext_new",message_edittext.getText().toString());
                setResult(RESULT_OK,intent);
                Order_LongText_Activity.this.finish();
            }
            else
            {
                error_dialog.show_error_dialog(Order_LongText_Activity.this,"Please Enter Text");
            }
        }
        else if(v == back_imageview)
        {
            Order_LongText_Activity.this.finish();
        }
    }

}
