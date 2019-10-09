package com.enstrapp.fieldtekpro.orders;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.equipment_inspection.Geo_Tag_Details_Model;
import com.enstrapp.fieldtekpro.equipment_inspection.Geo_Tag_Model;
import com.enstrapp.fieldtekpro.equipment_inspection.ItGeoData;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.login.SER_Login;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
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

public class Orders_MapView_Activity extends FragmentActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

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
    String cookie = "", token = "", username = "", password = "", device_id = "", device_uuid = "",
            device_serial_number = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private List<Orders_Object> ordersList = new ArrayList<>();

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

        app_sharedpreferences = Orders_MapView_Activity.this
                .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        app_editor = app_sharedpreferences.edit();

        DATABASE_NAME = Orders_MapView_Activity.this.getString(R.string.database_name);
        FieldTekPro_db = Orders_MapView_Activity.this.openOrCreateDatabase(DATABASE_NAME,
                MODE_PRIVATE, null);

        FieldTekPro_SharedPref = getApplicationContext()
                .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();

        username = FieldTekPro_SharedPref.getString("Username", null);
        password = FieldTekPro_SharedPref.getString("Password", null);

        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        String androidId = "" + Settings.Secure
                .getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();
        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */

        capture_location_button = (Button) findViewById(R.id.capture_location_button);
        capture_location_button.setVisibility(View.GONE);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        heading_textview = (TextView) findViewById(R.id.heading_textview);

        heading_textview.setText("Orders Map View");

        capture_location_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

        new Get_Order_List().execute();
    }

    private class Get_Order_List extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ordersList.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader",
                        null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String lat = cursor.getString(18);
                            String lng = cursor.getString(19);
                            if ((lat != null && !lat.equals("")) && (lng != null && !lng.equals(""))) {
                                Orders_Object olo = new Orders_Object(cursor.getString(2),
                                        cursor.getString(33),
                                        cursor.getString(39),
                                        cursor.getString(8),
                                        cursor.getString(9),
                                        cursor.getString(10), lat, lng,
                                        cursor.getString(4));
                                ordersList.add(olo);
                            }
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
            } finally {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (ordersList.size() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkPermissions()) {
                        initViews();
                    }
                } else {
                    initViews();
                }
            } else {
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            Orders_MapView_Activity.this.finish();
        }
    }

    private void initViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.routemap_mapview);
        mapFragment.getMapAsync(this);
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(Orders_MapView_Activity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded
                    .toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
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
            if (ContextCompat.checkSelfPermission(Orders_MapView_Activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        mGoogleMap.setMyLocationEnabled(false);
        MapsInitializer.initialize(Orders_MapView_Activity.this);
    }

    private void addCustomMarker() {
        if (mGoogleMap == null) {
            return;
        }
        mGoogleMap.clear();

        for (int i = 0; i < ordersList.size(); i++) {
            String lat = ordersList.get(i).getLatitude();
            String lng = ordersList.get(i).getLongitude();
            Double latitude = Double.parseDouble(lat);
            Double longitude = Double.parseDouble(lng);
            MarkerOptions mp = new MarkerOptions();
            mp.position(new LatLng(latitude, longitude));
            mp.title("Order Number : " + ordersList.get(i).getOrderId());
            mp.snippet("Equipment No : " + ordersList.get(i).getEquipment() +
                    "\n" + "Function Location : " + ordersList.get(i).getFunctionLocation() +
                    "\n" + "Priority : " + ordersList.get(i).getPriority() + " - "
                    + ordersList.get(i).getPriorityText() + "\n" + "Status : " +
                    ordersList.get(i).getOrderStatus() + "\n" + "Text : " +
                    ordersList.get(i).getShort_text());
            mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mGoogleMap.addMarker(mp);
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(latitude, longitude), 17));
        }

        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.orders_mapview_dialog, null);
                TextView title_textview = (TextView) v.findViewById(R.id.title_textview);
                title_textview.setText(marker.getTitle());
                TextView snippet_textview = (TextView) v.findViewById(R.id.snippet_textview);
                snippet_textview.setText(marker.getSnippet());
                return v;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        current_lat = location.getLatitude();
        current_lng = location.getLongitude();
        current_alt = location.getAltitude();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(Orders_MapView_Activity.this)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(Orders_MapView_Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, (LocationListener) this);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                this);
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
            custom_progress_dialog.show_progress_dialog(Orders_MapView_Activity.this,
                    getString(R.string.geotag_progress));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(Orders_MapView_Activity.this);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (token_status.equalsIgnoreCase("success")) {
                new Post_GEO_Tag().execute();
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Orders_MapView_Activity.this,
                        getString(R.string.geotag_unable));
            }
        }
    }

    private class Post_GEO_Tag extends AsyncTask<Void, Integer, Void> {
        String url_link = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token = app_sharedpreferences.getString("token", null);
                cookie = app_sharedpreferences.getString("cookie", null);
                String webservice_type = app_sharedpreferences.getString("webservice_type",
                        null);
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Get_SYNC_MAP_DATA where" +
                                " Zdoctype = ? and Zactivity = ? and Endpoint = ?",
                        new String[]{"G", "U", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Orders_MapView_Activity.this.getString(R.string.ip_address);
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120000, TimeUnit.SECONDS)
                        .writeTimeout(120000, TimeUnit.SECONDS)
                        .readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(URL).client(client).build();
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
                geo_tag_details_model.setMuser(username.toUpperCase());
                geo_tag_details_model.setDeviceid(device_id);
                geo_tag_details_model.setDevicesno(device_serial_number);
                geo_tag_details_model.setUdid(device_uuid);
                geo_tag_details_model.setItGeoData(itGeoDataArrayList);

                Geo_Tag_Model geo_tag_model = new Geo_Tag_Model();
                geo_tag_model.setGeo_tag_details_model(geo_tag_details_model);

                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);

                Call<SER_Login> call = service.PostGeoTagData(url_link, geo_tag_model, basic, map);
                call.enqueue(new Callback<SER_Login>() {
                    @Override
                    public void onResponse(Call<SER_Login> call, Response<SER_Login> response) {
                        int login_response_status_code = response.code();
                        Log.v("kiran_sss", login_response_status_code + "....");

                        custom_progress_dialog.dismiss_progress_dialog();
                        Success_Dialog success_dialog = new Success_Dialog();
                        success_dialog.show_success_dialog(Orders_MapView_Activity.this,
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
    }

    private class Orders_Object {
        private String orderId;
        private String priorityText;
        private String priority;
        private String equipment;
        private String functionLocation;
        private String orderStatus;
        private String latitude;
        private String longitude;
        private String short_text;

        public Orders_Object(String orderId, String priorityText, String orderStatus,
                             String priority, String equipment, String functionLocation,
                             String latitude, String longitude, String short_text) {
            this.orderId = orderId;
            this.priorityText = priorityText;
            this.orderStatus = orderStatus;
            this.priority = priority;
            this.equipment = equipment;
            this.functionLocation = functionLocation;
            this.latitude = latitude;
            this.longitude = longitude;
            this.short_text = short_text;
        }

        public String getShort_text() {
            return short_text;
        }

        public void setShort_text(String short_text) {
            this.short_text = short_text;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPriorityText() {
            return priorityText;
        }

        public void setPriorityText(String priorityText) {
            this.priorityText = priorityText;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public String getFunctionLocation() {
            return functionLocation;
        }

        public void setFunctionLocation(String functionLocation) {
            this.functionLocation = functionLocation;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}

