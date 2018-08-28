package com.enstrapp.fieldtekpro.equipment_inspection;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
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


public class Equipment_Inspection_Breakdown_Statics_Activity extends AppCompatActivity implements View.OnClickListener
{

    ImageView home_imageview;
    String status = "timeout";
    TextView no_data, current_month, break_time;
    CombinedChart mChart;
    LineDataSet lineDataSet_m;
    LineData lineData_c;
    Breakdown_Statistics breakdown_statistics;
    ProgressDialog progressdialog;
    ArrayList<Mis_Break_Stat_Object> bmt = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<String> SpmonS = new ArrayList<String>();
    String[] SpmonSS;
    float[] count_m, mttr;
    Date date;
    String tot_m2_time = "", equip_id = "", equip_name = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "";
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    SQLiteDatabase App_db;
    String DATABASE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_inspection_breakdown_statics);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            equip_id = extras.getString("equipment_id");
            equip_name = extras.getString("equipment_text");
        }

        current_month = (TextView) findViewById(R.id.current_month);
        break_time = (TextView) findViewById(R.id.break_down_hours);
        home_imageview = (ImageView) findViewById(R.id.back_imageview);

        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();

        DATABASE_NAME = getString(R.string.database_name);
        App_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        no_data = (TextView) findViewById(R.id.no_data_textview);
        username = FieldTekPro_SharedPref.getString("FieldTekPro_Username", null);
        password = FieldTekPro_SharedPref.getString("FieldTekPro_Password", null);

        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        String androidId = "" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();

        mChart = (CombinedChart) findViewById(R.id.line_chart);
        home_imageview.setOnClickListener(this);

        current_month.setText(equip_id + " (" + equip_name + ")");

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            breakdown_statistics = new Breakdown_Statistics();
            breakdown_statistics.execute();
        }
        else
        {
            mChart.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            no_data.setText("No Network Available. Please Check your Internet Connection.");
        }

    }


    public void onClick(View v)
    {
        if (v == home_imageview)
        {
            Equipment_Inspection_Breakdown_Statics_Activity.this.finish();
        }
    }


    private class Breakdown_Statistics extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(Equipment_Inspection_Breakdown_Statics_Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                /* Initializing Shared Preferences */
                FieldTekPro_SharedPref =getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
                username = FieldTekPro_SharedPref.getString("Username", null);
                password = FieldTekPro_SharedPref.getString("Password", null);
                String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
                /* Initializing Shared Preferences */
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"EB", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                device_serial_number = Build.SERIAL;
                String androidId = "" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
                device_uuid = deviceUuid.toString();
                /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                String URL =getString(R.string.ip_address);

                Map<String, String> map = new HashMap<>();
                map.put("Muser", username.toUpperCase().toString());
                map.put("DEVICESNO", device_serial_number);
                map.put("UDID", device_uuid);
                map.put("Ivequnr",equip_id );
                map.put("ivyear", "");
                map.put("LOW", "");
                map.put("HIGH", "");
                map.put("DeviceId", device_id);
                map.put("ivtransmittype", "MISR");
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
               Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<EquipmentInspection_BreakStatics_SER> call = service.getEquipmentInspectionAnalysis(url_link, basic, map);
                Response<EquipmentInspection_BreakStatics_SER> response = call.execute();
                int response_status_code = response.code();
                if (response_status_code == 200)
                {
                    if (response.isSuccessful() && response.body() != null)
                    {

                        /*Reading Response Data and Parsing to Serializable*/
                        EquipmentInspection_BreakStatics_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String response_data = new Gson().toJson(rs.getD().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                        /*Reading Data by using FOR Loop*/
                        for (int i = 0; i < response_data_jsonArray.length(); i++) {

                            /*Reading Data by using FOR Loop*/
                            JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());

                            /*Reading and Inserting Data into Database Table for EsBkdneqTotal*/
                            if (jsonObject.has("EsBkdneqTotal"))
                            {
                                try
                                {
                                    String EsBkdneqTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdneqMonthTotal().getResults());
                                    JSONArray jsonArray = new JSONArray(EsBkdneqTotal_response_data);
                                    if (jsonArray.length() > 0)
                                    {
                                        for (int j = 0; j < jsonArray.length(); j++)
                                        {
                                            tot_m2_time = jsonArray.getJSONObject(j).optString("TotM2");
                                            Mis_Break_Stat_Object bpt_o = new Mis_Break_Stat_Object();
                                            bpt_o.setIwerk(jsonArray.getJSONObject(j).optString("Iwerk"));
                                            bpt_o.setWarea(jsonArray.getJSONObject(j).optString("Warea"));
                                            bpt_o.setArbpl(jsonArray.getJSONObject(j).optString("Arbpl"));
                                            bpt_o.setSpmon(jsonArray.getJSONObject(j).optString("Spmon"));
                                            bpt_o.setSunit(jsonArray.getJSONObject(j).optString("Sunit"));
                                            bpt_o.setSmsaus(jsonArray.getJSONObject(j).optString("Smsaus"));
                                            bpt_o.setCount(jsonArray.getJSONObject(j).optString("Count"));
                                            bpt_o.setTotM2(jsonArray.getJSONObject(j).optString("TotM2"));
                                            bpt_o.setTotM3(jsonArray.getJSONObject(j).optString("TotM3"));
                                            bpt_o.setBdpmrat(jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                            bpt_o.setWitHrs(jsonArray.getJSONObject(j).optString("WitHrs"));
                                            bpt_o.setWitoutHrs(jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                            bpt_o.setMttrHours(jsonArray.getJSONObject(j).optString("MttrHours"));
                                            bpt_o.setMtbrHours(jsonArray.getJSONObject(j).optString("MtbrHours"));
                                            bpt_o.setName(jsonArray.getJSONObject(j).optString("Name"));
                                            bmt.add(bpt_o);
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            status = "success";
                        }
                    }
                }
            }
            catch (Exception e)
            {
                if (e.getMessage() == null)
                {
                    status = "exception";
                }
                else if (e.getMessage().equalsIgnoreCase("Read timed out"))
                {
                    status = "timeout";
                }
                else
                {
                    status = "exception";
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            try
            {
                if (status.equalsIgnoreCase("success"))
                {
                    final List<BarEntry> entries = new ArrayList<>();
                    ArrayList<Entry> line_c = new ArrayList<Entry>();
                    ArrayList<Entry> line_m = new ArrayList<Entry>();
                    String outputPattern = "MMM yyyy";
                    String inputPattern = "yyyyMM";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                    if (tot_m2_time != null && !tot_m2_time.equals(""))
                    {
                        if (tot_m2_time.contains(","))
                        {
                            String[] break_s = tot_m2_time.split(",");
                            String one = break_s[0];
                            String two = break_s[1].substring(0, 2);
                            String brk_hrs = one + "." + two;
                            break_time.setText("Total Breakdown Time : " + brk_hrs + " hrs");
                        }
                        else
                        {
                            break_time.setText("Total Breakdown Time : " + tot_m2_time + " hrs");
                        }
                    }
                    else
                    {
                        break_time.setVisibility(View.GONE);
                    }
                    if (bmt.size() > 0)
                    {
                        mttr = new float[bmt.size()];
                        count_m = new float[bmt.size()];
                        SpmonSS = new String[bmt.size()];
                        for (int j = 0; j < bmt.size(); j++)
                        {
                            try
                            {
                                if (bmt.get(j).getCount() != null && !bmt.get(j).getCount().equals(""))
                                {
                                    count_m[j] = Float.parseFloat(bmt.get(j).getCount());
                                }
                                else
                                {
                                    count_m[j] = 0;
                                }
                                if (bmt.get(j).getMttrHours() != null && !bmt.get(j).getMttrHours().equals(""))
                                {
                                    mttr[j] = Float.parseFloat(bmt.get(j).getMttrHours());
                                }
                                else
                                {
                                    mttr[j] = 0;
                                }

                                if (bmt.get(j).getSpmon() != null && !bmt.get(j).getSpmon().equals("") && !bmt.get(j).getSpmon().equals("null"))
                                {
                                    try
                                    {
                                        date = inputFormat.parse(bmt.get(j).getSpmon().substring(1));
                                        String dateS = outputFormat.format(date);
                                        SpmonS.add(dateS);
                                        SpmonSS[j] = "" + dateS;
                                    }
                                    catch (Exception e)
                                    {
                                    }
                                }
                                else
                                {
                                    SpmonSS[j] = "";
                                }
                            }
                            catch (Exception nfe)
                            {
                            }
                            line_m.add(new Entry(j, mttr[j]));
                            line_c.add(new Entry(j, count_m[j]));
                        }

                        ScatterDataSet set1 = new ScatterDataSet(line_c, "No# of BD");
                        set1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
                        set1.setColor(Color.rgb(201, 26, 41));
                        set1.setScatterShapeSize(13f);
                        lineDataSet_m = new LineDataSet(line_m, "MTTR");
                        lineDataSet_m.enableDashedLine(10f, 5f, 0f);
                        lineDataSet_m.setCircleRadius(3f);
                        lineDataSet_m.setDrawCircleHole(false);
                        lineDataSet_m.setCircleColor(Color.rgb(49, 84, 154));
                        lineDataSet_m.setColors(Color.rgb(49, 84, 154));
                        Drawable drawable_m = ContextCompat.getDrawable(Equipment_Inspection_Breakdown_Statics_Activity.this, R.drawable.fade_blue);
                        lineDataSet_m.setFillDrawable(drawable_m);
                        lineDataSet_m.setDrawFilled(true);
                        lineData_c = new LineData();
                        lineData_c.addDataSet(lineDataSet_m);
                        ScatterData d = new ScatterData();
                        d.addDataSet(set1);
                        MyMarkerView mv = new MyMarkerView(Equipment_Inspection_Breakdown_Statics_Activity.this, R.layout.markerview);
                        mv.setChartView(mChart);
                        mChart.setMarker(mv);
                        CombinedData data = new CombinedData();
                        data.setData(lineData_c);
                        data.setData(d);
                        Legend l = mChart.getLegend();
                        l.setWordWrapEnabled(true);
                        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        l.setDrawInside(true);
                        l.setTextSize(12);
                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setGranularity(1f); // only intervals of 1 day
                        xAxis.setValueFormatter(new MyXAxisValueFormatter(SpmonSS));
                        if (entries.size() == 1)
                        {
                            xAxis.setAxisMaximum(data.getXMax() + 0.5f);
                            xAxis.setAxisMinimum(data.getXMin() - 0.5f);
                        }
                        else
                        {
                            xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                            xAxis.setAxisMinimum(data.getXMin() - 0.25f);
                        }
                        YAxis rightAxis = mChart.getAxisRight();
                        rightAxis.setDrawLabels(false);
                        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER});
                        mChart.getDescription().setEnabled(false);
                        mChart.setExtraOffsets(2, 2, 2, 2);
                        mChart.getAxisLeft().setAxisMinimum(0);
                        mChart.getAxisLeft().setDrawLabels(true);
                        mChart.getAxisLeft().setDrawGridLines(true);
                        mChart.getAxisRight().setDrawGridLines(false);
                        mChart.getAxisLeft().setDrawAxisLine(true);
                        mChart.getAxisRight().setDrawAxisLine(false);
                        mChart.getXAxis().setLabelRotationAngle(90);
                        mChart.getLegend().setEnabled(true);
                        mChart.animateY(1000);
                        mChart.getXAxis().setLabelCount(bmt.size(), false);
                        mChart.notifyDataSetChanged();//Required if changes are made to pie value
                        progressdialog.dismiss();
                        mChart.setData(data);
                        mChart.invalidate();
                        mChart.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                    }
                    else
                    {
                        progressdialog.dismiss();
                        mChart.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    progressdialog.dismiss();
                    mChart.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e)
            {
            }

        }

    }

}
