package com.enstrapp.fieldtekpro.Calibration;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Initialload.Calibration_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

import static android.content.Context.MODE_PRIVATE;

public class Calibration_Save {
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "", Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();

    public static Map<String, String> Post_Calibration_Data(Context activity, ArrayList<Model_Notif_Calibration_Operations> calib_operations_ArrayList, ArrayList<Model_Notif_Calibration_UsageDecision> calib_usagedecision_ArrayList, ArrayList<Model_Notif_Calibration_Defects> calib_defects_ArrayList, String order_id) {
        try {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            token = app_sharedpreferences.getString("token", null);
            cookie = app_sharedpreferences.getString("cookie", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"D3", "PS", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
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

            /*Adding Arraylist*/
            ArrayList ArrayList = new ArrayList<>();
            /*Adding Arraylist*/

            /*Calling Calibration Model with Data*/
            Model_Notif_Calibration model_notif_calibration = new Model_Notif_Calibration();
            model_notif_calibration.setMuser(username.toUpperCase().toString());
            model_notif_calibration.setDeviceid(device_id);
            model_notif_calibration.setDevicesno(device_serial_number);
            model_notif_calibration.setUdid(device_uuid);
            model_notif_calibration.setIvTransmitType("LOAD");
            model_notif_calibration.setIvCommit(true);
            model_notif_calibration.setItQinspData(calib_operations_ArrayList);
            model_notif_calibration.setItQudData(calib_usagedecision_ArrayList);
            model_notif_calibration.setItQdefectData(calib_defects_ArrayList);
            model_notif_calibration.setEtQinspData(ArrayList);
            model_notif_calibration.setEtQudData(ArrayList);
            model_notif_calibration.setEtQdefectData(ArrayList);
            model_notif_calibration.setEtMessage(ArrayList);
            /*Calling Calibration Model with Data*/


            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Calibration_SER> call = service.PostCalibrationData(url_link, model_notif_calibration, basic, map);
            Response<Calibration_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {
                    /*Reading Response Data and Parsing to Serializable*/
                    Calibration_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/
                    if (response.body().getD().getEtMessage().getResults() != null &&
                            response.body().getD().getEtMessage().getResults().size() > 0) {
                        StringBuilder Message_stringbuilder = new StringBuilder();
                        List<Calibration_SER.EtMessage_Result> messages = response.body().getD().getEtMessage().getResults();
                        for (int i = 0; i < response.body().getD().getEtMessage().getResults().size(); i++) {
                            if (i == 0)
                                Message_stringbuilder.append(messages.get(i).getMessage());
                            else {
                                Message_stringbuilder.append("\n");
                                Message_stringbuilder.append(messages.get(i).getMessage().substring(1));
                            }
                        }

                        if (Message_stringbuilder.toString().startsWith("S")) {
                            App_db.execSQL("delete from EtQinspData");
                            App_db.execSQL("delete from EtQudData");

                            App_db.beginTransaction();

                            String response_data = new Gson().toJson(rs.getD());
                            JSONObject response_jsonObject = new JSONObject(response_data);
                            if (response_jsonObject.has("EtQinspData")) {
                                try {
                                    String EtQinspData_response_data = new Gson().toJson(rs.getD().getEtQinspData().getResults());
                                    JSONArray response_data_jsonArray = new JSONArray(EtQinspData_response_data);
                                    if (response_data_jsonArray.length() > 0) {
                                        String EtQinspData_sql = "Insert into EtQinspData (Aufnr, Prueflos, Vornr, Plnty, Plnnr, Plnkn, Merknr, Quantitat, Qualitat, QpmkZaehl, Msehi, Msehl, Verwmerkm, Kurztext, Result, Sollwert, Toleranzob, Toleranzub, Rueckmelnr, Satzstatus, Equnr, Pruefbemkt, Mbewertg, Pruefer, Pruefdatuv, Pruefdatub, Pruefzeitv, Pruefzeitb, Iststpumf, Anzfehleh, Anzwertg, Ktextmat, Katab1, Katalgart1, Auswmenge1, Codetext, Xstatus, Action, UUID, Udid, Werks) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement statement = App_db.compileStatement(EtQinspData_sql);
                                        statement.clearBindings();
                                        for (int j = 0; j < response_data_jsonArray.length(); j++) {
                                            statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                            statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Prueflos"));
                                            statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Vornr"));
                                            statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Plnty"));
                                            statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Plnnr"));
                                            statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Plnkn"));
                                            statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Merknr"));
                                            statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Quantitat"));
                                            statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Qualitat"));
                                            statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("QpmkZaehl"));
                                            statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Msehi"));
                                            statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Msehl"));
                                            statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Verwmerkm"));
                                            statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Kurztext"));
                                            statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Result"));
                                            statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("Sollwert"));
                                            statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("Toleranzob"));
                                            statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("Toleranzub"));
                                            statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("Rueckmelnr"));
                                            statement.bindString(20, response_data_jsonArray.getJSONObject(j).optString("Satzstatus"));
                                            statement.bindString(21, response_data_jsonArray.getJSONObject(j).optString("Equnr"));
                                            statement.bindString(22, response_data_jsonArray.getJSONObject(j).optString("Pruefbemkt"));

                                            String QUANTITAT = response_data_jsonArray.getJSONObject(j).optString("Quantitat");
                                            if (QUANTITAT.equalsIgnoreCase("X")) {
                                                String result = response_data_jsonArray.getJSONObject(j).optString("Result");
                                                if (result != null && !result.equals("")) {
                                                    String fromm = "", too = "";
                                                    String from = response_data_jsonArray.getJSONObject(j).optString("Toleranzub");
                                                    if (from != null && !from.equals("")) {
                                                        if (from.contains(",")) {
                                                            String result1 = from.replace(",", ".");
                                                            Float dvd = Float.parseFloat(result1);
                                                            DecimalFormat df = new DecimalFormat("###.##");
                                                            too = df.format(dvd);
                                                        } else {
                                                            Float dvd = Float.parseFloat(from);
                                                            DecimalFormat df = new DecimalFormat("###.##");
                                                            too = df.format(dvd);
                                                        }
                                                    }

                                                    String to = response_data_jsonArray.getJSONObject(j).optString("Toleranzob");
                                                    if (to != null && !to.equals("")) {
                                                        if (to.contains(",")) {
                                                            String result1 = to.replace(",", ".");
                                                            Float dvd = Float.parseFloat(result1);
                                                            DecimalFormat df = new DecimalFormat("###.##");
                                                            fromm = df.format(dvd);
                                                        } else {
                                                            Float dvd = Float.parseFloat(to);
                                                            DecimalFormat df = new DecimalFormat("###.##");
                                                            fromm = df.format(dvd);
                                                        }
                                                    }

                                                    if (result.contains(",")) {
                                                        String result1 = result.replace(",", ".");
                                                        Float dvd = Float.parseFloat(result1);
                                                        DecimalFormat df = new DecimalFormat("###.##");
                                                        String res = df.format(dvd);
                                                        Float floatt = Float.parseFloat(res);
                                                        Float floatt1 = Float.parseFloat(too);
                                                        Float floatt2 = Float.parseFloat(fromm);
                                                        if (floatt >= floatt1 && floatt <= floatt2) {
                                                            statement.bindString(23, "A");
                                                        } else {
                                                            statement.bindString(23, "R");
                                                        }
                                                    } else {
                                                        if (too != null && !too.equals("") && fromm != null && !fromm.equals("")) {
                                                            Float floatt = Float.parseFloat(result);
                                                            Float floatt1 = Float.parseFloat(too);
                                                            Float floatt2 = Float.parseFloat(fromm);
                                                            if (floatt >= floatt1 && floatt <= floatt2) {
                                                                statement.bindString(23, "A");
                                                            } else {
                                                                statement.bindString(23, "R");
                                                            }
                                                        } else {
                                                            statement.bindString(23, "R");
                                                        }
                                                    }
                                                } else {
                                                    statement.bindString(23, "R");
                                                }
                                            } else {
                                                statement.bindString(23, response_data_jsonArray.getJSONObject(j).optString("Mbewertg"));
                                            }

                                            statement.bindString(24, response_data_jsonArray.getJSONObject(j).optString("Pruefer"));
                                            statement.bindString(25, response_data_jsonArray.getJSONObject(j).optString("Pruefdatuv"));
                                            statement.bindString(26, response_data_jsonArray.getJSONObject(j).optString("Pruefdatub"));
                                            statement.bindString(27, response_data_jsonArray.getJSONObject(j).optString("Pruefzeitv"));
                                            statement.bindString(28, response_data_jsonArray.getJSONObject(j).optString("Pruefzeitb"));
                                            statement.bindString(29, response_data_jsonArray.getJSONObject(j).optString("Iststpumf"));
                                            statement.bindString(30, response_data_jsonArray.getJSONObject(j).optString("Anzfehleh"));
                                            statement.bindString(31, response_data_jsonArray.getJSONObject(j).optString("Anzwertg"));
                                            statement.bindString(32, response_data_jsonArray.getJSONObject(j).optString("Ktextmat"));
                                            statement.bindString(33, response_data_jsonArray.getJSONObject(j).optString("Katab1"));
                                            statement.bindString(34, response_data_jsonArray.getJSONObject(j).optString("Katalgart1"));
                                            statement.bindString(35, response_data_jsonArray.getJSONObject(j).optString("Auswmenge1"));
                                            statement.bindString(36, response_data_jsonArray.getJSONObject(j).optString("Codetext"));
                                            statement.bindString(37, response_data_jsonArray.getJSONObject(j).optString("Xstatus"));
                                            statement.bindString(38, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                            UUID uniqueKey_uuid = UUID.randomUUID();
                                            statement.bindString(39, uniqueKey_uuid.toString());
                                            statement.bindString(40, response_data_jsonArray.getJSONObject(j).optString("Udid"));
                                            statement.bindString(41, response_data_jsonArray.getJSONObject(j).optString("Werks"));
                                            statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }

                            if (response_jsonObject.has("EtQudData")) {
                                try {
                                    String EtQudData_response_data = new Gson().toJson(rs.getD().getEtQudData().getResults());
                                    JSONArray response_data_jsonArray = new JSONArray(EtQudData_response_data);
                                    if (response_data_jsonArray.length() > 0) {
                                        String EtQudData_sql = "Insert into EtQudData (Prueflos,Aufnr,Werks, Equnr, Vkatart, Vcodegrp, Vauswahlmg, Vcode, Qkennzahl, Vname, Vdatum, Vaedatum, Vezeitaen, Udtext, Udforce, Rcode, Xstatus, Action, Udid, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtQudData_statement = App_db.compileStatement(EtQudData_sql);
                                        EtQudData_statement.clearBindings();
                                        for (int j = 0; j < response_data_jsonArray.length(); j++) {
                                            EtQudData_statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Prueflos"));
                                            EtQudData_statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtQudData_statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Werks"));
                                            EtQudData_statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Equnr"));
                                            EtQudData_statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Vkatart"));
                                            EtQudData_statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Vcodegrp"));
                                            EtQudData_statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Vauswahlmg"));
                                            EtQudData_statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Vcode"));
                                            EtQudData_statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Qkennzahl"));
                                            EtQudData_statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("Vname"));
                                            EtQudData_statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Vdatum"));
                                            EtQudData_statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Vaedatum"));
                                            EtQudData_statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Vezeitaen"));
                                            EtQudData_statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Udtext"));
                                            EtQudData_statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Udforce"));
                                            EtQudData_statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("Rcode"));
                                            EtQudData_statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("Xstatus"));
                                            EtQudData_statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                            EtQudData_statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("Udid"));
                                            String Vcode = response_data_jsonArray.getJSONObject(j).optString("Vcode");
                                            String notes = response_data_jsonArray.getJSONObject(j).optString("Udtext");
                                            if (Vcode != null && !Vcode.equals("") || notes != null && !notes.equals("")) {
                                                EtQudData_statement.bindString(20, "hide");
                                            } else {
                                                EtQudData_statement.bindString(20, "visible");
                                            }
                                            EtQudData_statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }

                            App_db.setTransactionSuccessful();
                            App_db.endTransaction();
                            Get_Response = Message_stringbuilder.toString();
                            Get_Data = "";
                        } else {
                            Get_Response = Message_stringbuilder.toString();
                            Get_Data = "";
                        }
                    }
                }
            } else {
            }
        } catch (Exception e) {
            Get_Response = activity.getString(R.string.unable_prcscalb);
            Get_Data = "";
        } finally {
        }
        response.put("response_status", Get_Response);
        response.put("response_data", Get_Data);
        return response;
    }


}
