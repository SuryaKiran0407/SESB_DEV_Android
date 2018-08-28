package com.enstrapp.fieldtekpro.Initialload;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.enstrapp.fieldtekpro.DashBoard.DashBoard_Activity;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

public class InitialLoad_Activity extends AppCompatActivity
{

    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    String Calibration_Status = "", JRA_status = "", DeviceToken_status = "", Measurementpoint_status = "",  Auth_status = "", BOM_status = "", FLOC_Status = "", DORD_Status = "", DNOT_Status = "", VHLP_WCM_Status = "", VHLP_Status = "", From = "", transmit_type = "LOAD", Syncmap_status = "", LoadSettings_status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_load_activity);

        /* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
		/* Initializing Shared Preferences */

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            From = extras.getString("From");
        }

        if (From != null && !From.equals(""))
        {
            progressDialog.show_progress_dialog(InitialLoad_Activity.this,getResources().getString(R.string.fetching_data));
            transmit_type = "LOAD";
        }
        else
        {
            progressDialog.show_progress_dialog(InitialLoad_Activity.this,getResources().getString(R.string.refreshing_data));
            transmit_type = "REFR";
        }

        new Get_Syncmap_Data().execute();

    }


    public class Get_Syncmap_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Syncmap_status = SyncMap.Get_Syncmap_Data(InitialLoad_Activity.this,transmit_type);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_Syncmap_Status",Syncmap_status+"...");
            if(Syncmap_status.equalsIgnoreCase("success"))
            {
                new Get_LoadSettings_Data().execute();
            }
            else
            {
            }
        }
    }


    public class Get_LoadSettings_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                LoadSettings_status = LoadSettings.Get_LoadSettings_Data(InitialLoad_Activity.this,transmit_type);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_LoadSettings_status",LoadSettings_status+"...");
            if(LoadSettings_status.equalsIgnoreCase("success"))
            {
                new Get_VHLP_Data().execute();
            }
            else
            {
            }
        }
    }


    public class Get_VHLP_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String vhlp_load_status = FieldTekPro_SharedPref.getString("Vhlp_Load_status",null);
                if(vhlp_load_status.equalsIgnoreCase("X"))
                {
                    VHLP_Status = VHLP.Get_VHLP_Data(InitialLoad_Activity.this,transmit_type);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_VHLP_Status",VHLP_Status+"...");
            new Get_VHLP_WCM_Data().execute();
        }
    }


    public class Get_VHLP_WCM_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String vhlp_load_status = FieldTekPro_SharedPref.getString("Vhlp_Load_status",null);
                if(vhlp_load_status.equalsIgnoreCase("X"))
                {
                    VHLP_WCM_Status = VHLP_WCM.Get_VHLP_WCM_Data(InitialLoad_Activity.this,transmit_type);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_VHLP_WCM_Status",VHLP_WCM_Status+"...");
            new Get_DORD_Data().execute();
        }
    }


    public class Get_DORD_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String dord_load_status = FieldTekPro_SharedPref.getString("Dord_Load_status",null);
                if(dord_load_status.equalsIgnoreCase("X"))
                {
                    DORD_Status = Orders.Get_DORD_Data(InitialLoad_Activity.this,transmit_type,"");
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_DORD_Status",DORD_Status+"...");
            new Get_DNOT_Data().execute();
        }
    }


    public class Get_DNOT_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String dnot_load_status = FieldTekPro_SharedPref.getString("Dnot_Load_status",null);
                if(dnot_load_status.equalsIgnoreCase("X"))
                {
                    DNOT_Status = Notifications.Get_DNOT_Data(InitialLoad_Activity.this,transmit_type);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_DNOT_Status",DNOT_Status+"...");
            new Get_BOM_Data().execute();
        }
    }


    public class Get_BOM_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String Ebom_Load_status = FieldTekPro_SharedPref.getString("Ebom_Load_status",null);
                if(Ebom_Load_status.equalsIgnoreCase("X"))
                {
                    BOM_status = BOM.Get_BOM_Data(InitialLoad_Activity.this,transmit_type,"");
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_BOM_Status",BOM_status+"...");
            new Get_FLOC_Data().execute();
        }
    }


    public class Get_FLOC_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String Floc_Load_status = FieldTekPro_SharedPref.getString("Floc_Load_status",null);
                if(Floc_Load_status.equalsIgnoreCase("X"))
                {
                    FLOC_Status = FLOC.Get_FLOC_Data(InitialLoad_Activity.this,transmit_type);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_FLOC_Status",FLOC_Status+"...");
            new Get_Auth_Data().execute();
        }
    }


    public class Get_Auth_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String Auth_Load_status = FieldTekPro_SharedPref.getString("Auth_Load_status",null);
                if(Auth_Load_status.equalsIgnoreCase("X"))
                {
                    Auth_status = Auth.Get_Auth_Data(InitialLoad_Activity.this,transmit_type);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_Auth_Status",Auth_status+"...");
            new Get_Measurepoints_Data().execute();
        }
    }


    public class Get_Measurepoints_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Measurementpoint_status = MeasurementPoint.Get_Mpoint_Data(InitialLoad_Activity.this,transmit_type);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_mPoint_Status",Measurementpoint_status+"...");
            new Set_DeviceToken_Data().execute();
        }
    }


    public class Set_DeviceToken_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                DeviceToken_status = DeviceToken.set_devicetoken_data(InitialLoad_Activity.this);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_DeviceToken_status",DeviceToken_status+"...");
            new Get_Calibration_Data().execute();
        }
    }


    public class Get_Calibration_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Calibration_Status = Calibration.Get_Calibration_Data(InitialLoad_Activity.this, transmit_type);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_Calibration_status",Calibration_Status+"...");
            FieldTekPro_SharedPrefeditor.putString("App_Login_Status", "LoggedIn");
            FieldTekPro_SharedPrefeditor.commit();
            InitialLoad_Activity.this.finish();
            progressDialog.dismiss_progress_dialog();
            Intent dashboard_intent = new Intent(InitialLoad_Activity.this, DashBoard_Activity.class);
            startActivity(dashboard_intent);
        }
    }


    public class Get_JRA_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String vhlp_load_status = FieldTekPro_SharedPref.getString("Vhlp_Load_status",null);
                if(vhlp_load_status.equalsIgnoreCase("X"))
                {
                    JRA_status = JSA.Get_JRA_Data(InitialLoad_Activity.this);
                }

            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Log.v("kiran_JRA_status_status",JRA_status+"...");
            FieldTekPro_SharedPrefeditor.putString("App_Login_Status", "LoggedIn");
            FieldTekPro_SharedPrefeditor.commit();
            InitialLoad_Activity.this.finish();
            progressDialog.dismiss_progress_dialog();
            Intent dashboard_intent = new Intent(InitialLoad_Activity.this, DashBoard_Activity.class);
            startActivity(dashboard_intent);
        }
    }


    /* Disable Back Button in the hardware */
    @Override
    public void onBackPressed()
    {
    }
	/* Disable Back Button in the hardware */

}
