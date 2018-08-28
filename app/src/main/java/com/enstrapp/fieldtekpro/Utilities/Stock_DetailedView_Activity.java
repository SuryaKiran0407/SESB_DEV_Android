package com.enstrapp.fieldtekpro.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

public class Stock_DetailedView_Activity extends Activity implements OnClickListener
{
	 TextView heading_textview,setLgort_textview,setLabst_textview,setSpeme_textview,setLgpbe_textview;
	 Button submit_reserve_button;
     TextView stock_desc_textview,plant_textview;
     ImageView back_imageview;
	 private SQLiteDatabase FieldTekPro_db;
	 private static String DATABASE_NAME = "";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_detailedview_activity);

		heading_textview = (TextView) findViewById(R.id.heading_textview);
		setLgort_textview = (TextView) findViewById(R.id.setLgort_textview);
		setLabst_textview = (TextView) findViewById(R.id.setLabst_textview);
		setSpeme_textview = (TextView) findViewById(R.id.setSpeme_textview);
		setLgpbe_textview = (TextView) findViewById(R.id.setLgpbe_textview);
		submit_reserve_button =(Button) findViewById(R.id.submit_reserve_button);
		stock_desc_textview = (TextView) findViewById(R.id.stock_desc_textview);
		plant_textview = (TextView) findViewById(R.id.plant_textview);
		back_imageview = (ImageView) findViewById(R.id.back_imageview);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			heading_textview.setText(extras.getString("Matnr"));
			stock_desc_textview.setText(extras.getString("Maktx"));
			plant_textview.setText(extras.getString("Werks"));
		    setLgort_textview.setText(extras.getString("Lgort"));
			setLabst_textview.setText(extras.getString("Labst"));
			setSpeme_textview.setText(extras.getString("Speme"));
			setLgpbe_textview.setText(extras.getString("Lgpbe"));
		}

		back_imageview.setOnClickListener(this);
		submit_reserve_button.setOnClickListener(this);

		DATABASE_NAME = getString(R.string.database_name);
		FieldTekPro_db = getApplicationContext().openOrCreateDatabase(DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);

	}

	@Override
	public void onClick(View v)
	{
			if (v == back_imageview)
			{
				Stock_DetailedView_Activity.this.finish();
			}
		    else if(v == submit_reserve_button)
			{
				Intent stock_reservtaion_intent = new Intent(Stock_DetailedView_Activity.this,BOM_Reservation_Activity.class);
				stock_reservtaion_intent.putExtra("Components",heading_textview.getText().toString());
				stock_reservtaion_intent.putExtra("Components_text",stock_desc_textview.getText().toString());
				stock_reservtaion_intent.putExtra("Plant",plant_textview.getText().toString());
				stock_reservtaion_intent.putExtra("Lgort",setLgort_textview.getText().toString());
				stock_reservtaion_intent.putExtra("Labst",setLabst_textview.getText().toString());
				stock_reservtaion_intent.putExtra("Speme",setSpeme_textview.getText().toString());
				stock_reservtaion_intent.putExtra("Lgpbe",setLgpbe_textview.getText().toString());
				startActivity(stock_reservtaion_intent);
			}
		}
	
}
