package com.enstrapp.fieldtekpro.equipment_inspection;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.login.SER_Login;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Equipment_GEO_Tag_Activity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    double current_lat, current_lng, current_alt;
    private GoogleMap mGoogleMap;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Button capture_location_button;
    ImageView back_imageview;
    TextView heading_textview;
    String function_location_id = "", equip_id = "", equip_name = "";
    Dialog decision_dialog;
    Error_Dialog error_dialog = new Error_Dialog();
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    SharedPreferences FieldTekPro_SharedPref;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    String cookie = "", token = "", username = "", password = "", device_id = "", device_uuid = "", device_serial_number = "";

    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_geo_tag_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            function_location_id = extras.getString("functionlocation_id");
            equip_id = extras.getString("equipment_id");
            equip_name = extras.getString("equipment_text");
        }

        app_sharedpreferences = Equipment_GEO_Tag_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        app_editor = app_sharedpreferences.edit();

        DATABASE_NAME = Equipment_GEO_Tag_Activity.this.getString(R.string.database_name);
        FieldTekPro_db = Equipment_GEO_Tag_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();

        username = FieldTekPro_SharedPref.getString("Username", null);
        password = FieldTekPro_SharedPref.getString("Password", null);

        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        String androidId = "" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();
        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */

        capture_location_button = (Button) findViewById(R.id.capture_location_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        heading_textview = (TextView) findViewById(R.id.heading_textview);

        heading_textview.setText(getString(R.string.geo_tag) + " (" + equip_id + ")");

        capture_location_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkPermissions())
            {
                initViews();
            }
        }
        else
        {
            initViews();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == capture_location_button) {
            decision_dialog = new Dialog(Equipment_GEO_Tag_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(true);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Equipment_GEO_Tag_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
            Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
            confirm.setText(R.string.yes);
            cancel.setText(R.string.no);
            description_textview.setText(getString(R.string.cordnts, equip_id, current_lat, current_lng));
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
                    String webservice_type = getString(R.string.webservice_type);
                    if(webservice_type.equalsIgnoreCase("odata"))
                    {
                        new Get_Token().execute();
                    }
                    else
                    {
                        new Post_GEO_Tag_REST().execute();
                    }
                }
            });
        }
        else if (v == back_imageview)
        {
            Equipment_GEO_Tag_Activity.this.finish();
        }
    }


    private void initViews()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.routemap_mapview);
        mapFragment.getMapAsync(this);
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(Equipment_GEO_Tag_Activity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    initViews();
                } else {
                    String permissionss = "";
                    for (String per : permissions) {
                        permissionss += "\n" + per;
                    }
                    // permissions list of don't granted permission
                }
                return;
            }
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Equipment_GEO_Tag_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setMyLocationEnabled(true);
        //mGoogleMap.setTrafficEnabled(true);
        //mGoogleMap.setOnMapLongClickListener(this);
        //mGoogleMap.setOnMarkerClickListener(this);
        //mGoogleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        MapsInitializer.initialize(Equipment_GEO_Tag_Activity.this);

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                current_lat = latLng.latitude;
                current_lng = latLng.longitude;
                Toast.makeText(Equipment_GEO_Tag_Activity.this,
                        getString(R.string.curnt_cordnts, current_lat, current_lng),
                        Toast.LENGTH_LONG).show();
                addCustomMarker();
            }
        });

    }

    private void addCustomMarker() {
        if (mGoogleMap == null) {
            return;
        }
        mGoogleMap.clear();
        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(current_lat, current_lng));
        mp.title(getString(R.string.your_cordnts, current_lat, current_lng));
        mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.addMarker(mp);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_lat, current_lng), 20));
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        current_lat = location.getLatitude();
        current_lng = location.getLongitude();
        current_alt = location.getAltitude();
        addCustomMarker();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(Equipment_GEO_Tag_Activity.this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(Equipment_GEO_Tag_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        current_lat = mLastLocation.getLatitude();
        current_lng = mLastLocation.getLongitude();
        addCustomMarker();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }


    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_GEO_Tag_Activity.this,
                    getString(R.string.geo_tagprgs));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(Equipment_GEO_Tag_Activity.this);
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
            if (token_status.equalsIgnoreCase("success")) {
                new Post_GEO_Tag().execute();
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Equipment_GEO_Tag_Activity.this,
                        getString(R.string.goe_unable));
            }
        }
    }


    private class Post_GEO_Tag extends AsyncTask<Void, Integer, Void> {
        String url_link = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token = app_sharedpreferences.getString("token", null);
                cookie = app_sharedpreferences.getString("cookie", null);
                String webservice_type = app_sharedpreferences.getString("webservice_type", null);
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"G", "U", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Equipment_GEO_Tag_Activity.this.getString(R.string.ip_address);
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);

                /*For Send Data in POST Header*/
                Map<String, String> map = new HashMap<>();
                map.put("x-csrf-token", token);
                map.put("Cookie", cookie);
                map.put("Accept", "application/json;odata=verbose");
                map.put("Content-Type", "application/json");
                /*For Send Data in POST Header*/


                ItGeoData itGeoData = new ItGeoData();
                itGeoData.setTplnr(function_location_id);
                itGeoData.setEqupNum(equip_id);
                itGeoData.setLatitude(current_lat + "");
                itGeoData.setLongitude(current_lng + "");
                itGeoData.setAltitude(current_alt + "");

                ArrayList<ItGeoData> itGeoDataArrayList = new ArrayList<>();
                itGeoDataArrayList.add(itGeoData);

                Geo_Tag_Details_Model geo_tag_details_model = new Geo_Tag_Details_Model();
                geo_tag_details_model.setIvTransmitType("LOAD");
                geo_tag_details_model.setMuser(username.toUpperCase().toString());
                geo_tag_details_model.setDeviceid(device_id);
                geo_tag_details_model.setDevicesno(device_serial_number);
                geo_tag_details_model.setUdid(device_uuid);
                geo_tag_details_model.setItGeoData(itGeoDataArrayList);

                Geo_Tag_Model geo_tag_model = new Geo_Tag_Model();
                geo_tag_model.setGeo_tag_details_model(geo_tag_details_model);

                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                Call<SER_Login> call = service.PostGeoTagData(url_link, geo_tag_model, basic, map);
                call.enqueue(new Callback<SER_Login>() {
                    @Override
                    public void onResponse(Call<SER_Login> call, Response<SER_Login> response) {
                        int login_response_status_code = response.code();
                        Log.v("kiran_sss", login_response_status_code + "....");
                        /*int login_response_status_code = response.code();
                        if(login_response_status_code == 201)
                        {
                        }
                        else if(login_response_status_code == 400)
                        {
                            custom_progress_dialog.dismiss_progress_dialog();
                        }
                        else if(login_response_status_code == 401)
                        {
                            custom_progress_dialog.dismiss_progress_dialog();
                        }
                        else
                        {
                            custom_progress_dialog.dismiss_progress_dialog();
                        }*/
                        custom_progress_dialog.dismiss_progress_dialog();
                        Success_Dialog success_dialog = new Success_Dialog();
                        success_dialog.show_success_dialog(Equipment_GEO_Tag_Activity.this,
                                getString(R.string.geotag_success, equip_id));
                    }

                    @Override
                    public void onFailure(Call<SER_Login> call, Throwable t) {
                        custom_progress_dialog.dismiss_progress_dialog();
                    }
                });

            } catch (Exception e) {
                custom_progress_dialog.dismiss_progress_dialog();
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
        }
    }



    private class Post_GEO_Tag_REST extends AsyncTask<Void, Integer, Void> {
        String url_link = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_GEO_Tag_Activity.this,
                    getString(R.string.geo_tagprgs));
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String webservice_type = app_sharedpreferences.getString("webservice_type", null);
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"G", "U", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Equipment_GEO_Tag_Activity.this.getString(R.string.ip_address);
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                REST_Interface service = retrofit.create(REST_Interface.class);

                /*For Send Data in POST Header*/
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json;odata=verbose");
                map.put("Content-Type", "application/json");
                /*For Send Data in POST Header*/


                ItGeoData_REST itGeoData = new ItGeoData_REST();
                itGeoData.setTplnr(function_location_id);
                itGeoData.setEqupNum(equip_id);
                itGeoData.setLatitude(current_lat + "");
                itGeoData.setLongitude(current_lng + "");
                itGeoData.setAltitude(current_alt + "");

                List<ItGeoData_REST> dd = new ArrayList<>();
                dd.add(itGeoData);

                Model_Notif_Create_REST.IsDevice isDevice = new Model_Notif_Create_REST.IsDevice();
                isDevice.setMUSER(username.toUpperCase().toString());
                isDevice.setDEVICEID(device_id);
                isDevice.setDEVICESNO(device_serial_number);
                isDevice.setUDID(device_uuid);


                Geo_Tag_Details_Model_REST geo_tag_details_model = new Geo_Tag_Details_Model_REST();
                geo_tag_details_model.setIsDevice(isDevice);
                geo_tag_details_model.setIvTransmitType("LOAD");
                geo_tag_details_model.setIvCommit(true);
                geo_tag_details_model.setIt_geo(dd);


                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                Call<SER_Login> call = service.PostGeoTagData(url_link, geo_tag_details_model, basic, map);
                call.enqueue(new Callback<SER_Login>() {
                    @Override
                    public void onResponse(Call<SER_Login> call, Response<SER_Login> response)
                    {
                        int login_response_status_code = response.code();
                        Log.v("kiran_sss", login_response_status_code + "....");
                        /*int login_response_status_code = response.code();
                        if(login_response_status_code == 201)
                        {
                        }
                        else if(login_response_status_code == 400)
                        {
                            custom_progress_dialog.dismiss_progress_dialog();
                        }
                        else if(login_response_status_code == 401)
                        {
                            custom_progress_dialog.dismiss_progress_dialog();
                        }
                        else
                        {
                            custom_progress_dialog.dismiss_progress_dialog();
                        }*/
                        custom_progress_dialog.dismiss_progress_dialog();
                        Success_Dialog success_dialog = new Success_Dialog();
                        success_dialog.show_success_dialog(Equipment_GEO_Tag_Activity.this,
                                getString(R.string.geotag_success, equip_id));
                    }

                    @Override
                    public void onFailure(Call<SER_Login> call, Throwable t) {
                        Log.v("kiran_ss",t.getMessage()+"...");
                        custom_progress_dialog.dismiss_progress_dialog();
                    }
                });

            } catch (Exception e) {
                custom_progress_dialog.dismiss_progress_dialog();
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
        }
    }


}

