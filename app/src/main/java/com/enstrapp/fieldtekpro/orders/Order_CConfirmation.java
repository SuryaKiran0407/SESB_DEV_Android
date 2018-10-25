package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Order_CConfirmation {

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "", Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();
    private static Check_Empty checkempty = new Check_Empty();
    private static ArrayList<HashMap<String, String>> orders_uuid_list = new ArrayList<HashMap<String, String>>();
    private static StringBuffer message = new StringBuffer();
    private static boolean success = false;

    public static String Get_Data(Context activity, ArrayList<ConfirmOrder_Prcbl> cop_al, ArrayList<Measurement_Parceble> mpo_al,
                                  String transmitType, String operation, String orderId, String type, String longtext_text)
    {
        try
        {
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
            if (type.equals("pc")) {
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"W", "Z", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
            } else {
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"W", "CC", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
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

            ArrayList<ItConfirmOrderColl_Ser> ItConfirmOrderColl_Al = new ArrayList<>();
            ArrayList<ItConfirmOrder_Ser> ItConfirmOrder_Al = new ArrayList<>();
            ArrayList<ItImrg_Ser> ItImrg_Al = new ArrayList<>();
            ArrayList<OrdrHdrSer> ItOrderHeader_Al = new ArrayList<>();
            ArrayList<OrdrOprtnSer> ItOrderOperations_Al = new ArrayList<>();
            ArrayList<OrdrMatrlSer> ItOrderComponents_Al = new ArrayList<>();
            ArrayList<OrdrLngTxtSer> ItOrderLongtext_Al = new ArrayList<>();
            ArrayList<OrdrPermitSer> ItOrderPermits_Al = new ArrayList<>();
            ArrayList<OrdrOlistSer> ItOrderOlist_Al = new ArrayList<>();
            ArrayList<OrdrStatusSer> ItOrderStatus_Al = new ArrayList<>();
            ArrayList<OrdrWcmWwSer> ItWcmWwData_Al = new ArrayList<>();
            ArrayList<OrdrWcmWaSer> ItWcmWaData_Al = new ArrayList<>();
            ArrayList<OrdrWcmChkRqSer> ItWcmWaChkReq_Al = new ArrayList<>();
            ArrayList<OrdrWcmWdSer> ItWcmWdData_Al = new ArrayList<>();
            ArrayList<OrdrWcmWdItmSer> ItWcmWdItemData_Al = new ArrayList<>();
            ArrayList<OrdrWcmWagnsSer> ItWcmWcagns_Al = new ArrayList<>();
            ArrayList<OrdrDocSer> ItDocs_Al = new ArrayList<>();
            /*ArrayList<OrdrGeoSer> IsGeo_Al = new ArrayList<>();
            IsGeo_Al.add(geoSer);*/

            if (type.equals("TECO"))
                for (ConfirmOrder_Prcbl cop : cop_al) {
                    ItConfirmOrderColl_Ser confirmOrderSer = new ItConfirmOrderColl_Ser();
                    confirmOrderSer.setAufnr(cop.getAufnr());
                    confirmOrderSer.setStatus(cop.getStatus());
                    ItConfirmOrderColl_Al.add(confirmOrderSer);
                }
            else if (type.equals("ORCC")) {
                for (ConfirmOrder_Prcbl cop : cop_al) {
                    ItConfirmOrderColl_Ser confirmOrderSer = new ItConfirmOrderColl_Ser();
                    confirmOrderSer.setAufnr(cop.getAufnr());
                    confirmOrderSer.setVornr(cop.getVornr());
                    confirmOrderSer.setConfNo(cop.getConfNo());
                    confirmOrderSer.setConfText(cop.getConfText());
                    confirmOrderSer.setActWork(cop.getActWork());
                    confirmOrderSer.setUnWork(cop.getUnWork());
                    confirmOrderSer.setPlanWork(cop.getPlanWork());
                    confirmOrderSer.setLearr(cop.getLearr());
                    confirmOrderSer.setBemot(cop.getBemot());
                    confirmOrderSer.setGrund(cop.getGrund());
                    confirmOrderSer.setLeknw(cop.getLeknw());
                    confirmOrderSer.setAueru(cop.getAueru());
                    confirmOrderSer.setAusor(cop.getAusor());
                    confirmOrderSer.setPernr(cop.getPernr());
                    confirmOrderSer.setLoart(cop.getLoart());
                    confirmOrderSer.setStatus(cop.getStatus());
                    confirmOrderSer.setRsnum(cop.getRsnum());
                    confirmOrderSer.setPostgDate(cop.getPostgDate());
                    confirmOrderSer.setPlant(cop.getPlant());
                    confirmOrderSer.setWorkCntr(cop.getWorkCntr());
                    confirmOrderSer.setExecStartDate(cop.getExecStartDate());
                    confirmOrderSer.setExecStartTime(cop.getExecStartTime());
                    confirmOrderSer.setExecFinDate(cop.getExecFinDate());
                    confirmOrderSer.setExecFinTime(cop.getExecFinTime());
                    ItConfirmOrderColl_Al.add(confirmOrderSer);
                }

                Cursor cursor1 = null;
                try {
                    cursor1 = App_db.rawQuery("select * from Orders_Attachments where Object_id = ?", new String[]{orderId});
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                String path = cursor1.getString(4);
                                final File file = new File(path);
                                if (file.exists()) {
                                    Uri selectedUri = Uri.fromFile(file);
                                    final String filee_name = file.getName();
                                    final String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                                    final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                                    final int file_size = Integer.parseInt(String.valueOf(file.length()));
                                    byte[] byteArray = null;
                                    InputStream inputStream = new FileInputStream(path);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    byte[] b = new byte[4096 * 8];
                                    int bytesRead = 0;
                                    while ((bytesRead = inputStream.read(b)) != -1) {
                                        bos.write(b, 0, bytesRead);
                                    }
                                    byteArray = bos.toByteArray();
                                    final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                    OrdrDocSer ods = new OrdrDocSer();
                                    ods.setZobjid(orderId);
                                    ods.setZdoctype("W");
                                    ods.setZdoctypeItem("WH");
                                    ods.setFilename(filee_name);
                                    ods.setFiletype(fileExtension);
                                    ods.setFsize(String.valueOf(file_size));
                                    ods.setContent(encodedImage);
                                    ods.setDocId("");
                                    ods.setDocType("");
                                    ods.setObjtype("EQUI");
                                    ItDocs_Al.add(ods);
                                }
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                } catch (Exception e) {
                } finally {
                    if (cursor1 != null)
                        cursor1.close();
                }

                if (mpo_al != null)
                    if (mpo_al.size() > 0) {
                        for (Measurement_Parceble mpo : mpo_al) {
                            ItImrg_Ser itImrgSer = new ItImrg_Ser();
                            itImrgSer.setQmnum(mpo.getQmnum());
                            itImrgSer.setAufnr(mpo.getAufnr());
                            itImrgSer.setVornr(mpo.getVornr());
                            itImrgSer.setMdocm(mpo.getMdocm());
                            itImrgSer.setPoint(mpo.getPoint());
                            itImrgSer.setMpobj(mpo.getMpobj());
                            itImrgSer.setMpobt(mpo.getMpobt());
                            itImrgSer.setPsort(mpo.getPsort());
                            itImrgSer.setPttxt(mpo.getPttxt());
                            itImrgSer.setAtinn(mpo.getAtinn());
                            itImrgSer.setIdate(mpo.getIdate());
                            itImrgSer.setItime(mpo.getItime());
                            itImrgSer.setMdtxt(mpo.getMdtxt());
                            itImrgSer.setReadr(mpo.getReadr());
                            itImrgSer.setAtbez(mpo.getAtbez());
                            itImrgSer.setMsehi(mpo.getMsehi());
                            itImrgSer.setMsehl(mpo.getMsehl());
                            itImrgSer.setReadc(mpo.getReadc());
                            itImrgSer.setDesic(mpo.getDesic());
                            itImrgSer.setPrest(mpo.getPrest());
                            itImrgSer.setDocaf(mpo.getDocaf());
                            itImrgSer.setAction(mpo.getAction());
                            itImrgSer.setVlcod(mpo.getVlcod());
                            itImrgSer.setMdtxt(mpo.getMdtxt());
                            ItImrg_Al.add(itImrgSer);
                        }
                    }
            } else {
                for (ConfirmOrder_Prcbl cop : cop_al) {
                    ItConfirmOrder_Ser confirmOrderSer = new ItConfirmOrder_Ser();
                    confirmOrderSer.setOrderid(cop.getAufnr());
                    confirmOrderSer.setOperation(cop.getVornr());
                    confirmOrderSer.setConfNo(cop.getConfNo());
                    confirmOrderSer.setConfText(cop.getConfText());
                    confirmOrderSer.setActWork(cop.getActWork());
                    confirmOrderSer.setUnWork(cop.getUnWork());
                    confirmOrderSer.setPlanWork(cop.getPlanWork());
                    confirmOrderSer.setLearr(cop.getLearr());
                    confirmOrderSer.setBemot(cop.getBemot());
                    confirmOrderSer.setGrund(cop.getGrund());
                    confirmOrderSer.setLeknw(cop.getLeknw());
                    confirmOrderSer.setAueru(cop.getAueru());
                    confirmOrderSer.setAusor(cop.getAusor());
                    confirmOrderSer.setPernr(cop.getPernr());
                    confirmOrderSer.setLoart(cop.getLoart());
                    confirmOrderSer.setStatus(cop.getStatus());
                    confirmOrderSer.setRsnum(cop.getRsnum());
                    confirmOrderSer.setRspos(cop.getRspos());
                    confirmOrderSer.setPosnr(cop.getPosnr());
                    confirmOrderSer.setMatnr(cop.getMatnr());
                    confirmOrderSer.setBwart(cop.getBwart());
                    confirmOrderSer.setWerks(cop.getWerks());
                    confirmOrderSer.setLgort(cop.getLgort());
                    confirmOrderSer.setErfmg(cop.getErfmg());
                    confirmOrderSer.setErfme(cop.getErfme());
                    confirmOrderSer.setExecStartDate(cop.getExecStartDate());
                    confirmOrderSer.setExecStartTime(cop.getExecStartTime());
                    confirmOrderSer.setExecFinDate(cop.getExecFinDate());
                    confirmOrderSer.setExecFinTime(cop.getExecFinTime());
                    ItConfirmOrder_Al.add(confirmOrderSer);


                    /*Adding Order Confirmation Longtext to Arraylist*/
                    String order_confirm_longtext = longtext_text;
                    if (order_confirm_longtext != null && !order_confirm_longtext.equals(""))
                    {
                        if(order_confirm_longtext.contains("\n"))
                        {
                            String[] longtext_array = order_confirm_longtext.split("\n");
                            for(int i = 0; i < longtext_array.length; i++)
                            {
                                OrdrLngTxtSer mnc = new OrdrLngTxtSer();
                                mnc.setAufnr(cop.getAufnr());
                                mnc.setActivity(cop.getVornr());
                                mnc.setTextLine(longtext_array[i]);
                                mnc.setTdid("RMEL");
                                ItOrderLongtext_Al.add(mnc);
                            }
                        }
                        else
                        {
                            OrdrLngTxtSer mnc = new OrdrLngTxtSer();
                            mnc.setAufnr(cop.getAufnr());
                            mnc.setActivity(cop.getVornr());
                            mnc.setTextLine(order_confirm_longtext);
                            mnc.setTdid("RMEL");
                            ItOrderLongtext_Al.add(mnc);
                        }
                    }
                    /*Adding Order Confirmation Longtext to Arraylist*/


                }
            }

            /*Adding Empty_Al to Arraylist*/
            ArrayList Empty_Al = new ArrayList<>();


            OrdrCreateSer ordrSer = new OrdrCreateSer();
            OrderPartialConfirm_Ser ordrpSer = new OrderPartialConfirm_Ser();
            if (type.equals("pc")) {
                ordrpSer.setMuser(username.toUpperCase().toString());
                ordrpSer.setDeviceid(device_id);
                ordrpSer.setDevicesno(device_serial_number);
                ordrpSer.setUdid(device_uuid);
                ordrpSer.setIvTransmitType(transmitType);
                ordrpSer.setIvCommit(true);
                ordrpSer.setOperation(operation);
                ordrpSer.setItConfirmOrder(ItConfirmOrder_Al);
                ordrpSer.setItOrderLongtext(ItOrderLongtext_Al);
                ordrpSer.setItDocs(ItDocs_Al);
                ordrpSer.setItImrg(ItImrg_Al);
                ordrpSer.setEsAufnr(Empty_Al);
                ordrpSer.setEtOrderHeader(Empty_Al);
                ordrpSer.setEtOrderOperations(Empty_Al);
                ordrpSer.setEtOrderLongtext(Empty_Al);
                ordrpSer.setItOrderPermits(Empty_Al);
                ordrpSer.setEtOrderOlist(Empty_Al);
                ordrpSer.setEtOrderStatus(Empty_Al);
                ordrpSer.setEtDocs(Empty_Al);
                ordrpSer.setEtWcmWwData(Empty_Al);
                ordrpSer.setEtWcmWaData(Empty_Al);
                ordrpSer.setEtWcmWdData(Empty_Al);
                ordrpSer.setEtWcmWdItemData(Empty_Al);
                ordrpSer.setEtWcmWaChkReq(Empty_Al);
                ordrpSer.setEtWcmWcagns(Empty_Al);
                ordrpSer.setEtOrderComponents(Empty_Al);
                ordrpSer.setEtMessages(Empty_Al);
            } else {
                ordrSer.setMuser(username.toUpperCase().toString());
                ordrSer.setDeviceid(device_id);
                ordrSer.setDevicesno(device_serial_number);
                ordrSer.setUdid(device_uuid);
                ordrSer.setIvTransmitType(transmitType);
                ordrSer.setIvCommit(true);
                ordrSer.setOperation(operation);
                ordrSer.setItConfirmOrderColl(ItConfirmOrderColl_Al);
                ordrSer.setItOrderLongtext(ItOrderLongtext_Al);
                ordrSer.setItDocs(ItDocs_Al);
                ordrSer.setItImrg(ItImrg_Al);
                ordrSer.setEsAufnr(Empty_Al);
                ordrSer.setEtOrderHeader(Empty_Al);
                ordrSer.setEtOrderOperations(Empty_Al);
                ordrSer.setEtOrderLongtext(Empty_Al);
                ordrSer.setItOrderPermits(Empty_Al);
                ordrSer.setEtOrderOlist(Empty_Al);
                ordrSer.setEtOrderStatus(Empty_Al);
                ordrSer.setEtDocs(Empty_Al);
                ordrSer.setEtWcmWwData(Empty_Al);
                ordrSer.setEtWcmWaData(Empty_Al);
                ordrSer.setEtWcmWdData(Empty_Al);
                ordrSer.setEtWcmWdItemData(Empty_Al);
                ordrSer.setEtWcmWaChkReq(Empty_Al);
                ordrSer.setEtWcmWcagns(Empty_Al);
                ordrSer.setEtOrderComponents(Empty_Al);
                ordrSer.setEtMessages(Empty_Al);
            }

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call;
            if (type.equals("pc"))
                call = service.postConfirmOrder(url_link, ordrpSer, basic, map);
            else
                call = service.postCreateOrder(url_link, ordrSer, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_response_status_code", response_status_code + "...");
            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {
                    orders_uuid_list.clear();

                    /*Reading Response Data and Parsing to Serializable*/
                    Orders_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD());
                    /*Converting GSON Response to JSON Data for Parsing*/

                    /*Converting Response JSON Data to JSONArray for Reading*/
                    try {
                        JSONObject jsonObject = new JSONObject(response_data);
                    /*Converting Response JSON Data to JSONArray for Reading*/

                    /*Reading Data by using FOR Loop*/
                    /*for (int i = 0; i < response_data_jsonArray.length(); i++) {
                        *//*Reading Data by using FOR Loop*//*
                        JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
*/

                        message = new StringBuffer();

                        if (jsonObject.has("EtMessages")) {
                            try {
                                String EtOrderHeader_response_data1 = new Gson().toJson(rs.getD().getEtMessages().getResults());
                                JSONArray jsonArray1 = new JSONArray(EtOrderHeader_response_data1);

                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    message.append(jsonArray1.getJSONObject(j).optString("Message"));
                                }
                                if (message.toString().startsWith("S")) {
                                    success = true;
                                    App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from DUE_ORDERS_Longtext where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtOrderOlist where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtOrderStatus where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtWcmWwData where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtWcmWaData where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtWcmWdData where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtWcmWcagns where Aufnr = ?", new String[]{orderId});
                                    App_db.execSQL("delete from EtOrderComponents where Aufnr = ?", new String[]{orderId});

                                    try {
                                        if (jsonObject.has("EtWcmWaChkReq")) {
                                            try {
                                                String EtWcmWaChkReq_response_data = new Gson().toJson(rs.getD().getEtWcmWaChkReq().getResults());
                                                JSONArray jsonArray = new JSONArray(EtWcmWaChkReq_response_data);
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    String wapinr = jsonArray.getJSONObject(i).optString("Wapinr");
                                                    App_db.execSQL("delete from EtWcmWaChkReq where Wapinr = ?", new String[]{wapinr});
                                                    App_db.execSQL("delete from EtWcmWaData where Wapinr = ?", new String[]{wapinr});
                                                }
                                            } catch (Exception e) {
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    try {
                                        if (jsonObject.has("EtWcmWdData")) {
                                            String EtWcmWdData_response_data = new Gson().toJson(rs.getD().getEtWcmWdData().getResults());
                                            JSONArray jsonArray = new JSONArray(EtWcmWdData_response_data);
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                String Wcnr = jsonArray.getJSONObject(i).optString("Wcnr");
                                                App_db.execSQL("delete from EtWcmWdItemData where Wcnr = ?", new String[]{Wcnr});
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                } else {
                                    success = false;
                                    Get_Response = message.toString();
                                }
                            } catch (Exception e) {
                                success = false;
                            }

                        /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/

                        /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                        }


                        if (success) {
                            if (jsonObject.has("EtOrderHeader")) {
                                try {
                                    String EtOrderHeader_response_data = new Gson().toJson(rs.getD().getEtOrderHeader().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderHeader_response_data);
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String Aufnr = jsonArray.getJSONObject(j).optString("Aufnr");
                                        HashMap<String, String> uuid_hashmap = new HashMap<String, String>();
                                        UUID uniqueKey = UUID.randomUUID();
                                        uuid_hashmap.put("UUID", uniqueKey.toString());
                                        uuid_hashmap.put("Aufnr", checkempty.check_empty(Aufnr));
                                        orders_uuid_list.add(uuid_hashmap);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/

                            App_db.beginTransaction();

                        /*Reading and Inserting Data into Database Table for EtOrderHeader*/
                            if (jsonObject.has("EtOrderHeader")) {

                                try {
                                    String EtOrderHeader_response_data = new Gson().toJson(rs.getD().getEtOrderHeader().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderHeader_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrderHeader_sql = "Insert into DUE_ORDERS_EtOrderHeader (UUID, Aufnr, Auart, Ktext, Ilart, Ernam, Erdat, Priok, Equnr, Strno, TplnrInt, Bautl, Gltrp, Gstrp, Docs, Permits, Altitude, Latitude, Longitude, Qmnum, Closed, Completed, Ingrp, Arbpl, Werks, Bemot, Aueru, Auarttext, Qmartx, Qmtxt, Pltxt, Eqktx, Priokx , Ilatx, Plantname, Wkctrname, Ingrpname, Maktx, Xstatus, Usr01, Usr02, Usr03, Usr04, Usr05, Kokrs, Kostl, Anlzu, Anlzux, Ausvn, Ausbs, Auswk, Qmnam, ParnrVw, NameVw, Posid, Revnr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtOrderHeader_statement = App_db.compileStatement(EtOrderHeader_sql);
                                        EtOrderHeader_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtOrderHeader_statement.bindString(1, uuid);
                                                EtOrderHeader_statement.bindString(2, Aufnr);
                                                EtOrderHeader_statement.bindString(3, jsonArray.getJSONObject(j).optString("Auart"));
                                                EtOrderHeader_statement.bindString(4, jsonArray.getJSONObject(j).optString("Ktext"));
                                                EtOrderHeader_statement.bindString(5, jsonArray.getJSONObject(j).optString("Ilart"));
                                                EtOrderHeader_statement.bindString(6, jsonArray.getJSONObject(j).optString("Ernam"));
                                                EtOrderHeader_statement.bindString(7, jsonArray.getJSONObject(j).optString("Erdat"));
                                                EtOrderHeader_statement.bindString(8, jsonArray.getJSONObject(j).optString("Priok"));
                                                EtOrderHeader_statement.bindString(9, jsonArray.getJSONObject(j).optString("Equnr"));
                                                EtOrderHeader_statement.bindString(10, jsonArray.getJSONObject(j).optString("Strno"));
                                                EtOrderHeader_statement.bindString(11, jsonArray.getJSONObject(j).optString("TplnrInt"));
                                                EtOrderHeader_statement.bindString(12, jsonArray.getJSONObject(j).optString("Bautl"));
                                                EtOrderHeader_statement.bindString(13, jsonArray.getJSONObject(j).optString("Gltrp"));
                                                EtOrderHeader_statement.bindString(14, jsonArray.getJSONObject(j).optString("Gstrp"));
                                                EtOrderHeader_statement.bindString(15, jsonArray.getJSONObject(j).optString("Docs"));
                                                EtOrderHeader_statement.bindString(16, jsonArray.getJSONObject(j).optString("Permits"));
                                                EtOrderHeader_statement.bindString(17, jsonArray.getJSONObject(j).optString("Altitude"));
                                                EtOrderHeader_statement.bindString(18, jsonArray.getJSONObject(j).optString("Latitude"));
                                                EtOrderHeader_statement.bindString(19, jsonArray.getJSONObject(j).optString("Longitude"));
                                                EtOrderHeader_statement.bindString(20, jsonArray.getJSONObject(j).optString("Qmnum"));
                                                EtOrderHeader_statement.bindString(21, jsonArray.getJSONObject(j).optString("Closed"));
                                                EtOrderHeader_statement.bindString(22, jsonArray.getJSONObject(j).optString("Completed"));
                                                EtOrderHeader_statement.bindString(23, jsonArray.getJSONObject(j).optString("Ingrp"));
                                                EtOrderHeader_statement.bindString(24, jsonArray.getJSONObject(j).optString("Arbpl"));
                                                EtOrderHeader_statement.bindString(25, jsonArray.getJSONObject(j).optString("Werks"));
                                                EtOrderHeader_statement.bindString(26, jsonArray.getJSONObject(j).optString("Bemot"));
                                                EtOrderHeader_statement.bindString(27, jsonArray.getJSONObject(j).optString("Aueru"));
                                                EtOrderHeader_statement.bindString(28, jsonArray.getJSONObject(j).optString("Auarttext"));
                                                EtOrderHeader_statement.bindString(29, jsonArray.getJSONObject(j).optString("Qmartx"));
                                                EtOrderHeader_statement.bindString(30, jsonArray.getJSONObject(j).optString("Qmtxt"));
                                                EtOrderHeader_statement.bindString(31, jsonArray.getJSONObject(j).optString("Pltxt"));
                                                EtOrderHeader_statement.bindString(32, jsonArray.getJSONObject(j).optString("Eqktx"));
                                                EtOrderHeader_statement.bindString(33, jsonArray.getJSONObject(j).optString("Priokx"));
                                                EtOrderHeader_statement.bindString(34, jsonArray.getJSONObject(j).optString("Ilatx"));
                                                EtOrderHeader_statement.bindString(35, jsonArray.getJSONObject(j).optString("Plantname"));
                                                EtOrderHeader_statement.bindString(36, jsonArray.getJSONObject(j).optString("Wkctrname"));
                                                EtOrderHeader_statement.bindString(37, jsonArray.getJSONObject(j).optString("Ingrpname"));
                                                EtOrderHeader_statement.bindString(38, jsonArray.getJSONObject(j).optString("Maktx"));
                                                EtOrderHeader_statement.bindString(39, jsonArray.getJSONObject(j).optString("Xstatus"));
                                                EtOrderHeader_statement.bindString(40, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtOrderHeader_statement.bindString(41, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtOrderHeader_statement.bindString(42, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtOrderHeader_statement.bindString(43, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtOrderHeader_statement.bindString(44, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtOrderHeader_statement.bindString(45, jsonArray.getJSONObject(j).optString("Kokrs"));
                                                EtOrderHeader_statement.bindString(46, jsonArray.getJSONObject(j).optString("Kostl"));
                                                EtOrderHeader_statement.bindString(47, jsonArray.getJSONObject(j).optString("Anlzu"));
                                                EtOrderHeader_statement.bindString(48, jsonArray.getJSONObject(j).optString("Anlzux"));
                                                EtOrderHeader_statement.bindString(49, jsonArray.getJSONObject(j).optString("Ausvn"));
                                                EtOrderHeader_statement.bindString(50, jsonArray.getJSONObject(j).optString("Ausbs"));
                                                EtOrderHeader_statement.bindString(51, jsonArray.getJSONObject(j).optString("Auswk"));
                                                EtOrderHeader_statement.bindString(52, jsonArray.getJSONObject(j).optString("Qmnam"));
                                                EtOrderHeader_statement.bindString(53, jsonArray.getJSONObject(j).optString("ParnrVw"));
                                                EtOrderHeader_statement.bindString(54, jsonArray.getJSONObject(j).optString("NameVw"));
                                                EtOrderHeader_statement.bindString(55, jsonArray.getJSONObject(j).optString("Posid"));
                                                EtOrderHeader_statement.bindString(56, jsonArray.getJSONObject(j).optString("Revnr"));
                                                EtOrderHeader_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtOrderHeader*/


                        /*Reading and Inserting Data into Database Table for EtOrderOperations*/
                            if (jsonObject.has("EtOrderOperations")) {
                                try {
                                    String EtOrderOperations_response_data = new Gson().toJson(rs.getD().getEtOrderOperations().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderOperations_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrderOperations_sql = "Insert into DUE_ORDERS_EtOrderOperations (UUID,Aufnr,Vornr,Uvorn,Ltxa1,Arbpl,Werks,Steus,Larnt,Dauno,Daune,Fsavd,Ssedd,Pernr,Asnum,Plnty,Plnal,Plnnr,Rueck,Aueru,ArbplText,WerksText,SteusText,LarntText,Usr01,Usr02,Usr03,Usr04,Usr05,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtOrderOperations_statement = App_db.compileStatement(EtOrderOperations_sql);
                                        EtOrderOperations_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtOrderOperations_statement.bindString(1, uuid);
                                                EtOrderOperations_statement.bindString(2, Aufnr);
                                                EtOrderOperations_statement.bindString(3, jsonArray.getJSONObject(j).optString("Vornr"));
                                                EtOrderOperations_statement.bindString(4, jsonArray.getJSONObject(j).optString("Uvorn"));
                                                EtOrderOperations_statement.bindString(5, jsonArray.getJSONObject(j).optString("Ltxa1"));
                                                EtOrderOperations_statement.bindString(6, jsonArray.getJSONObject(j).optString("Arbpl"));
                                                EtOrderOperations_statement.bindString(7, jsonArray.getJSONObject(j).optString("Werks"));
                                                EtOrderOperations_statement.bindString(8, jsonArray.getJSONObject(j).optString("Steus"));
                                                EtOrderOperations_statement.bindString(9, jsonArray.getJSONObject(j).optString("Larnt"));
                                                EtOrderOperations_statement.bindString(10, jsonArray.getJSONObject(j).optString("Dauno"));
                                                EtOrderOperations_statement.bindString(11, jsonArray.getJSONObject(j).optString("Daune"));
                                                EtOrderOperations_statement.bindString(12, jsonArray.getJSONObject(j).optString("Fsavd"));
                                                EtOrderOperations_statement.bindString(13, jsonArray.getJSONObject(j).optString("Ssedd"));
                                                EtOrderOperations_statement.bindString(14, jsonArray.getJSONObject(j).optString("Pernr"));
                                                EtOrderOperations_statement.bindString(15, jsonArray.getJSONObject(j).optString("Asnum"));
                                                EtOrderOperations_statement.bindString(16, jsonArray.getJSONObject(j).optString("Plnty"));
                                                EtOrderOperations_statement.bindString(17, jsonArray.getJSONObject(j).optString("Plnal"));
                                                EtOrderOperations_statement.bindString(18, jsonArray.getJSONObject(j).optString("Plnnr"));
                                                EtOrderOperations_statement.bindString(19, jsonArray.getJSONObject(j).optString("Rueck"));
                                                EtOrderOperations_statement.bindString(20, jsonArray.getJSONObject(j).optString("Aueru"));
                                                EtOrderOperations_statement.bindString(21, jsonArray.getJSONObject(j).optString("ArbplText"));
                                                EtOrderOperations_statement.bindString(22, jsonArray.getJSONObject(j).optString("WerksText"));
                                                EtOrderOperations_statement.bindString(23, jsonArray.getJSONObject(j).optString("SteusText"));
                                                EtOrderOperations_statement.bindString(24, jsonArray.getJSONObject(j).optString("LarntText"));
                                                EtOrderOperations_statement.bindString(25, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtOrderOperations_statement.bindString(26, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtOrderOperations_statement.bindString(27, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtOrderOperations_statement.bindString(28, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtOrderOperations_statement.bindString(29, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtOrderOperations_statement.bindString(30, jsonArray.getJSONObject(j).optString("Action"));
                                                EtOrderOperations_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtOrderOperations*/


                        /*Reading and Inserting Data into Database Table for EtOrderLongtext*/
                            if (jsonObject.has("EtOrderLongtext")) {
                                try {
                                    String EtOrderLongtext_response_data = new Gson().toJson(rs.getD().getEtOrderLongtext().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderLongtext_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext (UUID, Aufnr, Activity, TextLine, Tdid) values(?,?,?,?,?);";
                                                SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                                                EtOrderLongtext_statement.clearBindings();
                                                EtOrderLongtext_statement.bindString(1, uuid);
                                                EtOrderLongtext_statement.bindString(2, Aufnr);
                                                EtOrderLongtext_statement.bindString(3, jsonArray.getJSONObject(j).optString("Activity"));
                                                EtOrderLongtext_statement.bindString(4, jsonArray.getJSONObject(j).optString("TextLine"));
                                                EtOrderLongtext_statement.bindString(5, jsonArray.getJSONObject(j).optString("Tdid"));
                                                EtOrderLongtext_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtOrderLongtext*/


                        /*Reading and Inserting Data into Database Table for EtOrderOlist*/
                            if (jsonObject.has("EtOrderOlist")) {
                                try {
                                    String EtOrderOlist_response_data = new Gson().toJson(rs.getD().getEtOrderOlist().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderOlist_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrderOlist_sql = "Insert into EtOrderOlist (UUID, Aufnr, Obknr, Obzae, Qmnum, Equnr, Strno, Tplnr, Bautl, Qmtxt, Pltxt, Eqktx, Maktx, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtOrderOlist_statement = App_db.compileStatement(EtOrderOlist_sql);
                                        EtOrderOlist_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtOrderOlist_statement.bindString(1, uuid);
                                                EtOrderOlist_statement.bindString(2, Aufnr);
                                                EtOrderOlist_statement.bindString(3, jsonArray.getJSONObject(j).optString("Obknr"));
                                                EtOrderOlist_statement.bindString(4, jsonArray.getJSONObject(j).optString("Obzae"));
                                                EtOrderOlist_statement.bindString(5, jsonArray.getJSONObject(j).optString("Qmnum"));
                                                EtOrderOlist_statement.bindString(6, jsonArray.getJSONObject(j).optString("Equnr"));
                                                EtOrderOlist_statement.bindString(7, jsonArray.getJSONObject(j).optString("Strno"));
                                                EtOrderOlist_statement.bindString(8, jsonArray.getJSONObject(j).optString("Tplnr"));
                                                EtOrderOlist_statement.bindString(9, jsonArray.getJSONObject(j).optString("Bautl"));
                                                EtOrderOlist_statement.bindString(10, jsonArray.getJSONObject(j).optString("Qmtxt"));
                                                EtOrderOlist_statement.bindString(11, jsonArray.getJSONObject(j).optString("Pltxt"));
                                                EtOrderOlist_statement.bindString(12, jsonArray.getJSONObject(j).optString("Eqktx"));
                                                EtOrderOlist_statement.bindString(13, jsonArray.getJSONObject(j).optString("Maktx"));
                                                EtOrderOlist_statement.bindString(14, jsonArray.getJSONObject(j).optString("Action"));
                                                EtOrderOlist_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtOrderOlist*/


                        /*Reading and Inserting Data into Database Table for EtOrderStatus*/
                            if (jsonObject.has("EtOrderStatus")) {
                                try {
                                    String EtOrderStatus_response_data = new Gson().toJson(rs.getD().getEtOrderStatus().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderStatus_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrderStatus_sql = "Insert into EtOrderStatus (UUID,Aufnr, Vornr, Objnr, Stsma, Inist, Stonr, Hsonr, Nsonr,Stat, Act, Txt04, Txt30, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtOrderStatus_statement = App_db.compileStatement(EtOrderStatus_sql);
                                        EtOrderStatus_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtOrderStatus_statement.bindString(1, uuid);
                                                EtOrderStatus_statement.bindString(2, Aufnr);
                                                EtOrderStatus_statement.bindString(3, jsonArray.getJSONObject(j).optString("Vornr"));
                                                EtOrderStatus_statement.bindString(4, jsonArray.getJSONObject(j).optString("Objnr"));
                                                EtOrderStatus_statement.bindString(5, jsonArray.getJSONObject(j).optString("Stsma"));
                                                EtOrderStatus_statement.bindString(6, jsonArray.getJSONObject(j).optString("Inist"));
                                                EtOrderStatus_statement.bindString(7, jsonArray.getJSONObject(j).optString("Stonr"));
                                                EtOrderStatus_statement.bindString(8, jsonArray.getJSONObject(j).optString("Hsonr"));
                                                EtOrderStatus_statement.bindString(9, jsonArray.getJSONObject(j).optString("Nsonr"));
                                                EtOrderStatus_statement.bindString(10, jsonArray.getJSONObject(j).optString("Stat"));
                                                EtOrderStatus_statement.bindString(11, jsonArray.getJSONObject(j).optString("Act"));
                                                EtOrderStatus_statement.bindString(12, jsonArray.getJSONObject(j).optString("Txt04"));
                                                EtOrderStatus_statement.bindString(13, jsonArray.getJSONObject(j).optString("Txt30"));
                                                EtOrderStatus_statement.bindString(14, jsonArray.getJSONObject(j).optString("Action"));
                                                EtOrderStatus_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtOrderStatus*/


                        /*Reading and Inserting Data into Database Table for EtDocs*/
                            if (jsonObject.has("EtDocs")) {
                                try {
                                    String EtDocs_response_data = new Gson().toJson(rs.getD().getEtDocs().getResults());
                                    JSONArray jsonArray = new JSONArray(EtDocs_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtDocs_sql = "Insert into DUE_ORDERS_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Contentx) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                        EtDocs_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtDocs_statement.bindString(1, "");
                                            EtDocs_statement.bindString(2, jsonArray.getJSONObject(j).optString("Zobjid"));
                                            EtDocs_statement.bindString(3, jsonArray.getJSONObject(j).optString("Zdoctype"));
                                            EtDocs_statement.bindString(4, jsonArray.getJSONObject(j).optString("ZdoctypeItem"));
                                            EtDocs_statement.bindString(5, jsonArray.getJSONObject(j).optString("Filename"));
                                            EtDocs_statement.bindString(6, jsonArray.getJSONObject(j).optString("Filetype"));
                                            EtDocs_statement.bindString(7, jsonArray.getJSONObject(j).optString("Fsize"));
                                            EtDocs_statement.bindString(8, jsonArray.getJSONObject(j).optString("Content"));
                                            EtDocs_statement.bindString(9, jsonArray.getJSONObject(j).optString("DocId"));
                                            EtDocs_statement.bindString(10, jsonArray.getJSONObject(j).optString("DocType"));
                                            EtDocs_statement.bindString(11, jsonArray.getJSONObject(j).optString("Objtype"));
                                            EtDocs_statement.bindString(12, jsonArray.getJSONObject(j).optString("Contentx"));
                                            EtDocs_statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtDocs*/


                        /*Reading and Inserting Data into Database Table for EtWcmWwData*/
                            if (jsonObject.has("EtWcmWwData")) {
                                try {
                                    String EtWcmWwData_response_data = new Gson().toJson(rs.getD().getEtWcmWwData().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWwData_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWcmWwData_sql = "Insert into EtWcmWwData (UUID, Aufnr, Objart, Wapnr, Iwerk, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Rctime, Rcunit, Priok, Priokx, Stxt, Datefr, Timefr, Dateto, Timeto, Objnr, Crea, Prep, Comp, Appr, Pappr, Action, Begru, Begtx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWwData_statement = App_db.compileStatement(EtWcmWwData_sql);
                                        EtWcmWwData_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtWcmWwData_statement.bindString(1, uuid);
                                                EtWcmWwData_statement.bindString(2, Aufnr);
                                                EtWcmWwData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objart"));
                                                EtWcmWwData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wapnr"));
                                                EtWcmWwData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Iwerk"));
                                                EtWcmWwData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Usage"));
                                                EtWcmWwData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usagex"));
                                                EtWcmWwData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Train"));
                                                EtWcmWwData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Trainx"));
                                                EtWcmWwData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Anlzu"));
                                                EtWcmWwData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzux"));
                                                EtWcmWwData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Etape"));
                                                EtWcmWwData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etapex"));
                                                EtWcmWwData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Rctime"));
                                                EtWcmWwData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Rcunit"));
                                                EtWcmWwData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Priok"));
                                                EtWcmWwData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Priokx"));
                                                EtWcmWwData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Stxt"));
                                                EtWcmWwData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Datefr"));
                                                EtWcmWwData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Timefr"));
                                                EtWcmWwData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Dateto"));
                                                EtWcmWwData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Timeto"));
                                                EtWcmWwData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Objnr"));
                                                EtWcmWwData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Crea"));
                                                EtWcmWwData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Prep"));
                                                EtWcmWwData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Comp"));
                                                EtWcmWwData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Appr"));
                                                EtWcmWwData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Pappr"));
                                                EtWcmWwData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Action"));
                                                EtWcmWwData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Begru"));
                                                EtWcmWwData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begtx"));
                                                EtWcmWwData_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWwData*/


                         /*Reading and Inserting Data into Database Table for EtWcmWaData*/
                            if (jsonObject.has("EtWcmWaData")) {
                                try {
                                    String EtWcmWaData_response_data = new Gson().toJson(rs.getD().getEtWcmWaData().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWaData_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWcmWaData_sql = "Insert into EtWcmWaData (UUID, Aufnr, Objart, Wapinr, Iwerk, Objtyp, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Stxt, Datefr, Timefr, Dateto, Timeto, Priok, Priokx, Rctime, Rcunit, Objnr, Refobj, Crea, Prep, Comp, Appr, Action, Begru, Begtx, Extperiod) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWaData_statement = App_db.compileStatement(EtWcmWaData_sql);
                                        EtWcmWaData_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtWcmWaData_statement.bindString(1, uuid);
                                                EtWcmWaData_statement.bindString(2, Aufnr);
                                                EtWcmWaData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objart"));
                                                EtWcmWaData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wapinr"));
                                                EtWcmWaData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Iwerk"));
                                                EtWcmWaData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Objtyp"));
                                                EtWcmWaData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usage"));
                                                EtWcmWaData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Usagex"));
                                                EtWcmWaData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Train"));
                                                EtWcmWaData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Trainx"));
                                                EtWcmWaData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzu"));
                                                EtWcmWaData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Anlzux"));
                                                EtWcmWaData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etape"));
                                                EtWcmWaData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Etapex"));
                                                EtWcmWaData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Stxt"));
                                                EtWcmWaData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Datefr"));
                                                EtWcmWaData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Timefr"));
                                                EtWcmWaData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Dateto"));
                                                EtWcmWaData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Timeto"));
                                                EtWcmWaData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Priok"));
                                                EtWcmWaData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Priokx"));
                                                EtWcmWaData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Rctime"));
                                                EtWcmWaData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Rcunit"));
                                                EtWcmWaData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Objnr"));
                                                EtWcmWaData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Refobj"));
                                                EtWcmWaData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Crea"));
                                                EtWcmWaData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Prep"));
                                                EtWcmWaData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Comp"));
                                                EtWcmWaData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Appr"));
                                                EtWcmWaData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Action"));
                                                EtWcmWaData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begru"));
                                                EtWcmWaData_statement.bindString(32, jsonArray.getJSONObject(j).optString("Begtx"));
                                                EtWcmWaData_statement.bindString(33, jsonArray.getJSONObject(j).optString("Extperiod"));
                                                EtWcmWaData_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWaData*/


                        /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/
                            if (jsonObject.has("EtWcmWaChkReq")) {
                                try {
                                    String EtWcmWaChkReq_response_data = new Gson().toJson(rs.getD().getEtWcmWaChkReq().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWaChkReq_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWcmWaChkReq_sql = "Insert into EtWcmWaChkReq (Wapinr, Wapityp, Needid, Value, ChkPointType, Desctext, Tplnr, Wkgrp, Needgrp, Equnr) values(?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWaChkReq_statement = App_db.compileStatement(EtWcmWaChkReq_sql);
                                        EtWcmWaChkReq_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtWcmWaChkReq_statement.bindString(1, jsonArray.getJSONObject(j).optString("Wapinr"));
                                            EtWcmWaChkReq_statement.bindString(2, jsonArray.getJSONObject(j).optString("Wapityp"));
                                            EtWcmWaChkReq_statement.bindString(5, jsonArray.getJSONObject(j).optString("ChkPointType"));
                                            if (jsonArray.getJSONObject(j).optString("ChkPointType").equalsIgnoreCase("W"))
                                                EtWcmWaChkReq_statement.bindString(3, jsonArray.getJSONObject(j).optString("Wkid"));
                                            else
                                                EtWcmWaChkReq_statement.bindString(3, jsonArray.getJSONObject(j).optString("Needid"));
                                            EtWcmWaChkReq_statement.bindString(4, jsonArray.getJSONObject(j).optString("Value"));
                                            EtWcmWaChkReq_statement.bindString(6, jsonArray.getJSONObject(j).optString("Desctext"));
                                            EtWcmWaChkReq_statement.bindString(7, jsonArray.getJSONObject(j).optString("Tplnr"));
                                            EtWcmWaChkReq_statement.bindString(8, jsonArray.getJSONObject(j).optString("Wkgrp"));
                                            EtWcmWaChkReq_statement.bindString(9, jsonArray.getJSONObject(j).optString("Needgrp"));
                                            EtWcmWaChkReq_statement.bindString(10, jsonArray.getJSONObject(j).optString("Equnr"));
                                            EtWcmWaChkReq_statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/


                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/
                            if (jsonObject.has("EtWcmWdData")) {
                                try {
                                    String EtWcmWdData_response_data = new Gson().toJson(rs.getD().getEtWcmWdData().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWdData_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWcmWdData_sql = "Insert into EtWcmWdData (Aufnr, Objart, Wcnr, Iwerk, Objtyp, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Stxt, Datefr, Timefr, Dateto, Timeto, Priok, Priokx, Rctime, Rcunit, Objnr, Refobj, Crea, Prep, Comp, Appr, Action, Begru, Begtx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWdData_statement = App_db.compileStatement(EtWcmWdData_sql);
                                        EtWcmWdData_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtWcmWdData_statement.bindString(1, jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtWcmWdData_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWdData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Wcnr"));
                                            EtWcmWdData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWdData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWdData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Usage"));
                                            EtWcmWdData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usagex"));
                                            EtWcmWdData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Train"));
                                            EtWcmWdData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Trainx"));
                                            EtWcmWdData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Anlzu"));
                                            EtWcmWdData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzux"));
                                            EtWcmWdData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Etape"));
                                            EtWcmWdData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etapex"));
                                            EtWcmWdData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWdData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Datefr"));
                                            EtWcmWdData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Timefr"));
                                            EtWcmWdData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Dateto"));
                                            EtWcmWdData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Timeto"));
                                            EtWcmWdData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtWcmWdData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtWcmWdData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Rctime"));
                                            EtWcmWdData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Rcunit"));
                                            EtWcmWdData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWdData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Refobj"));
                                            EtWcmWdData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Crea"));
                                            EtWcmWdData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Prep"));
                                            EtWcmWdData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Comp"));
                                            EtWcmWdData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Appr"));
                                            EtWcmWdData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWdData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Begru"));
                                            EtWcmWdData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begtx"));
                                            if (jsonArray.getJSONObject(j).has("EtWcmWdDataTagtext")) {
                                                try {
                                                    String EtWcmWdDataTagtext_response_data = new Gson().toJson(rs.getD().getEtWcmWdData().getResults().get(j).getEtWcmWdDataTagtext().getResults());
                                                    JSONArray jsonArray1 = new JSONArray(EtWcmWdDataTagtext_response_data);
                                                    if (jsonArray1.length() > 0) {
                                                        String EtWcmWdDataTagtext_sql = "Insert into EtWcmWdDataTagtext (Aufnr, Wcnr, Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                        SQLiteStatement EtWcmWdDataTagtext_statement = App_db.compileStatement(EtWcmWdDataTagtext_sql);
                                                        EtWcmWdDataTagtext_statement.clearBindings();
                                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                                            EtWcmWdDataTagtext_statement.bindString(1, jsonArray1.getJSONObject(k).optString("Aufnr"));
                                                            EtWcmWdDataTagtext_statement.bindString(2, jsonArray1.getJSONObject(k).optString("Wcnr"));
                                                            EtWcmWdDataTagtext_statement.bindString(3, jsonArray1.getJSONObject(k).optString("Objtype"));
                                                            EtWcmWdDataTagtext_statement.bindString(4, jsonArray1.getJSONObject(k).optString("FormatCol"));
                                                            EtWcmWdDataTagtext_statement.bindString(5, jsonArray1.getJSONObject(k).optString("TextLine"));
                                                            EtWcmWdDataTagtext_statement.bindString(6, jsonArray1.getJSONObject(k).optString("Action"));
                                                            EtWcmWdDataTagtext_statement.execute();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                            if (jsonArray.getJSONObject(j).has("EtWcmWdDataUntagtext")) {
                                                try {
                                                    String EtWcmWdDataUntagtext_response_data = new Gson().toJson(rs.getD().getEtWcmWdData().getResults().get(j).getEtWcmWdDataUntagtext().getResults());
                                                    JSONArray jsonArray1 = new JSONArray(EtWcmWdDataUntagtext_response_data);
                                                    if (jsonArray1.length() > 0) {
                                                        String EtWcmWdDataUntagtext_sql = "Insert into EtWcmWdDataUntagtext (Aufnr, Wcnr, Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                        SQLiteStatement EtWcmWdDataUntagtext_statement = App_db.compileStatement(EtWcmWdDataUntagtext_sql);
                                                        EtWcmWdDataUntagtext_statement.clearBindings();
                                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                                            EtWcmWdDataUntagtext_statement.bindString(1, jsonArray1.getJSONObject(k).optString("Aufnr"));
                                                            EtWcmWdDataUntagtext_statement.bindString(2, jsonArray1.getJSONObject(k).optString("Wcnr"));
                                                            EtWcmWdDataUntagtext_statement.bindString(3, jsonArray1.getJSONObject(k).optString("Objtype"));
                                                            EtWcmWdDataUntagtext_statement.bindString(4, jsonArray1.getJSONObject(k).optString("FormatCol"));
                                                            EtWcmWdDataUntagtext_statement.bindString(5, jsonArray1.getJSONObject(k).optString("TextLine"));
                                                            EtWcmWdDataUntagtext_statement.bindString(6, jsonArray1.getJSONObject(k).optString("Action"));
                                                            EtWcmWdDataUntagtext_statement.execute();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                        }
                                        EtWcmWdData_statement.execute();

                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/


                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/
                            if (jsonObject.has("EtWcmWdItemData")) {
                                try {
                                    String EtWcmWdItemData_response_data = new Gson().toJson(rs.getD().getEtWcmWdItemData().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWdItemData_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String EtWcmWdItemData_sql = "Insert into EtWcmWdItemData (Wcnr, Wcitm, Objnr, Itmtyp, Seq, Pred, Succ, Ccobj, Cctyp, Stxt, Tggrp, Tgstep, Tgproc, Tgtyp, Tgseq, Tgtxt, Unstep, Unproc, Untyp, Unseq, Untxt, Phblflg, Phbltyp, Phblnr, Tgflg, Tgform, Tgnr, Unform, Unnr, Control, Location, Refobj, Action, Btg, Etg, Bug, Eug) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtWcmWdItemData_statement = App_db.compileStatement(EtWcmWdItemData_sql);
                                            EtWcmWdItemData_statement.clearBindings();
                                            EtWcmWdItemData_statement.bindString(1, jsonArray.getJSONObject(j).optString("Wcnr"));
                                            EtWcmWdItemData_statement.bindString(2, jsonArray.getJSONObject(j).optString("Wcitm"));
                                            EtWcmWdItemData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWdItemData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Itmtyp"));
                                            EtWcmWdItemData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Seq"));
                                            EtWcmWdItemData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Pred"));
                                            EtWcmWdItemData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Succ"));
                                            EtWcmWdItemData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Ccobj"));
                                            EtWcmWdItemData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Cctyp"));
                                            EtWcmWdItemData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWdItemData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Tggrp"));
                                            EtWcmWdItemData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Tgstep"));
                                            EtWcmWdItemData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Tgproc"));
                                            EtWcmWdItemData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Tgtyp"));
                                            EtWcmWdItemData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Tgseq"));
                                            EtWcmWdItemData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Tgtxt"));
                                            EtWcmWdItemData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Unstep"));
                                            EtWcmWdItemData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Unproc"));
                                            EtWcmWdItemData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Untyp"));
                                            EtWcmWdItemData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Unseq"));
                                            EtWcmWdItemData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Untxt"));
                                            EtWcmWdItemData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Phblflg"));
                                            EtWcmWdItemData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Phbltyp"));
                                            EtWcmWdItemData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Phblnr"));
                                            EtWcmWdItemData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Tgflg"));
                                            EtWcmWdItemData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Tgform"));
                                            EtWcmWdItemData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Tgnr"));
                                            EtWcmWdItemData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Unform"));
                                            EtWcmWdItemData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Unnr"));
                                            EtWcmWdItemData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Control"));
                                            EtWcmWdItemData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Location"));
                                            EtWcmWdItemData_statement.bindString(32, jsonArray.getJSONObject(j).optString("Refobj"));
                                            EtWcmWdItemData_statement.bindString(33, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWdItemData_statement.bindString(34, jsonArray.getJSONObject(j).optString("Btg"));
                                            EtWcmWdItemData_statement.bindString(35, jsonArray.getJSONObject(j).optString("Etg"));
                                            EtWcmWdItemData_statement.bindString(36, jsonArray.getJSONObject(j).optString("Bug"));
                                            EtWcmWdItemData_statement.bindString(37, jsonArray.getJSONObject(j).optString("Eug"));
                                            EtWcmWdItemData_statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/


                        /*Reading and Inserting Data into Database Table for EtWcmWcagns*/
                            if (jsonObject.has("EtWcmWcagns")) {
                                try {
                                    String EtWcmWcagns_response_data = new Gson().toJson(rs.getD().getEtWcmWcagns().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWcagns_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWcmWcagns_sql = "Insert into EtWcmWcagns (UUID, Aufnr, Objnr, Counter, Objart, Objtyp, Pmsog, Gntxt, Geniakt, Genvname, Action, Werks, Crname, Hilvl, Procflg, Direction, Copyflg, Mandflg, Deacflg, Status, Asgnflg, Autoflg, Agent, Valflg, Wcmuse, Gendatum, Gentime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWcagns_statement = App_db.compileStatement(EtWcmWcagns_sql);
                                        EtWcmWcagns_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtWcmWcagns_statement.bindString(1, uuid);
                                                EtWcmWcagns_statement.bindString(2, Aufnr);
                                                EtWcmWcagns_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objnr"));
                                                EtWcmWcagns_statement.bindString(4, jsonArray.getJSONObject(j).optString("Counter"));
                                                EtWcmWcagns_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objart"));
                                                EtWcmWcagns_statement.bindString(6, jsonArray.getJSONObject(j).optString("Objtyp"));
                                                EtWcmWcagns_statement.bindString(7, jsonArray.getJSONObject(j).optString("Pmsog"));
                                                EtWcmWcagns_statement.bindString(8, jsonArray.getJSONObject(j).optString("Gntxt"));
                                                EtWcmWcagns_statement.bindString(9, jsonArray.getJSONObject(j).optString("Geniakt"));
                                                EtWcmWcagns_statement.bindString(10, jsonArray.getJSONObject(j).optString("Genvname"));
                                                EtWcmWcagns_statement.bindString(11, jsonArray.getJSONObject(j).optString("Action"));
                                                EtWcmWcagns_statement.bindString(12, jsonArray.getJSONObject(j).optString("Werks"));
                                                EtWcmWcagns_statement.bindString(13, jsonArray.getJSONObject(j).optString("Crname"));
                                                EtWcmWcagns_statement.bindString(14, jsonArray.getJSONObject(j).optString("Hilvl"));
                                                EtWcmWcagns_statement.bindString(15, jsonArray.getJSONObject(j).optString("Procflg"));
                                                EtWcmWcagns_statement.bindString(16, jsonArray.getJSONObject(j).optString("Direction"));
                                                EtWcmWcagns_statement.bindString(17, jsonArray.getJSONObject(j).optString("Copyflg"));
                                                EtWcmWcagns_statement.bindString(18, jsonArray.getJSONObject(j).optString("Mandflg"));
                                                EtWcmWcagns_statement.bindString(19, jsonArray.getJSONObject(j).optString("Deacflg"));
                                                EtWcmWcagns_statement.bindString(20, jsonArray.getJSONObject(j).optString("Status"));
                                                EtWcmWcagns_statement.bindString(21, jsonArray.getJSONObject(j).optString("Asgnflg"));
                                                EtWcmWcagns_statement.bindString(22, jsonArray.getJSONObject(j).optString("Autoflg"));
                                                EtWcmWcagns_statement.bindString(23, jsonArray.getJSONObject(j).optString("Agent"));
                                                EtWcmWcagns_statement.bindString(24, jsonArray.getJSONObject(j).optString("Valflg"));
                                                EtWcmWcagns_statement.bindString(25, jsonArray.getJSONObject(j).optString("Wcmuse"));
                                                EtWcmWcagns_statement.bindString(26, jsonArray.getJSONObject(j).optString("Gendatum"));
                                                EtWcmWcagns_statement.bindString(27, jsonArray.getJSONObject(j).optString("Gentime"));
                                                EtWcmWcagns_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWcagns*/


                        /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                            if (jsonObject.has("EtOrderComponents")) {
                                try {
                                    String EtOrderComponents_response_data = new Gson().toJson(rs.getD().getEtOrderComponents().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderComponents_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrderComponents_sql = "Insert into EtOrderComponents (UUID, Aufnr, Vornr, Uvorn, Rsnum, Rspos, Matnr, Werks, Lgort, Posnr, Bdmng, Meins, Postp, MatnrText, WerksText, LgortText, PostpText, Usr01, Usr02, Usr03, Usr04, Usr05, Action, Wempf, Ablad) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtOrderComponents_statement = App_db.compileStatement(EtOrderComponents_sql);
                                        EtOrderComponents_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                            for (int k = 0; k < orders_uuid_list.size(); k++) {
                                                String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                                if (Aufnr.equals(old_aufnr)) {
                                                    uuid = orders_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                EtOrderComponents_statement.bindString(1, uuid);
                                                EtOrderComponents_statement.bindString(2, Aufnr);
                                                EtOrderComponents_statement.bindString(3, jsonArray.getJSONObject(j).optString("Vornr"));
                                                EtOrderComponents_statement.bindString(4, jsonArray.getJSONObject(j).optString("Uvorn"));
                                                EtOrderComponents_statement.bindString(5, jsonArray.getJSONObject(j).optString("Rsnum"));
                                                EtOrderComponents_statement.bindString(6, jsonArray.getJSONObject(j).optString("Rspos"));
                                                EtOrderComponents_statement.bindString(7, jsonArray.getJSONObject(j).optString("Matnr"));
                                                EtOrderComponents_statement.bindString(8, jsonArray.getJSONObject(j).optString("Werks"));
                                                EtOrderComponents_statement.bindString(9, jsonArray.getJSONObject(j).optString("Lgort"));
                                                EtOrderComponents_statement.bindString(10, jsonArray.getJSONObject(j).optString("Posnr"));
                                                EtOrderComponents_statement.bindString(11, jsonArray.getJSONObject(j).optString("Bdmng"));
                                                EtOrderComponents_statement.bindString(12, jsonArray.getJSONObject(j).optString("Meins"));
                                                EtOrderComponents_statement.bindString(13, jsonArray.getJSONObject(j).optString("Postp"));
                                                EtOrderComponents_statement.bindString(14, jsonArray.getJSONObject(j).optString("MatnrText"));
                                                EtOrderComponents_statement.bindString(15, jsonArray.getJSONObject(j).optString("WerksText"));
                                                EtOrderComponents_statement.bindString(16, jsonArray.getJSONObject(j).optString("LgortText"));
                                                EtOrderComponents_statement.bindString(17, jsonArray.getJSONObject(j).optString("PostpText"));
                                                EtOrderComponents_statement.bindString(18, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtOrderComponents_statement.bindString(19, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtOrderComponents_statement.bindString(20, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtOrderComponents_statement.bindString(21, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtOrderComponents_statement.bindString(22, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtOrderComponents_statement.bindString(23, jsonArray.getJSONObject(j).optString("Action"));
                                                EtOrderComponents_statement.bindString(24, jsonArray.getJSONObject(j).optString("Wempf"));
                                                EtOrderComponents_statement.bindString(25, jsonArray.getJSONObject(j).optString("Ablad"));
                                                EtOrderComponents_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            App_db.setTransactionSuccessful();
                            App_db.endTransaction();
                            Get_Response = message.toString();
                        }
                    } catch (Exception e) {

                    }
                    /*Reading Data by using FOR Loop*/
                }
            } else {
                Get_Response = "Unable to Confirm Order. Please try again.";
            }
        } catch (Exception e) {
            Get_Response = "Unable to Confirm Order. Please try again.";
        }
        return Get_Response;
    }
}
