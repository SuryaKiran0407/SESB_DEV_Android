package com.enstrapp.fieldtekpro.orders;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class JSA_List_Activity extends AppCompatActivity implements View.OnClickListener
{

    TextView title_textview, no_data_textview;
    ImageView back_imageview;
    RecyclerView list_recycleview;
    LinearLayout no_data_layout;
    FloatingActionButton create_fab_button, refresh_fab_button;
    FloatingActionMenu floatingActionMenu;
    String aufnr = "", wapinr = "", iwerk = "", equipId = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    String password = "", username = "", device_serial_number = "", device_id = "", device_uuid = "";
    SharedPreferences app_sharedpreferences;
    SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    int response_status_code = 0;
    Response<JSA_List_SER> response;
    List<JSAList_Object> JSA_list = new ArrayList<>();
    JSALIST_ADAPTER adapter;
    SwipeRefreshLayout swiperefreshlayout;
    Dialog decision_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_list_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            aufnr = extras.getString("aufnr");
            wapinr = extras.getString("wapinr");
            iwerk = extras.getString("iwerk");
            equipId = extras.getString("equipId");
        }

        title_textview = (TextView)findViewById(R.id.title_textview);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);
        list_recycleview = (RecyclerView)findViewById(R.id.list_recycleview);
        no_data_layout = (LinearLayout)findViewById(R.id.no_data_layout);
        no_data_textview = (TextView)findViewById(R.id.no_data_textview);
        create_fab_button = (FloatingActionButton)findViewById(R.id.create_fab_button);
        refresh_fab_button = (FloatingActionButton)findViewById(R.id.refresh_fab_button);
        floatingActionMenu = (FloatingActionMenu)findViewById(R.id.floatingActionMenu);
        swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        DATABASE_NAME = JSA_List_Activity.this.getString(R.string.database_name);
        App_db = JSA_List_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);

        create_fab_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
        refresh_fab_button.setOnClickListener(this);

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Refresh_JSA_Data();
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red,R.color.lime,R.color.colorAccent,R.color.red,R.color.blue,R.color.black,R.color.orange);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            new Get_JSA_List_Data().execute();
        }
        else
        {
            network_connection_dialog.show_network_connection_dialog(JSA_List_Activity.this);
        }
    }


    public void Refresh_JSA_Data()
    {
        cd = new ConnectionDetector(JSA_List_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            decision_dialog = new Dialog(JSA_List_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView)decision_dialog.findViewById(R.id.description_textview);
            Glide.with(JSA_List_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
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
                    new Get_JSA_List_Data().execute();
                }
            });
        }
        else
        {
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(JSA_List_Activity.this);
        }
    }


    public class Get_JSA_List_Data extends AsyncTask<Void, Integer, Void>
    {
        String url_link = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show_progress_dialog(JSA_List_Activity.this,"Loading JSA Data...");
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                /* Initializing Shared Preferences */
                app_sharedpreferences = JSA_List_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username",null);
                password = app_sharedpreferences.getString("Password",null);
                String webservice_type = app_sharedpreferences.getString("webservice_type",null);
                /* Initializing Shared Preferences */
                 /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                device_id = Settings.Secure.getString(JSA_List_Activity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                device_serial_number = Build.SERIAL;
                String androidId = ""+ Settings.Secure.getString(JSA_List_Activity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32)| device_serial_number.hashCode());
                device_uuid = deviceUuid.toString();
		        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"D6", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = JSA_List_Activity.this.getString(R.string.ip_address);
                Map<String, String> map = new HashMap<>();
                map.put("IvUser", username);
                map.put("Muser", username.toUpperCase().toString());
                map.put("Deviceid", device_id);
                map.put("Devicesno", device_serial_number);
                map.put("Udid", device_uuid);
                map.put("IvTransmitType", "");
                map.put("ItAufnr", aufnr);
                map.put("IvRasId", "");
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<JSA_List_SER> call = service.getJSAListData(url_link, basic, map);
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
                        JSA_List_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        App_db.execSQL("delete from EtJSAHdr");
                        App_db.execSQL("delete from EtJSARisks");
                        App_db.execSQL("delete from EtJSAPer");
                        App_db.execSQL("delete from EtJSAImp");
                        App_db.execSQL("delete from EtJSARskCtrl");

                        App_db.beginTransaction();

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String EtJSAHdr_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtJSAHdr().getResults());
                        if (EtJSAHdr_response_data != null && !EtJSAHdr_response_data.equals("") && !EtJSAHdr_response_data.equals("null"))
                        {
                            JSONArray response_data_jsonArray = new JSONArray(EtJSAHdr_response_data);
                            if(response_data_jsonArray.length() > 0)
                            {
                                String sql = "Insert into EtJSAHdr (DbKey,Aufnr,Rasid,Rasstatus,Rastype,Title,Job,Opstatus,Location,Comment,Statustxt,Rastypetxt,Opstatustxt,Locationtxt,Jobtxt,Action) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for(int j = 0; j < response_data_jsonArray.length(); j++)
                                {
                                    statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("DbKey"));
                                    statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                    statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Rasid"));
                                    statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Rasstatus"));
                                    statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Rastype"));
                                    statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Title"));
                                    statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Job"));
                                    statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Opstatus"));
                                    statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Location"));
                                    statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("Comment"));
                                    statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Statustxt"));
                                    statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Rastypetxt"));
                                    statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Opstatustxt"));
                                    statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Locationtxt"));
                                    statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Jobtxt"));
                                    statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                    statement.execute();
                                }
                            }
                        }


                        String EtJSARisks_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtJSARisks().getResults());
                        if (EtJSARisks_response_data != null && !EtJSARisks_response_data.equals("") && !EtJSARisks_response_data.equals("null"))
                        {
                            JSONArray response_data_jsonArray = new JSONArray(EtJSARisks_response_data);
                            if(response_data_jsonArray.length() > 0)
                            {
                                String sql = "Insert into EtJSARisks (Aufnr,Rasid,StepID,StepSeq,RiskID,StepPers,Hazard,RiskLevel,RiskType,Evaluation,Likelihood,Severity,HazCat,Hazardtxt,HazCattxt,StepTxt,RiskLeveltxt,RiskTypetxt,Action) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for(int j = 0; j < response_data_jsonArray.length(); j++)
                                {
                                    statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                    statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Rasid"));
                                    statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("StepID"));
                                    statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("StepSeq"));
                                    statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("RiskID"));
                                    statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("StepPers"));
                                    statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Hazard"));
                                    statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("RiskLevel"));
                                    statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("RiskType"));
                                    statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("Evaluation"));
                                    statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Likelihood"));
                                    statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Severity"));
                                    statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("HazCat"));
                                    statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Hazardtxt"));
                                    statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("HazCattxt"));
                                    statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("StepTxt"));
                                    statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("RiskLeveltxt"));
                                    statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("RiskTypetxt"));
                                    statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                    statement.execute();
                                }
                            }
                        }


                        String EtJSAPer_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtJSAPer().getResults());
                        if (EtJSAPer_response_data != null && !EtJSAPer_response_data.equals("") && !EtJSAPer_response_data.equals("null"))
                        {
                            JSONArray response_data_jsonArray = new JSONArray(EtJSAPer_response_data);
                            if(response_data_jsonArray.length() > 0)
                            {
                                String sql = "Insert into EtJSAPer (Aufnr,Rasid,PersonID,Role,Roletxt,Persontxt,Action) values (?,?,?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for(int j = 0; j < response_data_jsonArray.length(); j++)
                                {
                                    statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                    statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Rasid"));
                                    statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("PersonID"));
                                    statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Role"));
                                    statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Roletxt"));
                                    statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Persontxt"));
                                    statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                    statement.execute();
                                }
                            }
                        }


                        String EtJSAImp_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtJSAImp().getResults());
                        if (EtJSAImp_response_data != null && !EtJSAImp_response_data.equals("") && !EtJSAImp_response_data.equals("null"))
                        {
                            JSONArray response_data_jsonArray = new JSONArray(EtJSAImp_response_data);
                            if(response_data_jsonArray.length() > 0)
                            {
                                String sql = "Insert into EtJSAImp (Aufnr,Rasid,StepID,RiskID,Impact,Impacttxt,Action) values (?,?,?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for(int j = 0; j < response_data_jsonArray.length(); j++)
                                {
                                    statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                    statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Rasid"));
                                    statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("StepID"));
                                    statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("RiskID"));
                                    statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Impact"));
                                    statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Impacttxt"));
                                    statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                    statement.execute();
                                }
                            }
                        }


                        String EtJSARskCtrl_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtJSARskCtrl().getResults());
                        if (EtJSARskCtrl_response_data != null && !EtJSARskCtrl_response_data.equals("") && !EtJSARskCtrl_response_data.equals("null"))
                        {
                            JSONArray response_data_jsonArray = new JSONArray(EtJSARskCtrl_response_data);
                            if(response_data_jsonArray.length() > 0)
                            {
                                String sql = "Insert into EtJSARskCtrl (Aufnr,Rasid,StepID,RiskID,StepCode,StepCompletion,Ctrlid,ControlCode,CatalogCode,ImplStatus,RespID,Type,Subject,GoalTargetCode,GoalObjectCode,GoalCtrlEffect,EffectDetDate,EffectDetCode,RefCategory,RefID,Ctrlidtxt,CatalogCodetxt,ImplStatustxt,StepCodetxt,StepCompletiontxt,Respidtxt,Action) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for(int j = 0; j < response_data_jsonArray.length(); j++)
                                {
                                    statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                    statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Rasid"));
                                    statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("StepID"));
                                    statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("RiskID"));
                                    statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("StepCode"));
                                    statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("StepCompletion"));
                                    statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Ctrlid"));
                                    statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("ControlCode"));
                                    statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("CatalogCode"));
                                    statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("ImplStatus"));
                                    statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("RespID"));
                                    statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Type"));
                                    statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Subject"));
                                    statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("GoalTargetCode"));
                                    statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("GoalObjectCode"));
                                    statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("GoalCtrlEffect"));
                                    statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("EffectDetDate"));
                                    statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("EffectDetCode"));
                                    statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("RefCategory"));
                                    statement.bindString(20, response_data_jsonArray.getJSONObject(j).optString("RefID"));
                                    statement.bindString(21, response_data_jsonArray.getJSONObject(j).optString("Ctrlidtxt"));
                                    statement.bindString(22, response_data_jsonArray.getJSONObject(j).optString("CatalogCodetxt"));
                                    statement.bindString(23, response_data_jsonArray.getJSONObject(j).optString("ImplStatustxt"));
                                    statement.bindString(24, response_data_jsonArray.getJSONObject(j).optString("StepCodetxt"));
                                    statement.bindString(25, response_data_jsonArray.getJSONObject(j).optString("StepCompletiontxt"));
                                    statement.bindString(26, response_data_jsonArray.getJSONObject(j).optString("Respidtxt"));
                                    statement.bindString(27, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                    statement.execute();
                                }
                            }
                        }
                        /*Converting GSON Response to JSON Data for Parsing*/


                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();


                        JSA_list.clear();

                        try
                        {
                            Cursor cursor = App_db.rawQuery("select * from EtJSAHdr", null);
                            if (cursor != null && cursor.getCount() > 0)
                            {
                                if (cursor.moveToFirst())
                                {
                                    do
                                    {
                                        JSAList_Object jsaListObject = new JSAList_Object(cursor.getString(3),cursor.getString(6),cursor.getString(15));
                                        JSA_list.add(jsaListObject);
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
                        if(JSA_list.size() > 0)
                        {
                            Collections.sort(JSA_list, new Comparator<JSAList_Object>()
                            {
                                public int compare(JSAList_Object o1, JSAList_Object o2)
                                {
                                    return o2.getRasid().compareTo(o1.getRasid());
                                }
                            });
                            title_textview.setText(getResources().getString(R.string.jsa)+" ("+JSA_list.size()+")");
                            adapter = new JSALIST_ADAPTER(JSA_List_Activity.this, JSA_list);
                            list_recycleview.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JSA_List_Activity.this);
                            list_recycleview.setLayoutManager(layoutManager);
                            list_recycleview.setItemAnimator(new DefaultItemAnimator());
                            list_recycleview.setAdapter(adapter);
                            list_recycleview.setVisibility(View.VISIBLE);
                            no_data_layout.setVisibility(View.GONE);
                            no_data_textview.setVisibility(View.GONE);
                        }
                        else
                        {
                            title_textview.setText(getResources().getString(R.string.jsa)+" (0)");
                            list_recycleview.setVisibility(View.GONE);
                            no_data_layout.setVisibility(View.VISIBLE);
                            no_data_textview.setVisibility(View.VISIBLE);
                        }
                        progressDialog.dismiss_progress_dialog();
                    }
                    catch (Exception e)
                    {
                        progressDialog.dismiss_progress_dialog();
                    }
                }
            }
            else
            {
                title_textview.setText(getString(R.string.jsa)+" (0)");
                no_data_layout.setVisibility(View.VISIBLE);
                progressDialog.dismiss_progress_dialog();
            }
            swiperefreshlayout.setRefreshing(false);
        }
    }


    public class JSALIST_ADAPTER extends RecyclerView.Adapter<JSALIST_ADAPTER.MyViewHolder>
    {
        private Context mContext;
        private List<JSAList_Object> type_details_list;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView ras_id_textview, title_textview, job_text_textview;
            public ImageView download_imageview;
            public LinearLayout data_layout;
            public MyViewHolder(final View view)
            {
                super(view);
                ras_id_textview = (TextView) view.findViewById(R.id.ras_id_textview);
                title_textview =  (TextView) view.findViewById(R.id.title_textview);
                job_text_textview = (TextView) view.findViewById(R.id.job_text_textview);
                download_imageview = (ImageView)view.findViewById(R.id.download_imageview);
                data_layout = (LinearLayout)view.findViewById(R.id.data_layout);
            }
        }
        public JSALIST_ADAPTER(Context mContext, List<JSAList_Object> list)
        {
            this.mContext = mContext;
            this.type_details_list = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jsa_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            final JSAList_Object nto = type_details_list.get(position);
            holder.ras_id_textview.setText(nto.getRasid());
            holder.title_textview.setText(nto.getTitle());
            holder.job_text_textview.setText(nto.getJobtxt());
            holder.download_imageview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    cd = new ConnectionDetector(getApplicationContext());
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent)
                    {
                        final Dialog decision_dialog = new Dialog(JSA_List_Activity.this);
                        decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        decision_dialog.setCancelable(false);
                        decision_dialog.setCanceledOnTouchOutside(false);
                        decision_dialog.setContentView(R.layout.decision_dialog);
                        ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
                        TextView description_textview = (TextView)decision_dialog.findViewById(R.id.description_textview);
                        Glide.with(JSA_List_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
                        Button confirm = (Button)decision_dialog.findViewById(R.id.yes_button);
                        Button cancel = (Button)decision_dialog.findViewById(R.id.no_button);
                        description_textview.setText("Do you want to download the PDF for selected JSA ID "+holder.ras_id_textview.getText().toString()+"?");
                        decision_dialog.show();
                        cancel.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                decision_dialog.dismiss();
                            }
                        });
                        confirm.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                decision_dialog.dismiss();
                                new Get_JSA_Download_Data().execute(holder.ras_id_textview.getText().toString());
                            }
                        });
                        decision_dialog.show();
                    }
                    else
                    {
                        network_connection_dialog.show_network_connection_dialog(JSA_List_Activity.this);
                    }
                }
            });
            holder.data_layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(JSA_List_Activity.this,JSA_Change_Activity.class);
                    intent.putExtra("rasid",holder.ras_id_textview.getText().toString());
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return type_details_list.size();
        }
    }


    private class Get_JSA_Download_Data extends AsyncTask<String, Integer, Void>
    {
        String ras_id = "", file_path = "", url_link = "", fileName = "", fileExtension = ".pdf";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show_progress_dialog(JSA_List_Activity.this,getResources().getString(R.string.downloading_pleasewait));
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                ras_id = params[0];
                 /* Initializing Shared Preferences */
                app_sharedpreferences = JSA_List_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username",null);
                password = app_sharedpreferences.getString("Password",null);
                String webservice_type = app_sharedpreferences.getString("webservice_type",null);
                /* Initializing Shared Preferences */
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"D6", "PS", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = JSA_List_Activity.this.getString(R.string.ip_address)+url_link;
                URL url = new URL(URL);
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                conection.setRequestProperty ("Authorization", basic);
                conection.setRequestProperty("Muser", username.toUpperCase().toString());
                conection.setRequestProperty("IvUser", username);
                conection.setRequestProperty("Deviceid", device_id);
                conection.setRequestProperty("Devicesno", device_serial_number);
                conection.setRequestProperty("Udid", device_uuid);
                conection.setRequestProperty("IvTransmitType", "");
                conection.setRequestProperty("ivaufnr", aufnr);
                conection.setRequestProperty("ivrasid", ras_id);
                conection.connect();
                InputStream inputStream = conection.getInputStream();

                fileName = aufnr+"_"+ras_id;
                fileExtension=".pdf";
                String PATH = Environment.getExternalStorageDirectory() + "/FTekP CGPL/JSA/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, fileName+fileExtension);
                file_path = outputFile.getPath();

                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                int  MEGABYTE = 1024 * 1024;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0)
                {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
                inputStream.close();
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            File file = new File(file_path);
            App_db.execSQL("delete from Orders_Attachments where Object_id = ? and jsa_id = ?", new String[]{wapinr,ras_id});
            App_db.beginTransaction();
            UUID uniqueKey_uuid = UUID.randomUUID();
            String sql11 = "Insert into Orders_Attachments (UUID, Object_id, Object_type, file_path, jsa_id) values(?,?,?,?,?);";
            SQLiteStatement statement11 = App_db.compileStatement(sql11);
            statement11.clearBindings();
            statement11.bindString(1, uniqueKey_uuid.toString());
            statement11.bindString(2, wapinr);
            statement11.bindString(3, "Orders");
            statement11.bindString(4, file_path);
            statement11.bindString(5, ras_id);
            statement11.execute();
            App_db.setTransactionSuccessful();
            App_db.endTransaction();
            progressDialog.dismiss_progress_dialog();
            Display_Image(file, fileName+fileExtension);
        }
    }


    public void Display_Image(File file, String filename)
    {
        progressDialog.show_progress_dialog(JSA_List_Activity.this,getString(R.string.loading));

        final Dialog decision_dialog = new Dialog(JSA_List_Activity.this,R.style.AppTheme);
        decision_dialog.setCancelable(true);
        decision_dialog.setCanceledOnTouchOutside(true);
        decision_dialog.setContentView(R.layout.notifications_view_attachements_activity);
        final WebView webView = (WebView) decision_dialog.findViewById(R.id.webView1);
        final TextView title_textview = (TextView)decision_dialog.findViewById(R.id.title_textview);
        title_textview.setText(filename);
        ImageView back_imageview = (ImageView)decision_dialog.findViewById(R.id.back_imageview);

        decision_dialog.show();

        back_imageview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                decision_dialog.dismiss();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onLoadResource(WebView view, String url)
            {
                super.onLoadResource(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                progressDialog.dismiss_progress_dialog();
            }
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
            {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        //File file1 = new File("/sdcard/abc123.pdf");
        webView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + file.getAbsolutePath() + "#zoom=page-width");
    }


    public class JSAList_Object
    {
        private String Rasid;
        private String Title;
        private String Jobtxt;
        public String getRasid() {
            return Rasid;
        }
        public void setRasid(String rasid) {
            Rasid = rasid;
        }
        public String getTitle() {
            return Title;
        }
        public void setTitle(String title) {
            Title = title;
        }
        public String getJobtxt() {
            return Jobtxt;
        }
        public void setJobtxt(String jobtxt) {
            Jobtxt = jobtxt;
        }
        public JSAList_Object(String Rasid, String Title, String Jobtxt)
        {
            this.Rasid = Rasid;
            this.Title = Title;
            this.Jobtxt = Jobtxt;
        }
    }


    @Override
    public void onClick(View v)
    {
        if(v == create_fab_button)
        {
            floatingActionMenu.close(true);
            Intent intent = new Intent(JSA_List_Activity.this,JSA_Add_Activity.class);
            intent.putExtra("aufnr",aufnr);
            intent.putExtra("wapinr",wapinr);
            intent.putExtra("iwerk",iwerk);
            intent.putExtra("equipId",equipId);
            startActivity(intent);
        }
        else if(v == back_imageview)
        {
            JSA_List_Activity.this.finish();
        }
        else if (v == refresh_fab_button)
        {
            floatingActionMenu.close(true);
            Refresh_JSA_Data();
        }
    }


}
