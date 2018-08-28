package com.enstrapp.fieldtekpro.maintenance_plan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Maintenance_Plan_Activity extends AppCompatActivity
{

    RecyclerView recyclerview;
    SwipeRefreshLayout swiperefreshlayout;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    String password = "", username = "", device_serial_number = "", device_id = "", device_uuid = "";
    SharedPreferences app_sharedpreferences;
    SharedPreferences.Editor app_editor;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private List<MaintenancePlan_List_Object> maintenanceplan_list = new ArrayList<>();
    TextView no_data_textView;
    MaintenancePlan_Adapter maintenancePlan_adapter;
    int response_status_code = 0;
    Response<MaintenancePlan_SER> response;
    ImageView back_imageview;
    Dialog decision_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenanceplan_list_activity);

        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        no_data_textView = (TextView) findViewById(R.id.no_data_textview);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);

        DATABASE_NAME = Maintenance_Plan_Activity.this.getString(R.string.database_name);
        App_db = Maintenance_Plan_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            new Get_MaintenancePlan_Data().execute();
        }
        else
        {
            //showing network error and navigating to wifi settings.
            network_connection_dialog.show_network_connection_dialog(Maintenance_Plan_Activity.this);
        }

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Refresh_Data();
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red,R.color.lime,R.color.colorAccent,R.color.red,R.color.blue,R.color.black,R.color.orange);

        back_imageview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Maintenance_Plan_Activity.this.finish();
            }
        });

    }


    public void Refresh_Data()
    {
        cd = new ConnectionDetector(Maintenance_Plan_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            decision_dialog = new Dialog(Maintenance_Plan_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView)decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Maintenance_Plan_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
            Button confirm = (Button)decision_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button)decision_dialog.findViewById(R.id.no_button);
            description_textview.setText("All Relavent Data will be loaded from server. Do you want to continue?");
            decision_dialog.show();
            cancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    decision_dialog.dismiss();
                    swiperefreshlayout.setRefreshing(false);
                }
            });
            confirm.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    decision_dialog.dismiss();
                    swiperefreshlayout.setRefreshing(false);
                    new Get_MaintenancePlan_Data().execute();
                }
            });
        }
        else
        {
            //showing network error and navigating to wifi settings.
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(Maintenance_Plan_Activity.this);
        }
    }


    public class Get_MaintenancePlan_Data extends AsyncTask<Void, Integer, Void>
    {
        String url_link = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Maintenance_Plan_Activity.this,getResources().getString(R.string.loading_maintenanceplan));
            maintenanceplan_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                /* Initializing Shared Preferences */
                app_sharedpreferences = Maintenance_Plan_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username",null);
                password = app_sharedpreferences.getString("Password",null);
                String webservice_type = app_sharedpreferences.getString("webservice_type",null);
                /* Initializing Shared Preferences */
                 /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                device_id = Settings.Secure.getString(Maintenance_Plan_Activity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                device_serial_number = Build.SERIAL;
                String androidId = ""+ Settings.Secure.getString(Maintenance_Plan_Activity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32)| device_serial_number.hashCode());
                device_uuid = deviceUuid.toString();
		        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"C3", "MS", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Maintenance_Plan_Activity.this.getString(R.string.ip_address);
                Map<String, String> map = new HashMap<>();
                map.put("IvUser", username);
                map.put("Muser", username.toUpperCase().toString());
                map.put("Deviceid", device_id);
                map.put("Devicesno", device_serial_number);
                map.put("Udid", device_uuid);
                map.put("IvTransmitType", "LOAD");
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<MaintenancePlan_SER> call = service.getMaintenancePlanData(url_link, basic, map);
                response = call.execute();
                response_status_code = response.code();
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
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    try
                    {
                        /*Reading Response Data and Parsing to Serializable*/
                        MaintenancePlan_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String response_data = new Gson().toJson(rs.getD().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                         /*Reading Data by using FOR Loop*/
                        if(response_data_jsonArray.length() > 0)
                        {
                            for(int i = 0; i < response_data_jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
                                String date = jsonObject.optString("Gstrp");
                                String Gstrpdate_formateed = "";
                                if (date != null && !date.equals(""))
                                {
                                    if (date.equals("00000000"))
                                    {
                                        Gstrpdate_formateed = "";
                                    }
                                    else
                                    {
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                        Date date1;
                                        try
                                        {
                                            date1 = inputFormat.parse(date);
                                            String outputDateStr = outputFormat.format(date1);
                                            Gstrpdate_formateed = outputDateStr;
                                        }
                                        catch (Exception e)
                                        {
                                            Gstrpdate_formateed = "";
                                        }
                                    }
                                }
                                else
                                {
                                    Gstrpdate_formateed = "";
                                }
                                String Addat_date = jsonObject.optString("Addat");
                                String Addatdate_formateed = "";
                                if (Addat_date != null && !Addat_date.equals(""))
                                {
                                    if (Addat_date.equals("00000000"))
                                    {
                                        Addatdate_formateed = "";
                                    }
                                    else
                                    {
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                        Date date1;
                                        try
                                        {
                                            date1 = inputFormat.parse(Addat_date);
                                            String outputDateStr = outputFormat.format(date1);
                                            Addatdate_formateed = outputDateStr;
                                        }
                                        catch (Exception e)
                                        {
                                            Addatdate_formateed = "";
                                        }
                                    }
                                }
                                else
                                {
                                    Addatdate_formateed = "";
                                }
                                MaintenancePlan_List_Object olo = new MaintenancePlan_List_Object(jsonObject.optString("Abnum"), jsonObject.optString("Warpl"), jsonObject.optString("Wptxt"), jsonObject.optString("Wapos"), jsonObject.optString("Pstxt"), jsonObject.optString("Strat"), jsonObject.optString("Equnr"), jsonObject.optString("Mptyp"), jsonObject.optString("Iwerk"), jsonObject.optString("Gewrk"), jsonObject.optString("Arbpl"), jsonObject.optString("Qmnum"), jsonObject.optString("Aufnr"), Gstrpdate_formateed, Addatdate_formateed, jsonObject.optString("Status"), jsonObject.optString("Mityp"), jsonObject.optString("Strno"), jsonObject.optString("Eqktx"), jsonObject.optString("Pltxt"));
                                maintenanceplan_list.add(olo);
                            }
                            if(maintenanceplan_list.size() > 0)
                            {
                                maintenancePlan_adapter = new MaintenancePlan_Adapter(Maintenance_Plan_Activity.this, maintenanceplan_list);
                                recyclerview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Maintenance_Plan_Activity.this);
                                recyclerview.setLayoutManager(layoutManager);
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(maintenancePlan_adapter);
                                no_data_textView.setVisibility(View.GONE);
                                swiperefreshlayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss_progress_dialog();
                            }
                            else
                            {
                                no_data_textView.setVisibility(View.VISIBLE);
                                swiperefreshlayout.setVisibility(View.GONE);
                                progressDialog.dismiss_progress_dialog();
                            }
                        }
                        else
                        {
                            no_data_textView.setVisibility(View.VISIBLE);
                            swiperefreshlayout.setVisibility(View.GONE);
                            progressDialog.dismiss_progress_dialog();
                        }
                        /*Reading Data by using FOR Loop*/
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            else
            {
                no_data_textView.setVisibility(View.VISIBLE);
                swiperefreshlayout.setVisibility(View.GONE);
                progressDialog.dismiss_progress_dialog();
            }
        }
    }



    public class MaintenancePlan_Adapter extends RecyclerView.Adapter<MaintenancePlan_Adapter.MyViewHolder>
    {
        private Context mContext;
        private List<MaintenancePlan_List_Object> maintenancePlan_list_data;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView Wptxt_textview, aufnr_textview, equipment_textview, maintaince_plan_textview, maintaince_item_textview;
            protected TextView Strat_textview;
            protected TextView Mptyp_textview;
            protected TextView Iwerk_textview;
            protected TextView Arbpl_textview;
            protected TextView Qmnum_textview;
            protected TextView Mityp_textview;
            protected TextView Addat_textview;
            protected TextView Strno_textview;
            protected TextView Eqktx_textview;
            protected TextView Pltxt_textview;
            protected LinearLayout maintain_plan_layout;
            public MyViewHolder(View view)
            {
                super(view);
                aufnr_textview = (TextView) view.findViewById(R.id.aufnr_textview);
                equipment_textview = (TextView) view.findViewById(R.id.equipment_textview);
                maintaince_plan_textview = (TextView) view.findViewById(R.id.maintaince_plan_textview);
                maintaince_item_textview = (TextView) view.findViewById(R.id.maintaince_item_textview);
                Wptxt_textview = (TextView) view.findViewById(R.id.Wptxt_textview);
                Strat_textview = (TextView) view.findViewById(R.id.Strat_textview);
                Mptyp_textview = (TextView) view.findViewById(R.id.Mptyp_textview);
                Iwerk_textview = (TextView) view.findViewById(R.id.Iwerk_textview);
                Arbpl_textview = (TextView) view.findViewById(R.id.Arbpl_textview);
                Qmnum_textview = (TextView) view.findViewById(R.id.Qmnum_textview);
                Mityp_textview = (TextView) view.findViewById(R.id.Mityp_textview);
                Addat_textview = (TextView) view.findViewById(R.id.Addat_textview);
                Strno_textview = (TextView) view.findViewById(R.id.Strno_textview);
                Eqktx_textview = (TextView) view.findViewById(R.id.Eqktx_textview);
                Pltxt_textview = (TextView) view.findViewById(R.id.Pltxt_textview);
                maintain_plan_layout = (LinearLayout) view.findViewById(R.id.maintain_plan_layout);
            }
        }
        public MaintenancePlan_Adapter(Context mContext, List<MaintenancePlan_List_Object> list)
        {
            this.mContext = mContext;
            this.maintenancePlan_list_data = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintainance_plan_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position)
        {
            final MaintenancePlan_List_Object olo = maintenancePlan_list_data.get(position);
            holder.aufnr_textview.setText(olo.getAufnr());
            holder.equipment_textview.setText(olo.getEqunr());
            holder.maintaince_plan_textview.setText(olo.getWarpl());
            holder.maintaince_item_textview.setText(olo.getWapos()+" - "+olo.getPstxt());
            holder.Wptxt_textview.setText(olo.getWptxt());
            holder.Strat_textview.setText(olo.getStrat());
            holder.Mptyp_textview.setText(olo.getMptyp());
            holder.Iwerk_textview.setText(olo.getIwerk());
            holder.Arbpl_textview.setText(olo.getArbpl());
            holder.Mityp_textview.setText(olo.getMityp());
            holder.Addat_textview.setText(olo.getAddat());
            holder.Strno_textview.setText(olo.getStrno());
            holder.Eqktx_textview.setText(olo.getEqktx());
            holder.Pltxt_textview.setText(olo.getPltxt());
            holder.Qmnum_textview.setText(olo.getQmnum());
            holder.maintain_plan_layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent maintainence_plan_intent = new Intent(Maintenance_Plan_Activity.this,Maintainence_Plan_DetailedVIew_Activity.class);
                    maintainence_plan_intent.putExtra("Abnum", holder.aufnr_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Warpl", holder.maintaince_plan_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Wptxt", holder.Wptxt_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Wapos", holder.maintaince_item_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Strat", holder.Strat_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Equnr", holder.equipment_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Mptyp", holder.Mptyp_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Iwerk", holder.Iwerk_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Arbpl", holder.Arbpl_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Qmnum", holder.Qmnum_textview.getText().toString());
                    //maintainence_plan_intent.putExtra("Gstrp", holder.start_date_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Mityp", holder.Mityp_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Addat", holder.Addat_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Strno", holder.Strno_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Eqktx", holder.Eqktx_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Pltxt", holder.Pltxt_textview.getText().toString());
                    maintainence_plan_intent.putExtra("Aufnr", holder.aufnr_textview.getText().toString());
                    startActivity(maintainence_plan_intent);
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return maintenancePlan_list_data.size();
        }
    }


    public class MaintenancePlan_List_Object
    {
        private String abnum;
        private String warpl;
        private String wptxt;
        private String wapos;
        private String pstxt;
        private String strat;
        private String equnr;
        private String mptyp;
        private String iwerk;
        private String gewrk;
        private String arbpl;
        private String qmnum;
        private String aufnr;
        private String gstrp;
        private String addat;
        private String status;
        private String mityp;
        private String strno;
        private String eqktx;
        private String pltxt;
        public String getAbnum() {
            return abnum;
        }
        public void setAbnum(String abnum) {
            this.abnum = abnum;
        }
        public String getWarpl() {
            return warpl;
        }
        public void setWarpl(String warpl) {
            this.warpl = warpl;
        }
        public String getWptxt() {
            return wptxt;
        }
        public void setWptxt(String wptxt) {
            this.wptxt = wptxt;
        }
        public String getWapos() {
            return wapos;
        }
        public void setWapos(String wapos) {
            this.wapos = wapos;
        }
        public String getPstxt() {
            return pstxt;
        }
        public void setPstxt(String pstxt) {
            this.pstxt = pstxt;
        }
        public String getStrat() {
            return strat;
        }
        public void setStrat(String strat) {
            this.strat = strat;
        }
        public String getEqunr() {
            return equnr;
        }
        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }
        public String getMptyp() {
            return mptyp;
        }
        public void setMptyp(String mptyp) {
            this.mptyp = mptyp;
        }
        public String getIwerk() {
            return iwerk;
        }
        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }
        public String getGewrk() {
            return gewrk;
        }
        public void setGewrk(String gewrk) {
            this.gewrk = gewrk;
        }
        public String getArbpl() {
            return arbpl;
        }
        public void setArbpl(String arbpl) {
            this.arbpl = arbpl;
        }
        public String getQmnum() {
            return qmnum;
        }
        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }
        public String getAufnr() {
            return aufnr;
        }
        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }
        public String getGstrp() {
            return gstrp;
        }
        public void setGstrp(String gstrp) {
            this.gstrp = gstrp;
        }
        public String getAddat() {
            return addat;
        }
        public void setAddat(String addat) {
            this.addat = addat;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getMityp() {
            return mityp;
        }
        public void setMityp(String mityp) {
            this.mityp = mityp;
        }
        public String getStrno() {
            return strno;
        }
        public void setStrno(String strno) {
            this.strno = strno;
        }
        public String getEqktx() {
            return eqktx;
        }
        public void setEqktx(String eqktx) {
            this.eqktx = eqktx;
        }
        public String getPltxt() {
            return pltxt;
        }
        public void setPltxt(String pltxt) {
            this.pltxt = pltxt;
        }
        public MaintenancePlan_List_Object(String abnum, String warpl, String wptxt, String wapos, String pstxt, String strat, String equnr, String mptyp, String iwerk, String gewrk, String arbpl, String qmnum, String aufnr, String gstrp, String addat, String status, String mityp, String strno, String eqktx, String pltxt)
        {
            this.abnum = abnum;
            this.warpl = warpl;
            this.wptxt = wptxt;
            this.wapos = wapos;
            this.pstxt = pstxt;
            this.strat = strat;
            this.equnr = equnr;
            this.mptyp = mptyp;
            this.iwerk = iwerk;
            this.gewrk = gewrk;
            this.arbpl = arbpl;
            this.qmnum = qmnum;
            this.aufnr = aufnr;
            this.gstrp = gstrp;
            this.addat = addat;
            this.status = status;
            this.mityp = mityp;
            this.strno = strno;
            this.eqktx = eqktx;
            this.pltxt = pltxt;
        }
    }


}
