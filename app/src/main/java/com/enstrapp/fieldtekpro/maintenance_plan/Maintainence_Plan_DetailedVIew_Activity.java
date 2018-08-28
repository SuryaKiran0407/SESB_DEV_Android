package com.enstrapp.fieldtekpro.maintenance_plan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

public class Maintainence_Plan_DetailedVIew_Activity extends Activity implements OnClickListener
{
	
	TextView function_location_desc_textview, equipment_short_text_textview, function_location_name_textview, Plantext_textview, Plnal_textview, Plnnr_textview, Plnty_textview, request_date_textview, main_item_type_textview, start_date_textview, order_number_textview, notification_number_textview, work_center_textview, plant_textview, maintaince_plan_type_textview, Tplnr_textview, equipment_textview, maintaince_plan_textview, call_number_textview, Wptxt_textview, maintaince_item_textview, Strat_textview;
	ImageView back_imageview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
		setContentView(R.layout.maintainence_plan_detailedview_activity);
		
		back_imageview = (ImageView)findViewById(R.id.back_imageview);
		call_number_textview = (TextView)findViewById(R.id.call_number_textview);
		maintaince_plan_textview = (TextView)findViewById(R.id.maintaince_plan_textview);
		Wptxt_textview = (TextView)findViewById(R.id.Wptxt_textview);
		maintaince_item_textview = (TextView)findViewById(R.id.maintaince_item_textview);
		Strat_textview = (TextView)findViewById(R.id.Strat_textview);
		equipment_textview = (TextView)findViewById(R.id.equipment_textview);
		Tplnr_textview = (TextView)findViewById(R.id.Tplnr_textview);
		maintaince_plan_type_textview = (TextView)findViewById(R.id.maintaince_plan_type_textview);
		plant_textview = (TextView)findViewById(R.id.plant_textview);
		work_center_textview = (TextView)findViewById(R.id.work_center_textview);
		notification_number_textview = (TextView)findViewById(R.id.notification_number_textview);
		order_number_textview = (TextView)findViewById(R.id.order_number_textview);
		start_date_textview = (TextView)findViewById(R.id.start_date_textview);
		main_item_type_textview = (TextView)findViewById(R.id.main_item_type_textview);
//		request_date_textview = (TextView)findViewById(R.id.request_date_textview);
		Plnty_textview = (TextView)findViewById(R.id.Plnty_textview);
		Plnnr_textview = (TextView)findViewById(R.id.Plnnr_textview);
		Plnal_textview = (TextView)findViewById(R.id.Plnal_textview);
		Plantext_textview = (TextView)findViewById(R.id.Plantext_textview);
		function_location_name_textview = (TextView)findViewById(R.id.function_location_name_textview);
		equipment_short_text_textview = (TextView)findViewById(R.id.equipment_short_text_textview);
		function_location_desc_textview = (TextView)findViewById(R.id.function_location_desc_textview);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) 
		{
			call_number_textview.setText(extras.getString("Abnum"));
			maintaince_plan_textview.setText(extras.getString("Warpl"));
			start_date_textview.setText(extras.getString("Gstrp"));
			order_number_textview.setText(extras.getString("Aufnr"));
			notification_number_textview.setText(extras.getString("Qmnum"));
			work_center_textview.setText(extras.getString("Arbpl"));
			plant_textview.setText(extras.getString("Iwerk"));
			Wptxt_textview.setText(extras.getString("Wptxt"));
			maintaince_item_textview.setText(extras.getString("Wapos"));
			Strat_textview.setText(extras.getString("Strat"));
			equipment_textview.setText(extras.getString("Equnr"));
			Tplnr_textview.setText(extras.getString("Tplnr"));
			maintaince_plan_type_textview.setText(extras.getString("Mptyp"));
			main_item_type_textview.setText(extras.getString("Mityp"));
//			request_date_textview.setText(extras.getString("Addat"));
			Plnty_textview.setText(extras.getString("Plnty"));
			Plnnr_textview.setText(extras.getString("Plnnr"));
			Plnal_textview.setText(extras.getString("Plnal"));
			Plantext_textview.setText(extras.getString("Plantext"));
			function_location_name_textview.setText(extras.getString("Strno"));
			equipment_short_text_textview.setText(extras.getString("Eqktx"));
			function_location_desc_textview.setText(extras.getString("Pltxt"));
		}
		
		back_imageview.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if(v == back_imageview)
		{
			overridePendingTransition(0, 0);
			onBackPressed();
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		overridePendingTransition(0, 0);
		super.onBackPressed();
	}
	
}
