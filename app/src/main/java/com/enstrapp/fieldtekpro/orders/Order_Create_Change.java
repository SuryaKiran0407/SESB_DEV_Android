package com.enstrapp.fieldtekpro.orders;

import android.content.ContentValues;
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

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class Order_Create_Change {

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "",
            device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "",
            Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();
    private static Check_Empty checkempty = new Check_Empty();
    private static ArrayList<HashMap<String, String>> orders_uuid_list = new ArrayList<HashMap<String, String>>();
    private static StringBuffer message = new StringBuffer();
    private static boolean success = false;
    private static String created_aufnr = "";
    private static ArrayList<Model_CustomInfo> operation_custominfo = new ArrayList<>();
    private static ArrayList<Model_CustomInfo> material_custominfo = new ArrayList<>();

    public static String[] Post_Create_Order(Context activity, OrdrHeaderPrcbl ohp,
                                             String transmitType, String operation,
                                             String orderId, String type,
                                             ArrayList<Model_CustomInfo> header_custominfo,
                                             ArrayList<HashMap<String, String>>
                                                     operation_custom_info_arraylist,
                                             ArrayList<HashMap<String, String>>
                                                     material_custom_info_arraylist,
                                             String uniqeId) {
        try {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            token = app_sharedpreferences.getString("token", null);
            cookie = app_sharedpreferences.getString("cookie", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",
                    null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = " +
                    "? and Zactivity = ? and Endpoint = ?", new String[]{"W", "U", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
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
            ArrayList<ItWcmWdDataTagtext> ItWcmWdAddTag_Al = new ArrayList<>();
            ArrayList<ItWcmWdDataUntagtext> ItWcmWdAddUnTag_Al = new ArrayList<>();
            ArrayList<OrdrWcmWagnsSer> ItWcmWcagns_Al = new ArrayList<>();
            ArrayList<OrdrDocSer> ItDocs_Al = new ArrayList<>();
            /*ArrayList<OrdrGeoSer> IsGeo_Al = new ArrayList<>();
            IsGeo_Al.add(geoSer);*/


            if (type.equals("WOCO")) {
                OrdrHdrSer ordrHdrSer = new OrdrHdrSer();
                ordrHdrSer.setAufnr(orderId);
                ordrHdrSer.setXstatus(type);
                ItOrderHeader_Al.add(ordrHdrSer);
            } else {
                /*Objects for Assigning Notification Header and Sending Data in BODY*/
                Cursor cursor1 = null;
                OrdrHdrSer ordrHdrSer = new OrdrHdrSer();
                ordrHdrSer.setAufnr(ohp.getOrdrId());
                ordrHdrSer.setAuart(ohp.getOrdrTypId());
                ordrHdrSer.setKtext(ohp.getOrdrShrtTxt());
                ordrHdrSer.setIlart(ohp.getActivitytype_id());
                ordrHdrSer.setErnam(username.toUpperCase().toString());
                ordrHdrSer.setErdat(dateFormat(ohp.getBasicStart()));
                ordrHdrSer.setPriok(ohp.getPriorityId());
                ordrHdrSer.setEqunr(ohp.getEquipNum());
                ordrHdrSer.setStrno(ohp.getFuncLocId());
                ordrHdrSer.setTplnrInt(ohp.getFuncLocId());
                ordrHdrSer.setBautl("");
                ordrHdrSer.setGltrp(dateFormat(ohp.getBasicEnd()));
                ordrHdrSer.setGstrp(dateFormat(ohp.getBasicStart()));
                ordrHdrSer.setMsaus("");
                ordrHdrSer.setAnlzu(ohp.getSysCondId());
                ordrHdrSer.setAusvn("");
                ordrHdrSer.setAusbs("");
                ordrHdrSer.setQmnam("");
                ordrHdrSer.setAuswk("");
                ordrHdrSer.setParnrVw(ohp.getPerRespId());
                ordrHdrSer.setNameVw(ohp.getPerRespName());
                ordrHdrSer.setDocs("");
                ordrHdrSer.setPermits("");
                ordrHdrSer.setAltitude("");
                ordrHdrSer.setLatitude("");
                ordrHdrSer.setLongitude("");
                ordrHdrSer.setQmnum(ohp.getNotifId());
                ordrHdrSer.setQcreate("");
                ordrHdrSer.setClosed("");
                ordrHdrSer.setCompleted("");
                ordrHdrSer.setWcm("");
                ordrHdrSer.setWsm("");
                ordrHdrSer.setIngrp(ohp.getPlnrGrpId());
                ordrHdrSer.setArbpl(ohp.getWrkCntrId());
                ordrHdrSer.setWerks(ohp.getPlant());
                ordrHdrSer.setBemot("");
                ordrHdrSer.setAueru("");
                ordrHdrSer.setAuarttext(ohp.getOrdrTypName());
                ordrHdrSer.setQmartx("");
                ordrHdrSer.setQmtxt("");
                ordrHdrSer.setPltxt(ohp.getFuncLocName());
                ordrHdrSer.setEqktx(ohp.getEquipName());
                ordrHdrSer.setPriokx(ohp.getPriorityTxt());
                ordrHdrSer.setIlatx(ohp.getActivitytype_text());
                ordrHdrSer.setPlantname(ohp.getPlantName());
                ordrHdrSer.setWkctrname(ohp.getWrkCntrName());
                ordrHdrSer.setIngrpname(ohp.getPlnrGrpName());
                ordrHdrSer.setMaktx("");
                ordrHdrSer.setAnlzux("");
                ordrHdrSer.setXstatus("");
                ordrHdrSer.setKokrs("");
                ordrHdrSer.setKostl(ohp.getRespCostCntrId());
                ordrHdrSer.setPosid(ohp.getPosid());
                ordrHdrSer.setRevnr(ohp.getRevnr());
                ordrHdrSer.setUsr01("");
                ordrHdrSer.setUsr02("");
                ordrHdrSer.setUsr03("");
                ordrHdrSer.setUsr04("");
                ordrHdrSer.setUsr05("");
                ordrHdrSer.setItOrderHeaderFields(header_custominfo);
                ItOrderHeader_Al.add(ordrHdrSer);


                /*Adding Order Longtext to Arraylist*/
                String order_header_longtext = ohp.getOrdrLngTxt();
                if (order_header_longtext != null && !order_header_longtext.equals("")) {
                    if (order_header_longtext.contains("\n")) {
                        String[] longtext_array = order_header_longtext.split("\n");
                        for (int i = 0; i < longtext_array.length; i++) {
                            OrdrLngTxtSer mnc = new OrdrLngTxtSer();
                            mnc.setAufnr(ohp.getOrdrId());
                            mnc.setActivity("");
                            mnc.setTextLine(longtext_array[i]);
                            mnc.setTdid("");
                            ItOrderLongtext_Al.add(mnc);
                        }
                    } else {
                        OrdrLngTxtSer mnc = new OrdrLngTxtSer();
                        mnc.setAufnr(ohp.getOrdrId());
                        mnc.setActivity("");
                        mnc.setTextLine(order_header_longtext);
                        mnc.setTdid("");
                        ItOrderLongtext_Al.add(mnc);
                    }
                }
                /*Adding Order Longtext to Arraylist*/

                if (ohp.getOrdrOprtnPrcbls() != null)
                    for (int i = 0; i < ohp.getOrdrOprtnPrcbls().size(); i++) {
                        OrdrOprtnSer oprtnSer = new OrdrOprtnSer();
                        oprtnSer.setAufnr(ohp.getOrdrOprtnPrcbls().get(i).getOrdrId());
                        oprtnSer.setVornr(ohp.getOrdrOprtnPrcbls().get(i).getOprtnId());
                        oprtnSer.setUvorn("");
                        oprtnSer.setLtxa1(ohp.getOrdrOprtnPrcbls().get(i).getOprtnShrtTxt());
                        oprtnSer.setArbpl(ohp.getOrdrOprtnPrcbls().get(i).getWrkCntrId());
                        oprtnSer.setWerks(ohp.getOrdrOprtnPrcbls().get(i).getPlantId());
                        oprtnSer.setSteus(ohp.getOrdrOprtnPrcbls().get(i).getCntrlKeyId());
                        oprtnSer.setLarnt(ohp.getOrdrOprtnPrcbls().get(i).getLarnt());
                        oprtnSer.setDauno(ohp.getOrdrOprtnPrcbls().get(i).getDuration());
                        oprtnSer.setDaune(ohp.getOrdrOprtnPrcbls().get(i).getDurationUnit());
                        oprtnSer.setFsavd(ohp.getOrdrOprtnPrcbls().get(i).getFsavd());
                        oprtnSer.setSsedd(ohp.getOrdrOprtnPrcbls().get(i).getSsedd());
                        oprtnSer.setPernr("");
                        oprtnSer.setAsnum("");
                        oprtnSer.setPlnty("");
                        oprtnSer.setPlnal("");
                        oprtnSer.setPlnnr("");
                        oprtnSer.setRueck(ohp.getOrdrOprtnPrcbls().get(i).getRueck());
                        oprtnSer.setAueru(ohp.getOrdrOprtnPrcbls().get(i).getAueru());
                        oprtnSer.setArbplText(ohp.getOrdrOprtnPrcbls().get(i).getWrkCntrTxt());
                        oprtnSer.setWerksText(ohp.getOrdrOprtnPrcbls().get(i).getPlantTxt());
                        oprtnSer.setSteusText(ohp.getOrdrOprtnPrcbls().get(i).getCntrlKeyTxt());
                        oprtnSer.setLarntText("");
                        oprtnSer.setUsr01("");
                        oprtnSer.setUsr02("");
                        oprtnSer.setUsr03("");
                        oprtnSer.setUsr04("");
                        oprtnSer.setUsr05("");
                        oprtnSer.setAction(ohp.getOrdrOprtnPrcbls().get(i).getStatus());

                        /*Adding Operation Longtext to Arraylist*/
                        String operation_longtext = ohp.getOrdrOprtnPrcbls().get(i).getOprtnLngTxt();
                        if (operation_longtext != null && !operation_longtext.equals("")) {
                            if (operation_longtext.contains("\n")) {
                                String[] longtext_array = operation_longtext.split("\n");
                                for (int j = 0; j < longtext_array.length; j++) {
                                    OrdrLngTxtSer mnc = new OrdrLngTxtSer();
                                    mnc.setAufnr(ohp.getOrdrId());
                                    mnc.setActivity(ohp.getOrdrOprtnPrcbls().get(i).getOprtnId());
                                    mnc.setTextLine(longtext_array[j]);
                                    mnc.setTdid("");
                                    ItOrderLongtext_Al.add(mnc);
                                }
                            } else {
                                OrdrLngTxtSer mnc = new OrdrLngTxtSer();
                                mnc.setAufnr(ohp.getOrdrId());
                                mnc.setActivity(ohp.getOrdrOprtnPrcbls().get(i).getOprtnId());
                                mnc.setTextLine(operation_longtext);
                                mnc.setTdid("");
                                ItOrderLongtext_Al.add(mnc);
                            }
                        }
                        /*Adding Operation Longtext to Arraylist*/

                        operation_custominfo = new ArrayList<>();
                        String operation_id = ohp.getOrdrOprtnPrcbls().get(i).getOprtnId();
                        if (operation_custom_info_arraylist.size() > 0) {
                            for (int j = 0; j < operation_custom_info_arraylist.size(); j++) {
                                String op_id = operation_custom_info_arraylist.get(j).get("Operation_id");
                                if (op_id.equalsIgnoreCase(operation_id)) {
                                    Model_CustomInfo mnc = new Model_CustomInfo();
                                    mnc.setZdoctype(operation_custom_info_arraylist.get(j).get("Zdoctype"));
                                    mnc.setZdoctypeItem(operation_custom_info_arraylist.get(j).get("ZdoctypeItem"));
                                    mnc.setTabname(operation_custom_info_arraylist.get(j).get("Tabname"));
                                    mnc.setFieldname(operation_custom_info_arraylist.get(j).get("Fieldname"));
                                    mnc.setDatatype(operation_custom_info_arraylist.get(j).get("Datatype"));
                                    String datatype = operation_custom_info_arraylist.get(j).get("Datatype");
                                    if (datatype.equalsIgnoreCase("DATS")) {
                                        String value = operation_custom_info_arraylist.get(j).get("Value");
                                        String inputPattern = "MMM dd, yyyy";
                                        String outputPattern = "yyyyMMdd";
                                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                        try {
                                            Date date = inputFormat.parse(value);
                                            String formatted_date = outputFormat.format(date);
                                            mnc.setValue(formatted_date);
                                        } catch (Exception e) {
                                            mnc.setValue("");
                                        }
                                    } else if (datatype.equalsIgnoreCase("TIMS")) {
                                        String value = operation_custom_info_arraylist.get(j).get("Value");
                                        String inputPattern = "HH:mm:ss";
                                        String outputPattern = "HHmmss";
                                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                        try {
                                            Date date = inputFormat.parse(value);
                                            String formatted_date = outputFormat.format(date);
                                            mnc.setValue(formatted_date);
                                        } catch (Exception e) {
                                            mnc.setValue("");
                                        }
                                    } else {
                                        mnc.setValue(operation_custom_info_arraylist.get(j).get("Value"));
                                    }
                                    mnc.setFlabel(operation_custom_info_arraylist.get(j).get("Flabel"));
                                    mnc.setSequence(operation_custom_info_arraylist.get(j).get("Sequence"));
                                    mnc.setLength(operation_custom_info_arraylist.get(j).get("Length"));
                                    operation_custominfo.add(mnc);
                                }
                            }
                            oprtnSer.setItOrderOperationFields(operation_custominfo);
                        } else {
                            oprtnSer.setItOrderOperationFields(operation_custominfo);
                        }

                        ItOrderOperations_Al.add(oprtnSer);
                    }

                if (ohp.getOrdrMatrlPrcbls() != null)
                    for (int i = 0; i < ohp.getOrdrMatrlPrcbls().size(); i++) {
                        OrdrMatrlSer matrlSer = new OrdrMatrlSer();
                        matrlSer.setAufnr(ohp.getOrdrMatrlPrcbls().get(i).getAufnr());
                        matrlSer.setVornr(ohp.getOrdrMatrlPrcbls().get(i).getOprtnId());
                        matrlSer.setUvorn("");
                        matrlSer.setRsnum(ohp.getOrdrMatrlPrcbls().get(i).getRsnum());
                        matrlSer.setRspos(ohp.getOrdrMatrlPrcbls().get(i).getRspos());
                        matrlSer.setMatnr(ohp.getOrdrMatrlPrcbls().get(i).getMatrlId());
                        matrlSer.setWerks(ohp.getOrdrMatrlPrcbls().get(i).getPlantId());
                        matrlSer.setLgort(ohp.getOrdrMatrlPrcbls().get(i).getLocation());
                        matrlSer.setPosnr(ohp.getOrdrMatrlPrcbls().get(i).getPartId());
                        matrlSer.setBdmng(ohp.getOrdrMatrlPrcbls().get(i).getQuantity());
                        matrlSer.setMeins(ohp.getOrdrMatrlPrcbls().get(i).getMeins());
                        matrlSer.setPostp(ohp.getOrdrMatrlPrcbls().get(i).getPostp());
                        matrlSer.setWempf(ohp.getOrdrMatrlPrcbls().get(i).getReceipt());
                        matrlSer.setAblad(ohp.getOrdrMatrlPrcbls().get(i).getUnloading());
                        matrlSer.setMatnrText(ohp.getOrdrMatrlPrcbls().get(i).getMatrlTxt());
                        matrlSer.setWerksText(ohp.getOrdrMatrlPrcbls().get(i).getPlantTxt());
                        matrlSer.setLgortText(ohp.getOrdrMatrlPrcbls().get(i).getLocationTxt());
                        matrlSer.setPostpText(ohp.getOrdrMatrlPrcbls().get(i).getPostpTxt());
                        matrlSer.setUsr01("");
                        matrlSer.setUsr02("");
                        matrlSer.setUsr03("");
                        matrlSer.setUsr04("");
                        matrlSer.setUsr05("");
                        matrlSer.setAction(ohp.getOrdrMatrlPrcbls().get(i).getStatus());

                        material_custominfo = new ArrayList<>();
                        String operation_id = ohp.getOrdrMatrlPrcbls().get(i).getOprtnId();
                        String part_id = ohp.getOrdrMatrlPrcbls().get(i).getPartId();
                        if (material_custom_info_arraylist.size() > 0) {
                            for (int j = 0; j < material_custom_info_arraylist.size(); j++) {
                                String op_id = material_custom_info_arraylist.get(j).get("Operation_id");
                                String p_id = material_custom_info_arraylist.get(j).get("Part_id");
                                if (op_id.equalsIgnoreCase(operation_id) && p_id.equalsIgnoreCase(part_id)) {
                                    Model_CustomInfo mnc = new Model_CustomInfo();
                                    mnc.setZdoctype(material_custom_info_arraylist.get(j).get("Zdoctype"));
                                    mnc.setZdoctypeItem(material_custom_info_arraylist.get(j).get("ZdoctypeItem"));
                                    mnc.setTabname(material_custom_info_arraylist.get(j).get("Tabname"));
                                    mnc.setFieldname(material_custom_info_arraylist.get(j).get("Fieldname"));
                                    mnc.setDatatype(material_custom_info_arraylist.get(j).get("Datatype"));
                                    String datatype = material_custom_info_arraylist.get(j).get("Datatype");
                                    if (datatype.equalsIgnoreCase("DATS")) {
                                        String value = material_custom_info_arraylist.get(j).get("Value");
                                        String inputPattern = "MMM dd, yyyy";
                                        String outputPattern = "yyyyMMdd";
                                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                        try {
                                            Date date = inputFormat.parse(value);
                                            String formatted_date = outputFormat.format(date);
                                            mnc.setValue(formatted_date);
                                        } catch (Exception e) {
                                            mnc.setValue("");
                                        }
                                    } else if (datatype.equalsIgnoreCase("TIMS")) {
                                        String value = material_custom_info_arraylist.get(j).get("Value");
                                        String inputPattern = "HH:mm:ss";
                                        String outputPattern = "HHmmss";
                                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                        try {
                                            Date date = inputFormat.parse(value);
                                            String formatted_date = outputFormat.format(date);
                                            mnc.setValue(formatted_date);
                                        } catch (Exception e) {
                                            mnc.setValue("");
                                        }
                                    } else {
                                        mnc.setValue(material_custom_info_arraylist.get(j).get("Value"));
                                    }
                                    mnc.setFlabel(material_custom_info_arraylist.get(j).get("Flabel"));
                                    mnc.setSequence(material_custom_info_arraylist.get(j).get("Sequence"));
                                    mnc.setLength(material_custom_info_arraylist.get(j).get("Length"));
                                    material_custominfo.add(mnc);
                                }
                            }
                            matrlSer.setItOrderComponentsFields(material_custominfo);
                        } else {
                            matrlSer.setItOrderComponentsFields(material_custominfo);
                        }

                        ItOrderComponents_Al.add(matrlSer);
                    }


                OrdrPermitSer permitSer = new OrdrPermitSer();
                permitSer.setAufnr("");
                permitSer.setVornr("");
                permitSer.setPermit("");
                permitSer.setGntxt("");
                permitSer.setGntyp("");
                permitSer.setRelease("");
                permitSer.setComplete("");
                permitSer.setNotRelevant("");
                permitSer.setIssuedBy("");
                permitSer.setUsr01("");
                permitSer.setUsr02("");
                permitSer.setUsr03("");
                permitSer.setAction("");
                ItOrderPermits_Al.add(permitSer);

                if (ohp.getOrdrObjectPrcbls() != null)
                    for (int i = 0; i < ohp.getOrdrObjectPrcbls().size(); i++) {
                        OrdrOlistSer olistSer = new OrdrOlistSer();
                        olistSer.setAufnr(ohp.getOrdrObjectPrcbls().get(i).getAufnr());
                        olistSer.setObknr(ohp.getOrdrObjectPrcbls().get(i).getObknr());
                        olistSer.setObzae(ohp.getOrdrObjectPrcbls().get(i).getObzae());
                        olistSer.setQmnum(ohp.getOrdrObjectPrcbls().get(i).getNotifNo());
                        olistSer.setEqunr(ohp.getOrdrObjectPrcbls().get(i).getEquipId());
                        olistSer.setStrno(ohp.getOrdrObjectPrcbls().get(i).getStrno());
                        olistSer.setTplnr(ohp.getOrdrObjectPrcbls().get(i).getTplnr());
                        olistSer.setBautl(ohp.getOrdrObjectPrcbls().get(i).getBautl());
                        olistSer.setQmtxt(ohp.getOrdrObjectPrcbls().get(i).getQmtxt());
                        olistSer.setPltxt(ohp.getOrdrObjectPrcbls().get(i).getPltxt());
                        olistSer.setEqktx(ohp.getOrdrObjectPrcbls().get(i).getEquipTxt());
                        olistSer.setMaktx(ohp.getOrdrObjectPrcbls().get(i).getMaktx());
                        olistSer.setAction(ohp.getOrdrObjectPrcbls().get(i).getAction());
                        ItOrderOlist_Al.add(olistSer);
                    }

                if (ohp.getOrdrStatusPrcbls() != null)
                    for (int i = 0; i < ohp.getOrdrStatusPrcbls().size(); i++) {
                        OrdrStatusSer statusSer = new OrdrStatusSer();
                        statusSer.setAufnr(ohp.getOrdrStatusPrcbls().get(i).getAufnr());
                        statusSer.setVornr(ohp.getOrdrStatusPrcbls().get(i).getVornr());
                        statusSer.setObjnr(ohp.getOrdrStatusPrcbls().get(i).getObjnr());
                        statusSer.setStsma(ohp.getOrdrStatusPrcbls().get(i).getStsma());
                        statusSer.setInist(ohp.getOrdrStatusPrcbls().get(i).getInist());
                        statusSer.setStonr(ohp.getOrdrStatusPrcbls().get(i).getStonr());
                        statusSer.setHsonr(ohp.getOrdrStatusPrcbls().get(i).getHsonr());
                        statusSer.setNsonr(ohp.getOrdrStatusPrcbls().get(i).getNsonr());
                        statusSer.setStat(ohp.getOrdrStatusPrcbls().get(i).getStat());
                        statusSer.setAct(ohp.getOrdrStatusPrcbls().get(i).getAct());
                        statusSer.setTxt04(ohp.getOrdrStatusPrcbls().get(i).getTxt04());
                        statusSer.setTxt30(ohp.getOrdrStatusPrcbls().get(i).getTxt30());
                        statusSer.setAction(ohp.getOrdrStatusPrcbls().get(i).getAction());
                        ItOrderStatus_Al.add(statusSer);
                    }

                if (ohp.getOrdrPermitPrcbls() != null)
                    for (int i = 0; i < ohp.getOrdrPermitPrcbls().size(); i++) {
                        OrdrWcmWwSer wcmWwSer = new OrdrWcmWwSer();
                        wcmWwSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getAufnr());
                        wcmWwSer.setObjart(ohp.getOrdrPermitPrcbls().get(i).getObjart());
                        wcmWwSer.setIwerk(ohp.getOrdrPermitPrcbls().get(i).getIwerk());
                        wcmWwSer.setWapnr(ohp.getOrdrPermitPrcbls().get(i).getWapnr());
                        wcmWwSer.setDatefr(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getDatefr()));
                        wcmWwSer.setTimefr(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getTimefr()));
                        wcmWwSer.setDateto(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getDateto()));
                        wcmWwSer.setTimeto(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getTimeto()));
                        wcmWwSer.setStxt(ohp.getOrdrPermitPrcbls().get(i).getStxt());
                        wcmWwSer.setUsage(ohp.getOrdrPermitPrcbls().get(i).getUsage());
                        wcmWwSer.setUsagex(ohp.getOrdrPermitPrcbls().get(i).getUsagex());
                        wcmWwSer.setTrain(ohp.getOrdrPermitPrcbls().get(i).getTrain());
                        wcmWwSer.setTrainx(ohp.getOrdrPermitPrcbls().get(i).getTrainx());
                        wcmWwSer.setAnlzu(ohp.getOrdrPermitPrcbls().get(i).getAnlzu());
                        wcmWwSer.setAnlzux(ohp.getOrdrPermitPrcbls().get(i).getAnlzux());
                        wcmWwSer.setEtape(ohp.getOrdrPermitPrcbls().get(i).getEtape());
                        wcmWwSer.setEtapex(ohp.getOrdrPermitPrcbls().get(i).getEtapex());
                        wcmWwSer.setRctime(ohp.getOrdrPermitPrcbls().get(i).getRctime());
                        wcmWwSer.setRcunit(ohp.getOrdrPermitPrcbls().get(i).getRcunit());
                        wcmWwSer.setPriok(ohp.getOrdrPermitPrcbls().get(i).getPriok());
                        wcmWwSer.setPriokx(ohp.getOrdrPermitPrcbls().get(i).getPriokx());
                        wcmWwSer.setBegru(ohp.getOrdrPermitPrcbls().get(i).getBegru());
                        wcmWwSer.setBegtx(ohp.getOrdrPermitPrcbls().get(i).getBegtx());
                        wcmWwSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getObjnr());
                        wcmWwSer.setCrea(ohp.getOrdrPermitPrcbls().get(i).getCrea());
                        wcmWwSer.setPrep(ohp.getOrdrPermitPrcbls().get(i).getPrep());
                        wcmWwSer.setComp(ohp.getOrdrPermitPrcbls().get(i).getComp());
                        wcmWwSer.setAppr(ohp.getOrdrPermitPrcbls().get(i).getAppr());
                        wcmWwSer.setPappr(ohp.getOrdrPermitPrcbls().get(i).getPappr());
                        wcmWwSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getAction());

                        if (ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al() != null)
                            for (int j = 0; j < ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().size(); j++) {
                                OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                                wcmWagnsSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getAufnr());
                                wcmWagnsSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getObjnr());
                                wcmWagnsSer.setCounter(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getCounter());
                                wcmWagnsSer.setWerks(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getWerks());
                                wcmWagnsSer.setCrname(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getCrname());
                                wcmWagnsSer.setObjart(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getObjart());
                                wcmWagnsSer.setObjtyp(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getObjtyp());
                                wcmWagnsSer.setPmsog(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getPmsog());
                                wcmWagnsSer.setGntxt(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getGntxt());
                                wcmWagnsSer.setGeniakt(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getGeniakt());
                                wcmWagnsSer.setGenvname(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getGenvname());
                                wcmWagnsSer.setGendatum(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getGendatum());
                                wcmWagnsSer.setGentime(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getGentime());
                                wcmWagnsSer.setHilvl(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getHilvl());
                                wcmWagnsSer.setProcflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getProcflg());
                                wcmWagnsSer.setDirection(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getDirection());
                                wcmWagnsSer.setCopyflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getCopyflg());
                                wcmWagnsSer.setMandflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getMandflg());
                                wcmWagnsSer.setDeacflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getDeacflg());
                                wcmWagnsSer.setStatus(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getStatus());
                                wcmWagnsSer.setAsgnflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getAsgnflg());
                                wcmWagnsSer.setAutoflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getAutoflg());
                                wcmWagnsSer.setAgent(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getAgent());
                                wcmWagnsSer.setValflg(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getValflg());
                                wcmWagnsSer.setWcmuse(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getWcmuse());
                                wcmWagnsSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWcagnsPrcbl_Al().get(j).getAction());
                                ItWcmWcagns_Al.add(wcmWagnsSer);
                            }

                        if (ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al() != null)
                            for (int j = 0; j < ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().size(); j++) {
                                OrdrStatusSer statusSer = new OrdrStatusSer();
                                statusSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getAufnr());
                                statusSer.setVornr(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getVornr());
                                statusSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getObjnr());
                                statusSer.setStsma(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getStsma());
                                statusSer.setInist(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getInist());
                                statusSer.setStonr(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getStonr());
                                statusSer.setHsonr(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getHsonr());
                                statusSer.setNsonr(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getNsonr());
                                statusSer.setStat(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getStat());
                                statusSer.setAct(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getAct());
                                statusSer.setTxt04(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getTxt04());
                                statusSer.setTxt30(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getTxt30());
                                statusSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getStatusPrcbl_Al().get(j).getAction());
//                                ItOrderStatus_Al.add(statusSer);
                            }

                        if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al() != null)
                            for (int j = 0; j < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().size(); j++) {
                                OrdrWcmWaSer wcmWaSer = new OrdrWcmWaSer();
                                wcmWaSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getAufnr());
                                wcmWaSer.setObjart(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getObjart());
                                wcmWaSer.setObjtyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getObjtyp());
                                wcmWaSer.setIwerk(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getIwerk());
                                wcmWaSer.setWapinr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWapinr());
                                wcmWaSer.setStxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStxt());
                                wcmWaSer.setDatefr(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getDatefr()));
                                wcmWaSer.setTimefr(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getTimefr()));
                                wcmWaSer.setDateto(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getDateto()));
                                wcmWaSer.setTimeto(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getTimeto()));
                                wcmWaSer.setExtperiod(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getExtperiod());
                                wcmWaSer.setUsage(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getUsage());
                                wcmWaSer.setUsagex(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getUsagex());
                                wcmWaSer.setTrain(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getTrain());
                                wcmWaSer.setTrainx(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getTrainx());
                                wcmWaSer.setAnlzu(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getAnlzu());
                                wcmWaSer.setAnlzux(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getAnlzux());
                                wcmWaSer.setEtape(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getEtape());
                                wcmWaSer.setEtapex(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getEtapex());
                                wcmWaSer.setBegru(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getBegru());
                                wcmWaSer.setBegtx(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getBegtx());
                                wcmWaSer.setPriok(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getPriok());
                                wcmWaSer.setPriokx(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getPriokx());
                                wcmWaSer.setRctime(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getRctime());
                                wcmWaSer.setRcunit(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getRcunit());
                                wcmWaSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getObjnr());
                                wcmWaSer.setRefobj(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getRefobj());
                                wcmWaSer.setCrea(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getCrea());
                                wcmWaSer.setPrep(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getPrep());
                                wcmWaSer.setComp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getComp());
                                wcmWaSer.setAppr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getAppr());
                                wcmWaSer.setPappr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getPappr());
                                wcmWaSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getAction());

                                try {
                                    cursor1 = App_db.rawQuery("select * from Orders_Attachments where Object_id = ?", new String[]{ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWapinr()});
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
                                                    ods.setZobjid(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWapinr());
                                                    ods.setZdoctype("W");
                                                    ods.setZdoctypeItem("WH");
                                                    ods.setFilename(filee_name);
                                                    ods.setFiletype(fileExtension);
                                                    ods.setFsize(String.valueOf(file_size));
                                                    ods.setContent(encodedImage);
                                                    ods.setDocId("");
                                                    ods.setDocType("");
                                                    ods.setObjtype("WCA");
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

                                if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al() != null)
                                    for (int k = 0; k < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().size(); k++) {
                                        OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                                        wcmWagnsSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getAufnr());
                                        wcmWagnsSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getObjnr());
                                        wcmWagnsSer.setCounter(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getCounter());
                                        wcmWagnsSer.setWerks(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getWerks());
                                        wcmWagnsSer.setCrname(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getCrname());
                                        wcmWagnsSer.setObjart(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getObjart());
                                        wcmWagnsSer.setObjtyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getObjtyp());
                                        wcmWagnsSer.setPmsog(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getPmsog());
                                        wcmWagnsSer.setGntxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getGntxt());
                                        wcmWagnsSer.setGeniakt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getGeniakt());
                                        wcmWagnsSer.setGenvname(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getGenvname());
                                        wcmWagnsSer.setGendatum(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getGendatum()));
                                        wcmWagnsSer.setGentime(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getGentime()));
                                        wcmWagnsSer.setHilvl(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getHilvl());
                                        wcmWagnsSer.setProcflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getProcflg());
                                        wcmWagnsSer.setDirection(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getDirection());
                                        wcmWagnsSer.setCopyflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getCopyflg());
                                        wcmWagnsSer.setMandflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getMandflg());
                                        wcmWagnsSer.setDeacflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getDeacflg());
                                        wcmWagnsSer.setStatus(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getStatus());
                                        wcmWagnsSer.setAsgnflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getAsgnflg());
                                        wcmWagnsSer.setAutoflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getAutoflg());
                                        wcmWagnsSer.setAgent(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getAgent());
                                        wcmWagnsSer.setValflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getValflg());
                                        wcmWagnsSer.setWcmuse(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getWcmuse());
                                        wcmWagnsSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWcagnsPrcbl_Al().get(k).getAction());
                                        ItWcmWcagns_Al.add(wcmWagnsSer);
                                    }

                                if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al() != null)
                                    for (int k = 0; k < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().size(); k++) {
                                        OrdrStatusSer statusSer = new OrdrStatusSer();
                                        statusSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getAufnr());
                                        statusSer.setVornr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getVornr());
                                        statusSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getObjnr());
                                        statusSer.setStsma(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getStsma());
                                        statusSer.setInist(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getInist());
                                        statusSer.setStonr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getStonr());
                                        statusSer.setHsonr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getHsonr());
                                        statusSer.setNsonr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getNsonr());
                                        statusSer.setStat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getStat());
                                        statusSer.setAct(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getAct());
                                        statusSer.setTxt04(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getTxt04());
                                        statusSer.setTxt30(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getTxt30());
                                        statusSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getStatusPrcbl_Al().get(k).getAction());
//                                        ItOrderStatus_Al.add(statusSer);
                                    }

                                if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al() != null)
                                    for (int k = 0; k < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().size(); k++) {
                                        OrdrWcmChkRqSer wcmChkRqSer = new OrdrWcmChkRqSer();
                                        wcmChkRqSer.setWapinr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getWapinr());
                                        wcmChkRqSer.setWapityp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getWapityp());
                                        wcmChkRqSer.setChkPointType(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getChkPointType());
                                        wcmChkRqSer.setWkid(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getWkid());
                                        wcmChkRqSer.setNeedid(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getNeedid());
                                        wcmChkRqSer.setValue(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getValue());
                                        wcmChkRqSer.setDesctext(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getDesctext());
                                        wcmChkRqSer.setWkgrp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getWkgrp());
                                        wcmChkRqSer.setNeedgrp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getNeedgrp());
                                        wcmChkRqSer.setTplnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getTplnr());
                                        wcmChkRqSer.setEqunr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaChkReqPrcbl_Al().get(k).getEqunr());
                                        ItWcmWaChkReq_Al.add(wcmChkRqSer);
                                    }

                                if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al() != null)
                                    for (int k = 0; k < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().size(); k++) {
                                        if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al() != null) {
                                            for (int l = 0; l < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().size(); l++) {
                                                ItWcmWdDataTagtext tagSer = new ItWcmWdDataTagtext();
                                                tagSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().get(l).getAufnr());
                                                tagSer.setWcnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().get(l).getWcnr());
                                                tagSer.setObjtype(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().get(l).getObjtype());
                                                tagSer.setFormatCol(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().get(l).getFormatCol());
                                                tagSer.setTextLine(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().get(l).getTextLine());
                                                tagSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().get(l).getAction());
                                                ItWcmWdAddTag_Al.add(tagSer);
                                            }
                                        }
                                        if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al() != null) {
                                            for (int l = 0; l < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().size(); l++) {
                                                ItWcmWdDataUntagtext unTagSer = new ItWcmWdDataUntagtext();
                                                unTagSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().get(l).getAufnr());
                                                unTagSer.setWcnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().get(l).getWcnr());
                                                unTagSer.setObjtype(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().get(l).getObjtype());
                                                unTagSer.setFormatCol(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().get(l).getFormatCol());
                                                unTagSer.setTextLine(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().get(l).getTextLine());
                                                unTagSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUnTagText_al().get(l).getAction());
                                                ItWcmWdAddUnTag_Al.add(unTagSer);
                                            }
                                        }
                                        OrdrWcmWdSer wcmWdSer = new OrdrWcmWdSer();
                                        wcmWdSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getAufnr());
                                        wcmWdSer.setObjart(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getObjart());
                                        wcmWdSer.setWcnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcnr());
                                        wcmWdSer.setIwerk(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getIwerk());
                                        wcmWdSer.setObjtyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getObjtyp());
                                        wcmWdSer.setUsage(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUsage());
                                        wcmWdSer.setUsagex(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getUsagex());
                                        wcmWdSer.setTrain(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTrain());
                                        wcmWdSer.setTrainx(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTrainx());
                                        wcmWdSer.setAnlzu(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getAnlzu());
                                        wcmWdSer.setAnlzux(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getAnlzux());
                                        wcmWdSer.setEtape(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getEtape());
                                        wcmWdSer.setEtapex(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getEtapex());
                                        wcmWdSer.setStxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStxt());
                                        wcmWdSer.setDatefr(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getDatefr()));
                                        wcmWdSer.setTimefr(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTimefr()));
                                        wcmWdSer.setDateto(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getDateto()));
                                        wcmWdSer.setTimeto(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTimeto()));
                                        wcmWdSer.setBegru(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getBegru());
                                        wcmWdSer.setBegtx(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getBegtx());
                                        wcmWdSer.setPriok(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getPriok());
                                        wcmWdSer.setPriokx(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getPriokx());
                                        wcmWdSer.setRctime(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getRctime());
                                        wcmWdSer.setRcunit(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getRcunit());
                                        wcmWdSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getObjnr());
                                        wcmWdSer.setRefobj(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getRefobj());
                                        wcmWdSer.setCrea(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getCrea());
                                        wcmWdSer.setPrep(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getPrep());
                                        wcmWdSer.setComp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getComp());
                                        wcmWdSer.setAppr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getAppr());
                                        wcmWdSer.setPappr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getPappr());
                                        wcmWdSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getAction());
                                        wcmWdSer.setItWcmWdDataTagtext(ItWcmWdAddTag_Al);
                                        wcmWdSer.setItWcmWdDataUntagtext(ItWcmWdAddUnTag_Al);

                                        if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al() != null)
                                            for (int l = 0; l < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().size(); l++) {
                                                OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                                                wcmWagnsSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getAufnr());
                                                wcmWagnsSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getObjnr());
                                                wcmWagnsSer.setCounter(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getCounter());
                                                wcmWagnsSer.setWerks(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getWerks());
                                                wcmWagnsSer.setCrname(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getCrname());
                                                wcmWagnsSer.setObjart(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getObjart());
                                                wcmWagnsSer.setObjtyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getObjtyp());
                                                wcmWagnsSer.setPmsog(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getPmsog());
                                                wcmWagnsSer.setGntxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getGntxt());
                                                wcmWagnsSer.setGeniakt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getGeniakt());
                                                wcmWagnsSer.setGenvname(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getGenvname());
                                                wcmWagnsSer.setGendatum(dateFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getGendatum()));
                                                wcmWagnsSer.setGentime(timeFormat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getGentime()));
                                                wcmWagnsSer.setHilvl(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getHilvl());
                                                wcmWagnsSer.setProcflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getProcflg());
                                                wcmWagnsSer.setDirection(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getDirection());
                                                wcmWagnsSer.setCopyflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getCopyflg());
                                                wcmWagnsSer.setMandflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getMandflg());
                                                wcmWagnsSer.setDeacflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getDeacflg());
                                                wcmWagnsSer.setStatus(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getStatus());
                                                wcmWagnsSer.setAsgnflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getAsgnflg());
                                                wcmWagnsSer.setAutoflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getAutoflg());
                                                wcmWagnsSer.setAgent(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getAgent());
                                                wcmWagnsSer.setValflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getValflg());
                                                wcmWagnsSer.setWcmuse(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getWcmuse());
                                                wcmWagnsSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l).getAction());
                                                ItWcmWcagns_Al.add(wcmWagnsSer);
                                            }

                                        if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al() != null)
                                            for (int l = 0; l < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().size(); l++) {
                                                OrdrStatusSer statusSer = new OrdrStatusSer();
                                                statusSer.setAufnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getAufnr());
                                                statusSer.setVornr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getVornr());
                                                statusSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getObjnr());
                                                statusSer.setStsma(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getStsma());
                                                statusSer.setInist(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getInist());
                                                statusSer.setStonr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getStonr());
                                                statusSer.setHsonr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getHsonr());
                                                statusSer.setNsonr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getNsonr());
                                                statusSer.setStat(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getStat());
                                                statusSer.setAct(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getAct());
                                                statusSer.setTxt04(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getTxt04());
                                                statusSer.setTxt30(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getTxt30());
                                                statusSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getStatusPrcbl_Al().get(l).getAction());
//                                                ItOrderStatus_Al.add(statusSer);
                                            }

                                        if (ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al() != null)
                                            for (int l = 0; l < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().size(); l++) {
                                                OrdrWcmWdItmSer wcmWdItmSer = new OrdrWcmWdItmSer();
                                                wcmWdItmSer.setWcnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getWcnr());
                                                wcmWdItmSer.setWcitm(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getWctim());
                                                wcmWdItmSer.setObjnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getObjnr());
                                                wcmWdItmSer.setItmtyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getItmtyp());
                                                wcmWdItmSer.setSeq(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getSeq());
                                                wcmWdItmSer.setPred(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getPred());
                                                wcmWdItmSer.setSucc(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getSucc());
                                                wcmWdItmSer.setCcobj(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getCcobj());
                                                wcmWdItmSer.setCctyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getCctyp());
                                                wcmWdItmSer.setStxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getStxt());
                                                wcmWdItmSer.setTggrp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTggrp());
                                                wcmWdItmSer.setTgstep(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgstep());
                                                wcmWdItmSer.setTgproc(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgproc());
                                                wcmWdItmSer.setTgtyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgtyp());
                                                wcmWdItmSer.setTgseq(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgseq());
                                                wcmWdItmSer.setTgtxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgtxt());
                                                wcmWdItmSer.setUnstep(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUnstep());
                                                wcmWdItmSer.setUnproc(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUnproc());
                                                wcmWdItmSer.setUntyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUntyp());
                                                wcmWdItmSer.setUnseq(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUnseq());
                                                wcmWdItmSer.setUntxt(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUntxt());
                                                wcmWdItmSer.setPhblflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getPhblflg());
                                                wcmWdItmSer.setPhbltyp(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getPhbltyp());
                                                wcmWdItmSer.setPhblnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getPhblnr());
                                                wcmWdItmSer.setTgflg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgflg());
                                                wcmWdItmSer.setTgform(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgform());
                                                wcmWdItmSer.setTgnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getTgnr());
                                                wcmWdItmSer.setUnform(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUnform());
                                                wcmWdItmSer.setUnnr(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getUnnr());
                                                wcmWdItmSer.setControl(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getControl());
                                                wcmWdItmSer.setLocation(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getLocation());
                                                wcmWdItmSer.setBtg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getBtg());
                                                wcmWdItmSer.setEtg(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getEtg());
                                                wcmWdItmSer.setBug(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getBug());
                                                wcmWdItmSer.setEug(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getEug());
                                                wcmWdItmSer.setRefobj(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getRefobj());
                                                wcmWdItmSer.setAction(ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getWdItemPrcbl_Al().get(l).getAction());
                                                ItWcmWdItemData_Al.add(wcmWdItmSer);
                                            }
                                        ItWcmWdData_Al.add(wcmWdSer);
                                    }
                                ItWcmWaData_Al.add(wcmWaSer);
                            }
                        ItWcmWwData_Al.add(wcmWwSer);
                    }

                String id = "";
                if (ohp.getOrdrId() != null && !ohp.getOrdrId().equals("")) {
                    id = ohp.getOrdrId();
                } else {
                    id = "";
                }
                try {
                    if (id != null && !id.equals(""))
                        cursor1 = App_db.rawQuery("select * from Orders_Attachments where Object_id = ?", new String[]{id});
                    else
                        cursor1 = App_db.rawQuery("select * from Orders_Attachments where Object_id = ?", new String[]{uniqeId});
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {

                                OrdrDocSer ods = new OrdrDocSer();
                                ods.setZobjid(id);
                                ods.setZdoctype("Q");
                                ods.setZdoctypeItem("QH");
                                ods.setFilename(cursor1.getString(1));
                                ods.setFiletype(cursor1.getString(5));
                                ods.setFsize(String.valueOf(""));
                                ods.setContent(cursor1.getString(4));
                                ods.setDocId("");
                                ods.setDocType("");
                                ods.setObjtype("BUS2038");
                                ItDocs_Al.add(ods);


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

                /*if (ohp.getOrdrDocsPrcbls() != null)
                    for (int i = 0; i < ohp.getOrdrDocsPrcbls().size(); i++) {
                        OrdrDocSer docSer = new OrdrDocSer();
                        docSer.setObjtype(ohp.getOrdrDocsPrcbls().get(i).getObjtype());
                        docSer.setZobjid(ohp.getOrdrDocsPrcbls().get(i).getZobjid());
                        docSer.setZdoctype(ohp.getOrdrDocsPrcbls().get(i).getZdoctype());
                        docSer.setZdoctypeItem(ohp.getOrdrDocsPrcbls().get(i).getZdoctypeItem());
                        docSer.setFilename(ohp.getOrdrDocsPrcbls().get(i).getFilename());
                        docSer.setFiletype(ohp.getOrdrDocsPrcbls().get(i).getFiletype());
                        docSer.setFsize(ohp.getOrdrDocsPrcbls().get(i).getFsize());
                        docSer.setContent(ohp.getOrdrDocsPrcbls().get(i).getContent());
                        docSer.setDocId(ohp.getOrdrDocsPrcbls().get(i).getDocId());
                        docSer.setDocType(ohp.getOrdrDocsPrcbls().get(i).getDocType());
                        ItDocs_Al.add(docSer);
                    }*/

            /*OrdrGeoSer geoSer = new OrdrGeoSer();
            geoSer.setLatitude("");
            geoSer.setLongitude("");
            geoSer.setAltitude("");*/
            }
            /*Adding Empty_Al to Arraylist*/


            ArrayList Empty_Al = new ArrayList<>();
            ArrayList<EtWcmWdDataSer> etWcmWdDataSers = new ArrayList<>();
            EtWcmWdDataSer etser = new EtWcmWdDataSer();
            etser.setEtWcmWdDataTagtext(Empty_Al);
            etser.setEtWcmWdDataUntagtext(Empty_Al);
            etWcmWdDataSers.add(etser);

            ArrayList EtOrderHeader_ArrayList = new ArrayList<>();
            Model_Order_Header_Custominfo custominfo = new Model_Order_Header_Custominfo();
            custominfo.setEtOrderHeaderFields(Empty_Al);
            EtOrderHeader_ArrayList.add(custominfo);


            ArrayList EtOrderOperation_ArrayList = new ArrayList<>();
            Model_Order_Operation_Custominfo operationcustominfo = new Model_Order_Operation_Custominfo();
            operationcustominfo.setEtOrderOperationsFields(Empty_Al);
            EtOrderOperation_ArrayList.add(operationcustominfo);


            ArrayList EtOrderMaterial_ArrayList = new ArrayList<>();
            Model_Order_Material_Custominfo materialcustominfo = new Model_Order_Material_Custominfo();
            materialcustominfo.setEtOrderComponentsFields(Empty_Al);
            EtOrderMaterial_ArrayList.add(materialcustominfo);


            /*Calling Notification Create Model with Data*/
            OrdrCreateSer ordrSer = new OrdrCreateSer();
            ordrSer.setMuser(username.toUpperCase().toString());
            ordrSer.setDeviceid(device_id);
            ordrSer.setDevicesno(device_serial_number);
            ordrSer.setUdid(device_uuid);
//            ordrSer.setIvTransmitType(transmitType);
            ordrSer.setIvTransmitType("FUNC");
            ordrSer.setIvCommit(true);
            ordrSer.setOperation(operation);
            ordrSer.setItOrderHeader(ItOrderHeader_Al);
            ordrSer.setItOrderOperations(ItOrderOperations_Al);
            ordrSer.setItOrderComponents(ItOrderComponents_Al);
            ordrSer.setItOrderLongtext(ItOrderLongtext_Al);
            ordrSer.setItOrderPermits(ItOrderPermits_Al);
            ordrSer.setItOrderOlist(ItOrderOlist_Al);
            ordrSer.setItOrderStatus(ItOrderStatus_Al);
            ordrSer.setItWcmWwData(ItWcmWwData_Al);
            ordrSer.setItWcmWaData(ItWcmWaData_Al);
            ordrSer.setItWcmWaChkReq(ItWcmWaChkReq_Al);
            ordrSer.setItWcmWdData(ItWcmWdData_Al);
            ordrSer.setItWcmWdItemData(ItWcmWdItemData_Al);
            ordrSer.setItWcmWcagns(ItWcmWcagns_Al);
            ordrSer.setItDocs(ItDocs_Al);
//            ordrSer.setIsGeo(IsGeo_Al);
            ordrSer.setEsAufnr(Empty_Al);
            ordrSer.setEtOrderHeader(EtOrderHeader_ArrayList);
            ordrSer.setEtOrderOperations(EtOrderOperation_ArrayList);
            ordrSer.setEtOrderLongtext(Empty_Al);
            ordrSer.setItOrderPermits(Empty_Al);
            ordrSer.setEtOrderOlist(Empty_Al);
            ordrSer.setEtOrderStatus(Empty_Al);
            ordrSer.setEtDocs(Empty_Al);
            ordrSer.setEtWcmWwData(Empty_Al);
            ordrSer.setEtWcmWaData(Empty_Al);
            ordrSer.setEtWcmWdData(etWcmWdDataSers);
            ordrSer.setEtWcmWdItemData(Empty_Al);
            ordrSer.setEtWcmWaChkReq(Empty_Al);
            ordrSer.setEtWcmWcagns(Empty_Al);
            ordrSer.setEtOrderComponents(EtOrderMaterial_ArrayList);
            ordrSer.setEtMessages(Empty_Al);
            ordrSer.setEtWcmWdDup(Empty_Al);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call = service.postCreateOrder(url_link, ordrSer, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_response_status_code", response_status_code + "...");
            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {
                    //if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {

                    success = false;
                    message = new StringBuffer();
                    try {
                        if (operation.equals("CRORD")) {
                            if (response.body().getD().getEsAufnr().getResults() != null) {
                                if (response.body().getD().getEsAufnr().getResults() != null && response.body().getD().getEsAufnr().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EsAufnr_Result esAufnr : response.body().getD().getEsAufnr().getResults()) {
                                        //message.append("\n");
                                        values.put("Aufnr", esAufnr.getAufnr());
                                        values.put("Message", esAufnr.getMessage());
                                        message.append(esAufnr.getMessage());

                                    }

                                    if (message.toString().startsWith("S")) {
                                        success = true;
                                    } else {
                                        success = false;
                                        Get_Response = message.toString();
                                    }
                                }
                            }
                        } else {
                            if (response.body().getD().getEtMessages().getResults() != null) {
                                if (response.body().getD().getEtMessages().getResults() != null && response.body().getD().getEtMessages().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtMessages_Result etMessages_result : response.body().getD().getEtMessages().getResults()) {
                                        values.put("Message", etMessages_result.getMessage());
                                        message.append(etMessages_result.getMessage());

                                    }
                                }
                            }
                            if (message.toString().startsWith("S")) {
                                success = true;
                                App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtOrderHeader_CustomInfo where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations_FIELDS where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from DUE_ORDERS_Longtext where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtOrderOlist where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtOrderStatus where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtWcmWwData where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtWcmWaData where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtWcmWdData where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtWcmWcagns where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtOrderComponents where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from DUE_ORDERS_EtOrderComponents_FIELDS where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtWcmWdDataTagtext where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from EtWcmWdDataUntagtext where Aufnr = ?", new String[]{orderId});
                                App_db.execSQL("delete from Orders_Attachments");

                                try {

                                    if (response.body().getD().getEtWcmWaData().getResults() != null) {
                                        if (response.body().getD().getEtWcmWaData().getResults() != null && response.body().getD().getEtWcmWaData().getResults().size() > 0) {
                                            ContentValues values = new ContentValues();
                                            for (Orders_SER.EtWcmWaData_Result etWcmWaData_result : response.body().getD().getEtWcmWaData().getResults()) {
                                                values.put("Wapinr", etWcmWaData_result.getWapinr());
                                                App_db.delete("DUE_ORDERS_EtDocs", "Zobjid =?", new String[]{"Wapinr"});
                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                }
                                try {
                                    if (response.body().getD().getEtWcmWaChkReq().getResults() != null) {
                                        if (response.body().getD().getEtWcmWaChkReq().getResults() != null && response.body().getD().getEtWcmWaChkReq().getResults().size() > 0) {
                                            ContentValues values = new ContentValues();
                                            for (Orders_SER.EtWcmWaChkReq_Result etWcmWaChkReq_result : response.body().getD().getEtWcmWaChkReq().getResults()) {
                                                values.put("Wapinr", etWcmWaChkReq_result.getWapinr());
                                                App_db.delete("EtWcmWaChkReq", "Wapinr = ?", new String[]{"wapinr"});
                                                App_db.delete("EtWcmWaData", "Wapinr", new String[]{"wapinr"});
                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                }
                                try {
                                    if (response.body().getD().getEtWcmWdData().getResults() != null) {
                                        if (response.body().getD().getEtWcmWdData().getResults() != null && response.body().getD().getEtWcmWdData().getResults().size() > 0) {
                                            ContentValues values = new ContentValues();
                                            for (Orders_SER.EtWcmWdData_Result etWcmWdData_result : response.body().getD().getEtWcmWdData().getResults()) {
                                                values.put("Wcnr", etWcmWdData_result.getWapinr());
                                                App_db.delete("EtWcmWdItemData", "Wcnr = ?", new String[]{"Wcnr"});

                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                }
                            } else {
                                success = false;
                                Get_Response = message.toString();
                            }
                        }
                    } catch (Exception e) {
                        success = false;
                    }

                    /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/

                    /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                    if (success) {
                        if (response.body().getD().getEtOrderHeader().getResults() != null) {
                            if (response.body().getD().getEtOrderHeader().getResults() != null && response.body().getD().getEtOrderHeader().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Orders_SER.EtOrderHeader_Result etOrderHeader_result : response.body().getD().getEtOrderHeader().getResults()) {
                                    // values.put("UUID", " ");
                                    values.put("Aufnr", etOrderHeader_result.getAufnr());
                                    App_db.insert("EtWcmWdItemData", null, values);

                                }
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/

                        App_db.beginTransaction();

                        /*Reading and Inserting Data into Database Table for EtOrderHeader*/
                        try {
                            try {
                                if (response.body().getD().getEtOrderHeader().getResults() != null)
                                    if (response.body().getD().getEtOrderHeader().getResults() != null
                                            && response.body().getD().getEtOrderHeader().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtOrderHeader_Result orderHeader_result : response.body().getD().getEtOrderHeader().getResults()) {
                                            values.put("UUID", orderHeader_result.getAufnr());
                                            values.put("Aufnr", orderHeader_result.getAufnr());
                                            created_aufnr = orderHeader_result.getAufnr();
                                            values.put("Auart", orderHeader_result.getAuart());
                                            values.put("Ktext", orderHeader_result.getKtext());
                                            values.put("Ilart", orderHeader_result.getIlart());
                                            values.put("Ernam", orderHeader_result.getErnam());
                                            values.put("Erdat", orderHeader_result.getErdat());
                                            values.put("Priok", orderHeader_result.getPriok());
                                            values.put("Equnr", orderHeader_result.getEqunr());
                                            values.put("Strno", orderHeader_result.getStrno());
                                            values.put("TplnrInt", orderHeader_result.getTplnrInt());
                                            values.put("Bautl", orderHeader_result.getBautl());
                                            values.put("Gltrp", orderHeader_result.getGltrp());
                                            values.put("Gstrp", orderHeader_result.getGstrp());
                                            values.put("Docs", orderHeader_result.getDocs());
                                            values.put("Permits", orderHeader_result.getPermits());
                                            values.put("Altitude", orderHeader_result.getAltitude());
                                            values.put("Latitude", orderHeader_result.getLatitude());
                                            values.put("Longitude", orderHeader_result.getLongitude());
                                            values.put("Qmnum", orderHeader_result.getQmnum());
                                            values.put("Closed", orderHeader_result.getClosed());
                                            values.put("Completed", orderHeader_result.getCompleted());
                                            values.put("Ingrp", orderHeader_result.getIngrp());
                                            values.put("Arbpl", orderHeader_result.getArbpl());
                                            values.put("Werks", orderHeader_result.getWerks());
                                            values.put("Bemot", orderHeader_result.getBemot());
                                            values.put("Aueru", orderHeader_result.getAueru());
                                            values.put("Auarttext", orderHeader_result.getAuarttext());
                                            values.put("Qmartx", orderHeader_result.getQmartx());
                                            values.put("Qmtxt", orderHeader_result.getQmtxt());
                                            values.put("Pltxt", orderHeader_result.getPltxt());
                                            values.put("Eqktx", orderHeader_result.getEqktx());
                                            values.put("Priokx", orderHeader_result.getPriokx());
                                            values.put("Ilatx", orderHeader_result.getIlatx());
                                            values.put("Plantname", orderHeader_result.getPlantname());
                                            values.put("Wkctrname", orderHeader_result.getWkctrname());
                                            values.put("Ingrpname", orderHeader_result.getIngrpname());
                                            values.put("Maktx", orderHeader_result.getMaktx());
                                            values.put("Xstatus", orderHeader_result.getXstatus());
                                            values.put("Usr01", orderHeader_result.getUsr01());
                                            values.put("Usr02", orderHeader_result.getUsr02());
                                            values.put("Usr03", orderHeader_result.getUsr03());
                                            values.put("Usr04", orderHeader_result.getUsr04());
                                            values.put("Usr05", orderHeader_result.getUsr05());
                                            values.put("Kokrs", orderHeader_result.getKokrs());
                                            values.put("Kostl", orderHeader_result.getKostl());
                                            values.put("Anlzu", orderHeader_result.getAnlzu());
                                            values.put("Anlzux", orderHeader_result.getAnlzux());
                                            values.put("Ausvn", orderHeader_result.getAusvn());
                                            values.put("Ausbs", orderHeader_result.getAusbs());
                                            values.put("Auswk", orderHeader_result.getAuswk());
                                            values.put("Qmnam", orderHeader_result.getQmnam());
                                            values.put("ParnrVw", orderHeader_result.getParnrVw());
                                            values.put("NameVw", orderHeader_result.getNameVw());
                                            values.put("Posid", orderHeader_result.getPosid());
                                            values.put("Revnr", orderHeader_result.getRevnr());
                                            App_db.insert("DUE_ORDERS_EtOrderHeader", null, values);

                                            if (orderHeader_result.getEtOrderHeaderFields() != null)
                                                if (orderHeader_result.getEtOrderHeaderFields().getResults() != null
                                                        && orderHeader_result.getEtOrderHeaderFields().getResults().size() > 0) {
                                                    ContentValues ValuesHCf = new ContentValues();
                                                    for (Orders_SER.EtOrderHeaderFields_Result etOrderHeaderFields_result : orderHeader_result.getEtOrderHeaderFields().getResults()) {
                                                        ValuesHCf.put("UUID", orderHeader_result.getAufnr());
                                                        ValuesHCf.put("Aufnr", orderHeader_result.getAufnr());
                                                        ValuesHCf.put("Zdoctype", etOrderHeaderFields_result.getZdoctype());
                                                        ValuesHCf.put("ZdoctypeItem", etOrderHeaderFields_result.getZdoctypeItem());
                                                        ValuesHCf.put("Tabname", etOrderHeaderFields_result.getTabname());
                                                        ValuesHCf.put("Fieldname", etOrderHeaderFields_result.getFieldname());
                                                        ValuesHCf.put("Value", etOrderHeaderFields_result.getValue());
                                                        ValuesHCf.put("Flabel", etOrderHeaderFields_result.getFlabel());
                                                        ValuesHCf.put("Sequence", etOrderHeaderFields_result.getSequence());
                                                        ValuesHCf.put("Length", etOrderHeaderFields_result.getLength());
                                                        ValuesHCf.put("Datatype", etOrderHeaderFields_result.getDatatype());
                                                        App_db.insert("EtOrderHeader_CustomInfo", null, ValuesHCf);
                                                    }
                                                }
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            try {
                                if (response.body().getD().getEtOrderOperations().getResults() != null)
                                    if (response.body().getD().getEtOrderOperations().getResults() != null
                                            && response.body().getD().getEtOrderOperations().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtOrderOperations_Result orderOperations : response.body().getD().getEtOrderOperations().getResults()) {
                                            values.put("UUID", orderOperations.getAufnr());
                                            values.put("Aufnr", orderOperations.getAufnr());
                                            values.put("Vornr", orderOperations.getVornr());
                                            values.put("Uvorn", orderOperations.getUvorn());
                                            values.put("Ltxa1", orderOperations.getLtxa1());
                                            values.put("Arbpl", orderOperations.getArbpl());
                                            values.put("Werks", orderOperations.getWerks());
                                            values.put("Steus", orderOperations.getSteus());
                                            values.put("Larnt", orderOperations.getLarnt());
                                            values.put("Dauno", orderOperations.getDauno());
                                            values.put("Daune", orderOperations.getDaune());
                                            values.put("Fsavd", orderOperations.getFsavd());
                                            values.put("Ssedd", orderOperations.getSsedd());
                                            values.put("Pernr", orderOperations.getPernr());
                                            values.put("Asnum", orderOperations.getAsnum());
                                            values.put("Plnty", orderOperations.getPlnty());
                                            values.put("Plnal", orderOperations.getPlnal());
                                            values.put("Plnnr", orderOperations.getPlnnr());
                                            values.put("Rueck", orderOperations.getRueck());
                                            values.put("Aueru", orderOperations.getAueru());
                                            values.put("ArbplText", orderOperations.getArbplText());
                                            values.put("WerksText", orderOperations.getWerksText());
                                            values.put("SteusText", orderOperations.getSteusText());
                                            values.put("LarntText", orderOperations.getLarntText());
                                            values.put("Usr01", orderOperations.getUsr01());
                                            values.put("Usr02", orderOperations.getUsr02());
                                            values.put("Usr03", orderOperations.getUsr03());
                                            values.put("Usr04", orderOperations.getUsr04());
                                            values.put("Usr05", orderOperations.getUsr05());
                                            values.put("Action", orderOperations.getAction());
                                            App_db.insert("DUE_ORDERS_EtOrderOperations", null, values);

                                            if (orderOperations.getEtOrderOperationsFields() != null)
                                                if (orderOperations.getEtOrderOperationsFields().getResults() != null
                                                        && orderOperations.getEtOrderOperationsFields().getResults().size() > 0) {
                                                    ContentValues ValuesOCf = new ContentValues();
                                                    for (Orders_SER.EtOrderHeaderFields_Result etOrderHeaderFields : orderOperations.getEtOrderOperationsFields().getResults()) {
                                                        ValuesOCf.put("UUID", orderOperations.getAufnr());
                                                        ValuesOCf.put("Aufnr", orderOperations.getAufnr());
                                                        ValuesOCf.put("Zdoctype", etOrderHeaderFields.getZdoctype());
                                                        ValuesOCf.put("ZdoctypeItem", etOrderHeaderFields.getZdoctypeItem());
                                                        ValuesOCf.put("Tabname", etOrderHeaderFields.getTabname());
                                                        ValuesOCf.put("Fieldname", etOrderHeaderFields.getFieldname());
                                                        ValuesOCf.put("Value", etOrderHeaderFields.getValue());
                                                        ValuesOCf.put("Flabel", etOrderHeaderFields.getFlabel());
                                                        ValuesOCf.put("Sequence", etOrderHeaderFields.getSequence());
                                                        ValuesOCf.put("Length", etOrderHeaderFields.getLength());
                                                        ValuesOCf.put("Datatype", etOrderHeaderFields.getDatatype());
                                                        ValuesOCf.put("OperationID", orderOperations.getVornr());
                                                        App_db.insert("DUE_ORDERS_EtOrderOperations_FIELDS", null, ValuesOCf);
                                                    }
                                                }
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtOrderOperations*/

                            /*Reading and Inserting Data into Database Table for EtOrderLongtext*/
                            try {

                                if (response.body().getD().getEtOrderLongtext().getResults() != null)
                                    if (response.body().getD().getEtOrderLongtext().getResults() != null &&
                                            response.body().getD().getEtOrderLongtext().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtOrderLongtext_Result orderLongtext_result : response.body().getD().getEtOrderLongtext().getResults()) {
                                            values.put("UUID", orderLongtext_result.getAufnr());
                                            values.put("Aufnr", orderLongtext_result.getAufnr());
                                            values.put("Activity", orderLongtext_result.getActivity());
                                            values.put("TextLine", orderLongtext_result.getTextLine());
                                            values.put("Tdid", orderLongtext_result.getTdid());
                                            App_db.insert("DUE_ORDERS_Longtext", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtOrderLongtext*/

                            /*Reading and Inserting Data into Database Table for EtOrderOlist*/
                            try {
                                if (response.body().getD().getEtOrderOlist().getResults() != null)
                                    if (response.body().getD().getEtOrderOlist().getResults() != null
                                            && response.body().getD().getEtOrderOlist().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtOrderOlist_Result orderOlist_result : response.body().getD().getEtOrderOlist().getResults()) {
                                            values.put("UUID", orderOlist_result.getAufnr());
                                            values.put("Aufnr", orderOlist_result.getAufnr());
                                            values.put("Obknr", orderOlist_result.getObknr());
                                            values.put("Obzae", orderOlist_result.getObzae());
                                            values.put("Qmnum", orderOlist_result.getQmnum());
                                            values.put("Equnr", orderOlist_result.getEqunr());
                                            values.put("Strno", orderOlist_result.getStrno());
                                            values.put("Tplnr", orderOlist_result.getTplnr());
                                            values.put("Bautl", orderOlist_result.getBautl());
                                            values.put("Qmtxt", orderOlist_result.getQmtxt());
                                            values.put("Pltxt", orderOlist_result.getPltxt());
                                            values.put("Eqktx", orderOlist_result.getEqktx());
                                            values.put("Maktx", orderOlist_result.getMaktx());
                                            values.put("Action", orderOlist_result.getAction());
                                            App_db.insert("EtOrderOlist", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtOrderOlist*/

                            /*Reading and Inserting Data into Database Table for EtOrderStatus*/
                            try {
                                if (response.body().getD().getEtOrderStatus().getResults() != null)
                                    if (response.body().getD().getEtOrderStatus().getResults() != null &&
                                            response.body().getD().getEtOrderStatus().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtOrderStatus_Result orderStatus_result : response.body().getD().getEtOrderStatus().getResults()) {
                                            values.put("UUID", orderStatus_result.getAufnr());
                                            values.put("Aufnr", orderStatus_result.getAufnr());
                                            values.put("Vornr", orderStatus_result.getVornr());
                                            values.put("Objnr", orderStatus_result.getObjnr());
                                            values.put("Stsma", orderStatus_result.getStsma());
                                            values.put("Inist", orderStatus_result.getInist());
                                            values.put("Stonr", orderStatus_result.getStonr());
                                            values.put("Hsonr", orderStatus_result.getHsonr());
                                            values.put("Nsonr", orderStatus_result.getNsonr());
                                            values.put("Stat", orderStatus_result.getStat());
                                            values.put("Act", orderStatus_result.getAct());
                                            values.put("Txt04", orderStatus_result.getTxt04());
                                            values.put("Txt30", orderStatus_result.getTxt30());
                                            values.put("Action", orderStatus_result.getAction());
                                            App_db.insert("EtOrderStatus", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtOrderStatus*/

                            /*Reading and Inserting Data into Database Table for EtDocs*/
                            try {

                                if (response.body().getD().getEtDocs().getResults() != null)
                                    if (response.body().getD().getEtDocs().getResults() != null &&
                                            response.body().getD().getEtDocs().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtDocs_Result etDocs_result : response.body().getD().getEtDocs().getResults()) {
                                            values.put("UUID", etDocs_result.getZobjid());
                                            values.put("Zobjid", etDocs_result.getZobjid());
                                            values.put("Zdoctype", etDocs_result.getZdoctype());
                                            values.put("ZdoctypeItem", etDocs_result.getZdoctypeItem());
                                            values.put("Filename", etDocs_result.getFilename());
                                            values.put("Filetype", etDocs_result.getFiletype());
                                            values.put("Fsize", etDocs_result.getFsize());
                                            values.put("Content", etDocs_result.getContent());
                                            values.put("DocID", etDocs_result.getDocID());
                                            values.put("DocType", etDocs_result.getDocType());
                                            values.put("Objtype", etDocs_result.getObjtype());
                                            values.put("Contentx", etDocs_result.getContentX());
                                            App_db.insert("DUE_ORDERS_EtDocs", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtDocs*/

                            /*Reading and Inserting Data into Database Table for EtWcmWwData*/
                            try {
                                if (response.body().getD().getEtWcmWwData().getResults() != null)
                                    if (response.body().getD().getEtWcmWwData().getResults() != null &&
                                            response.body().getD().getEtWcmWwData().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWwData_Result wcmWwData_result : response.body().getD().getEtWcmWwData().getResults()) {
                                            values.put("UUID", wcmWwData_result.getAufnr());
                                            values.put("Aufnr", wcmWwData_result.getAufnr());
                                            values.put("Objart", wcmWwData_result.getObjart());
                                            values.put("Wapnr", wcmWwData_result.getWapnr());
                                            values.put("Iwerk", wcmWwData_result.getIwerk());
                                            values.put("Usage", wcmWwData_result.getUsage());
                                            values.put("Usagex", wcmWwData_result.getUsagex());
                                            values.put("Train", wcmWwData_result.getTrain());
                                            values.put("Trainx", wcmWwData_result.getTrainx());
                                            values.put("Anlzu", wcmWwData_result.getAnlzu());
                                            values.put("Anlzux", wcmWwData_result.getAnlzux());
                                            values.put("Etape", wcmWwData_result.getEtape());
                                            values.put("Etapex", wcmWwData_result.getEtapex());
                                            values.put("Rctime", wcmWwData_result.getRctime());
                                            values.put("Rcunit", wcmWwData_result.getRcunit());
                                            values.put("Priok", wcmWwData_result.getPriok());
                                            values.put("Priokx", wcmWwData_result.getPriokx());
                                            values.put("Stxt", wcmWwData_result.getStxt());
                                            values.put("Datefr", wcmWwData_result.getDatefr());
                                            values.put("Timefr", wcmWwData_result.getTimefr());
                                            values.put("Dateto", wcmWwData_result.getDateto());
                                            values.put("Timeto", wcmWwData_result.getTimeto());
                                            values.put("Objnr", wcmWwData_result.getObjnr());
                                            values.put("Crea", wcmWwData_result.getCrea());
                                            values.put("Prep", wcmWwData_result.getPrep());
                                            values.put("Comp", wcmWwData_result.getComp());
                                            values.put("Appr", wcmWwData_result.getAppr());
                                            values.put("Pappr", wcmWwData_result.getPappr());
                                            values.put("Action", wcmWwData_result.getAction());
                                            values.put("Begru", wcmWwData_result.getBegru());
                                            values.put("Begtx", wcmWwData_result.getBegtx());
                                            App_db.insert("EtWcmWwData", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWwData*/

                            /*Reading and Inserting Data into Database Table for EtWcmWaData*/
                            try {
                                if (response.body().getD().getEtWcmWaData().getResults() != null)
                                    if (response.body().getD().getEtWcmWaData().getResults() != null &&
                                            response.body().getD().getEtWcmWaData().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWaData_Result wcmWaData_result : response.body().getD().getEtWcmWaData().getResults()) {
                                            values.put("UUID", wcmWaData_result.getAufnr());
                                            values.put("Aufnr", wcmWaData_result.getAufnr());
                                            values.put("Objart", wcmWaData_result.getObjart());
                                            values.put("Wapinr", wcmWaData_result.getWapinr());
                                            values.put("Iwerk", wcmWaData_result.getIwerk());
                                            values.put("Objtyp", wcmWaData_result.getObjtyp());
                                            values.put("Usage", wcmWaData_result.getUsage());
                                            values.put("Usagex", wcmWaData_result.getUsagex());
                                            values.put("Train", wcmWaData_result.getTrain());
                                            values.put("Trainx", wcmWaData_result.getTrainx());
                                            values.put("Anlzu", wcmWaData_result.getAnlzu());
                                            values.put("Anlzux", wcmWaData_result.getAnlzux());
                                            values.put("Etape", wcmWaData_result.getEtape());
                                            values.put("Etapex", wcmWaData_result.getEtapex());
                                            values.put("Stxt", wcmWaData_result.getStxt());
                                            values.put("Datefr", wcmWaData_result.getDatefr());
                                            values.put("Timefr", wcmWaData_result.getTimefr());
                                            values.put("Dateto", wcmWaData_result.getDateto());
                                            values.put("Timeto", wcmWaData_result.getTimeto());
                                            values.put("Priok", wcmWaData_result.getPriok());
                                            values.put("Priokx", wcmWaData_result.getPriokx());
                                            values.put("Rctime", wcmWaData_result.getRctime());
                                            values.put("Rcunit", wcmWaData_result.getRcunit());
                                            values.put("Objnr", wcmWaData_result.getObjnr());
                                            values.put("Refobj", wcmWaData_result.getRefobj());
                                            values.put("Crea", wcmWaData_result.getCrea());
                                            values.put("Prep", wcmWaData_result.getPrep());
                                            values.put("Comp", wcmWaData_result.getComp());
                                            values.put("Appr", wcmWaData_result.getAppr());
                                            values.put("Action", wcmWaData_result.getAction());
                                            values.put("Begru", wcmWaData_result.getBegru());
                                            values.put("Begtx", wcmWaData_result.getBegtx());
                                            values.put("Extperiod", wcmWaData_result.getExtperiod());
                                            App_db.insert("EtWcmWaData", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWaData*/

                            /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/
                            try {
                                if (response.body().getD().getEtWcmWaChkReq().getResults() != null)
                                    if (response.body().getD().getEtWcmWaChkReq().getResults() != null &&
                                            response.body().getD().getEtWcmWaChkReq().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWaChkReq_Result wcmWaChkReq_result : response.body().getD().getEtWcmWaChkReq().getResults()) {
                                            values.put("Wapinr", wcmWaChkReq_result.getWapinr());
                                            values.put("Wapityp", wcmWaChkReq_result.getWapityp());
                                            values.put("Needid", wcmWaChkReq_result.getNeedid());
                                            values.put("Value", wcmWaChkReq_result.getValue());
                                            values.put("ChkPointType", wcmWaChkReq_result.getChkPointType());
                                            values.put("Desctext", wcmWaChkReq_result.getDesctext());
                                            values.put("Tplnr", wcmWaChkReq_result.getTplnr());
                                            values.put("Wkgrp", wcmWaChkReq_result.getWkgrp());
                                            values.put("Needgrp", wcmWaChkReq_result.getNeedgrp());
                                            values.put("Equnr", wcmWaChkReq_result.getEqunr());
                                            values.put("Wkid", wcmWaChkReq_result.getWkid());
                                            App_db.insert("EtWcmWaChkReq", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/

                            /*Reading and Inserting Data into Database Table for EtWcmWdData*/
                            try {
                                if (response.body().getD().getEtWcmWdData().getResults() != null)
                                    if (response.body().getD().getEtWcmWdData().getResults() != null &&
                                            response.body().getD().getEtWcmWdData().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWdData_Result wcmWdData_result : response.body().getD().getEtWcmWdData().getResults()) {
                                            values.put("Aufnr", wcmWdData_result.getAufnr());
                                            values.put("Objart", wcmWdData_result.getObjart());
                                            values.put("Wcnr", wcmWdData_result.getWcnr());
                                            values.put("Iwerk", wcmWdData_result.getIwerk());
                                            values.put("Objtyp", wcmWdData_result.getObjtyp());
                                            values.put("Usage", wcmWdData_result.getUsage());
                                            values.put("Usagex", wcmWdData_result.getUsagex());
                                            values.put("Train", wcmWdData_result.getTrain());
                                            values.put("Trainx", wcmWdData_result.getTrainx());
                                            values.put("Anlzu", wcmWdData_result.getAnlzu());
                                            values.put("Anlzux", wcmWdData_result.getAnlzux());
                                            values.put("Etape", wcmWdData_result.getEtape());
                                            values.put("Etapex", wcmWdData_result.getEtapex());
                                            values.put("Stxt", wcmWdData_result.getStxt());
                                            values.put("Datefr", wcmWdData_result.getDatefr());
                                            values.put("Timefr", wcmWdData_result.getTimefr());
                                            values.put("Dateto", wcmWdData_result.getDateto());
                                            values.put("Timeto", wcmWdData_result.getTimeto());
                                            values.put("Priok", wcmWdData_result.getPriok());
                                            values.put("Priokx", wcmWdData_result.getPriokx());
                                            values.put("Rctime", wcmWdData_result.getRctime());
                                            values.put("Rcunit", wcmWdData_result.getRcunit());
                                            values.put("Objnr", wcmWdData_result.getObjnr());
                                            values.put("Refobj", wcmWdData_result.getRefobj());
                                            values.put("Crea", wcmWdData_result.getCrea());
                                            values.put("Prep", wcmWdData_result.getPrep());
                                            values.put("Comp", wcmWdData_result.getComp());
                                            values.put("Appr", wcmWdData_result.getAppr());
                                            values.put("Action", wcmWdData_result.getAction());
                                            values.put("Begru", wcmWdData_result.getBegru());
                                            values.put("Begtx", wcmWdData_result.getBegtx());
                                            App_db.insert("EtWcmWdData", null, values);

                                            /*EtWcmWdDataTagtext*/
                                            if (wcmWdData_result.getEtWcmWdDataTagtext() != null)
                                                if (wcmWdData_result.getEtWcmWdDataTagtext().getResults() != null
                                                        && wcmWdData_result.getEtWcmWdDataTagtext().getResults().size() > 0) {
                                                    ContentValues valuesTag = new ContentValues();
                                                    for (Orders_SER.EtWcmWdDataTagtext_Result wcmWdDataTagtext_result :
                                                            wcmWdData_result.getEtWcmWdDataTagtext().getResults()) {
                                                        valuesTag.put("Aufnr", wcmWdDataTagtext_result.getAufnr());
                                                        valuesTag.put("Wcnr", wcmWdDataTagtext_result.getWcnr());
                                                        valuesTag.put("Objtype", wcmWdDataTagtext_result.getObjtype());
                                                        valuesTag.put("FormatCol", wcmWdDataTagtext_result.getFormatCol());
                                                        valuesTag.put("TextLine", wcmWdDataTagtext_result.getTextLine());
                                                        valuesTag.put("Action", wcmWdDataTagtext_result.getAction());
                                                        App_db.insert("EtWcmWdDataTagtext", null, valuesTag);
                                                    }
                                                }

                                            /*EtWcmWdDataUntagtext*/
                                            if (wcmWdData_result.getEtWcmWdDataUntagtext() != null)
                                                if (wcmWdData_result.getEtWcmWdDataUntagtext().getResults() != null
                                                        && wcmWdData_result.getEtWcmWdDataUntagtext().getResults().size() > 0) {
                                                    ContentValues valuesUnTag = new ContentValues();
                                                    for (Orders_SER.EtWcmWdDataTagtext_Result wcmWdDataUntagtext :
                                                            wcmWdData_result.getEtWcmWdDataUntagtext().getResults()) {
                                                        valuesUnTag.put("Aufnr", wcmWdDataUntagtext.getAufnr());
                                                        valuesUnTag.put("Wcnr", wcmWdDataUntagtext.getWcnr());
                                                        valuesUnTag.put("Objtype", wcmWdDataUntagtext.getObjtype());
                                                        valuesUnTag.put("FormatCol", wcmWdDataUntagtext.getFormatCol());
                                                        valuesUnTag.put("TextLine", wcmWdDataUntagtext.getTextLine());
                                                        valuesUnTag.put("Action", wcmWdDataUntagtext.getAction());
                                                        App_db.insert("EtWcmWdDataUntagtext", null, valuesUnTag);
                                                    }
                                                }
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWdData*/

                            /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/
                            try {
                                if (response.body().getD().getEtWcmWdItemData().getResults() != null)
                                    if (response.body().getD().getEtWcmWdItemData().getResults() != null &&
                                            response.body().getD().getEtWcmWdItemData().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWdItemData_Result wcmWdItemData_result : response.body().getD().getEtWcmWdItemData().getResults()) {
                                            values.put("Wcnr", wcmWdItemData_result.getWcnr());
                                            values.put("Wcitm", wcmWdItemData_result.getWcitm());
                                            values.put("Objnr", wcmWdItemData_result.getObjnr());
                                            values.put("Itmtyp", wcmWdItemData_result.getItmtyp());
                                            values.put("Seq", wcmWdItemData_result.getSeq());
                                            values.put("Pred", wcmWdItemData_result.getPred());
                                            values.put("Succ", wcmWdItemData_result.getSucc());
                                            values.put("Ccobj", wcmWdItemData_result.getCcobj());
                                            values.put("Cctyp", wcmWdItemData_result.getCctyp());
                                            values.put("Stxt", wcmWdItemData_result.getStxt());
                                            values.put("Tggrp", wcmWdItemData_result.getTggrp());
                                            values.put("Tgstep", wcmWdItemData_result.getTgstep());
                                            values.put("Tgproc", wcmWdItemData_result.getTgproc());
                                            values.put("Tgtyp", wcmWdItemData_result.getTgtyp());
                                            values.put("Tgseq", wcmWdItemData_result.getTgseq());
                                            values.put("Tgtxt", wcmWdItemData_result.getTgtxt());
                                            values.put("Unstep", wcmWdItemData_result.getUnstep());
                                            values.put("Unproc", wcmWdItemData_result.getUnproc());
                                            values.put("Untyp", wcmWdItemData_result.getUntyp());
                                            values.put("Unseq", wcmWdItemData_result.getUnseq());
                                            values.put("Untxt", wcmWdItemData_result.getUntxt());
                                            values.put("Phblflg", wcmWdItemData_result.getPhblflg());
                                            values.put("Phbltyp", wcmWdItemData_result.getPhbltyp());
                                            values.put("Phblnr", wcmWdItemData_result.getPhblnr());
                                            values.put("Tgflg", wcmWdItemData_result.getTgflg());
                                            values.put("Tgform", wcmWdItemData_result.getTgform());
                                            values.put("Tgnr", wcmWdItemData_result.getTgnr());
                                            values.put("Unform", wcmWdItemData_result.getUnform());
                                            values.put("Unnr", wcmWdItemData_result.getUnnr());
                                            values.put("Control", wcmWdItemData_result.getControl());
                                            values.put("Location", wcmWdItemData_result.getLocation());
                                            values.put("Refobj", wcmWdItemData_result.getRefobj());
                                            values.put("Action", wcmWdItemData_result.getAction());
                                            values.put("Btg", wcmWdItemData_result.getBtg());
                                            values.put("Etg", wcmWdItemData_result.getEtg());
                                            values.put("Bug", wcmWdItemData_result.getBug());
                                            values.put("Eug", wcmWdItemData_result.getEug());
                                            App_db.insert("EtWcmWdItemData", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/

                            /*Reading and Inserting Data into Database Table for EtWcmWcagns*/
                            try {
                                if (response.body().getD().getEtWcmWcagns().getResults() != null)
                                    if (response.body().getD().getEtWcmWcagns().getResults() != null
                                            && response.body().getD().getEtWcmWcagns().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWcagns_Result wcmWcagns_result : response.body().getD().getEtWcmWcagns().getResults()) {
                                            values.put("UUID", wcmWcagns_result.getAufnr());
                                            values.put("Aufnr", wcmWcagns_result.getAufnr());
                                            values.put("Objnr", wcmWcagns_result.getObjnr());
                                            values.put("Counter", wcmWcagns_result.getCounter());
                                            values.put("Objart", wcmWcagns_result.getObjart());
                                            values.put("Objtyp", wcmWcagns_result.getObjtyp());
                                            values.put("Pmsog", wcmWcagns_result.getPmsog());
                                            values.put("Gntxt", wcmWcagns_result.getGntxt());
                                            values.put("Geniakt", wcmWcagns_result.getGeniakt());
                                            values.put("Genvname", wcmWcagns_result.getGenvname());
                                            values.put("Action", wcmWcagns_result.getAction());
                                            values.put("Werks", wcmWcagns_result.getWerks());
                                            values.put("Crname", wcmWcagns_result.getCrname());
                                            values.put("Hilvl", wcmWcagns_result.getHilvl());
                                            values.put("Procflg", wcmWcagns_result.getProcflg());
                                            values.put("Direction", wcmWcagns_result.getDirection());
                                            values.put("Copyflg", wcmWcagns_result.getCopyflg());
                                            values.put("Mandflg", wcmWcagns_result.getMandflg());
                                            values.put("Deacflg", wcmWcagns_result.getDeacflg());
                                            values.put("Status", wcmWcagns_result.getStatus());
                                            values.put("Asgnflg", wcmWcagns_result.getAsgnflg());
                                            values.put("Autoflg", wcmWcagns_result.getAutoflg());
                                            values.put("Agent", wcmWcagns_result.getAgent());
                                            values.put("Valflg", wcmWcagns_result.getValflg());
                                            values.put("Wcmuse", wcmWcagns_result.getWcmuse());
                                            values.put("Gendatum", wcmWcagns_result.getGendatum());
                                            values.put("Gentime", wcmWcagns_result.getGentime());
                                            App_db.insert("EtWcmWcagns", null, values);
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWcagns*/

                            /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                            try {
                                if (response.body().getD().getEtOrderComponents().getResults() != null)
                                    if (response.body().getD().getEtOrderComponents().getResults() != null &&
                                            response.body().getD().getEtOrderComponents().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtOrderComponents_Result etOrderComponents_result : response.body().getD().getEtOrderComponents().getResults()) {
                                            values.put("UUID", etOrderComponents_result.getAufnr());
                                            values.put("Aufnr", etOrderComponents_result.getAufnr());
                                            values.put("Vornr", etOrderComponents_result.getVornr());
                                            values.put("Uvorn", etOrderComponents_result.getUvorn());
                                            values.put("Rsnum", etOrderComponents_result.getRsnum());
                                            values.put("Rspos", etOrderComponents_result.getRspos());
                                            values.put("Matnr", etOrderComponents_result.getMatnr());
                                            values.put("Werks", etOrderComponents_result.getWerks());
                                            values.put("Lgort", etOrderComponents_result.getLgort());
                                            values.put("Posnr", etOrderComponents_result.getPosnr());
                                            values.put("Bdmng", etOrderComponents_result.getBdmng());
                                            values.put("Meins", etOrderComponents_result.getMeins());
                                            values.put("Postp", etOrderComponents_result.getPostp());
                                            values.put("MatnrText", etOrderComponents_result.getMatnrText());
                                            values.put("WerksText", etOrderComponents_result.getWerksText());
                                            values.put("LgortText", etOrderComponents_result.getLgortText());
                                            values.put("PostpText", etOrderComponents_result.getPostpText());
                                            values.put("Usr01", etOrderComponents_result.getUsr01());
                                            values.put("Usr02", etOrderComponents_result.getUsr02());
                                            values.put("Usr03", etOrderComponents_result.getUsr03());
                                            values.put("Usr04", etOrderComponents_result.getUsr04());
                                            values.put("Usr05", etOrderComponents_result.getUsr05());
                                            values.put("Action", etOrderComponents_result.getAction());
                                            values.put("Wempf", etOrderComponents_result.getWempf());
                                            values.put("Ablad", etOrderComponents_result.getAblad());
                                            App_db.insert("EtOrderComponents", null, values);

                                            if (etOrderComponents_result.getEtOrderComponentsFields() != null)
                                                if (etOrderComponents_result.getEtOrderComponentsFields().getResults() != null
                                                        && etOrderComponents_result.getEtOrderComponentsFields().getResults().size() > 0) {
                                                    ContentValues ValuesOCf = new ContentValues();
                                                    for (Orders_SER.EtOrderHeaderFields_Result etOrderHeaderFields : etOrderComponents_result.getEtOrderComponentsFields().getResults()) {
                                                        ValuesOCf.put("UUID", etOrderComponents_result.getAufnr());
                                                        ValuesOCf.put("Aufnr", etOrderComponents_result.getAufnr());
                                                        ValuesOCf.put("Zdoctype", etOrderHeaderFields.getZdoctype());
                                                        ValuesOCf.put("ZdoctypeItem", etOrderHeaderFields.getZdoctypeItem());
                                                        ValuesOCf.put("Tabname", etOrderHeaderFields.getTabname());
                                                        ValuesOCf.put("Fieldname", etOrderHeaderFields.getFieldname());
                                                        ValuesOCf.put("Value", etOrderHeaderFields.getValue());
                                                        ValuesOCf.put("Flabel", etOrderHeaderFields.getFlabel());
                                                        ValuesOCf.put("Sequence", etOrderHeaderFields.getSequence());
                                                        ValuesOCf.put("Length", etOrderHeaderFields.getLength());
                                                        ValuesOCf.put("Datatype", etOrderHeaderFields.getDatatype());
                                                        ValuesOCf.put("OperationID", etOrderComponents_result.getVornr());
                                                        ValuesOCf.put("PartID", etOrderComponents_result.getPosnr());
                                                        App_db.insert("DUE_ORDERS_EtOrderComponents_FIELDS ", null, values);
                                                    }
                                                }
                                        }
                                    }
                            } catch (Exception e) {

                            }
                            App_db.setTransactionSuccessful();
                            Get_Response = message.toString();
                            Get_Data = created_aufnr;
                        } finally {
                            App_db.endTransaction();
                        }
                    } else {
                        try {
                            if (response.body().getD().getEtWcmWdDup().getResults() != null) {
                                String EtNotifDup_response_data = new Gson().toJson(response.body().getD().getEtWcmWdDup().getResults());
                                if (EtNotifDup_response_data != null && !EtNotifDup_response_data.equals("")) {
                                    JSONArray jsonArray = new JSONArray(EtNotifDup_response_data);

                                    Get_Response = "Duplicate";
                                    Get_Data = jsonArray.toString();
                                } else {
                                    Get_Response = activity
                                            .getString(R.string.crtordr_unable);
                                    Get_Data = "";
                                }
                            }
                        }catch(Exception e)
                        {
                            Log.v("response",""+e.getMessage());
                        }
                    }
                }
            } else {
                if (operation.equals("CHORD"))
                    Get_Response = activity.getString(R.string.unable_change);
                else
                    Get_Response = activity.getString(R.string.crtordr_unable);
            }

        } catch (Exception e) {
            Log.v("REsponse", "" + e.getMessage());
            if (operation.equals("CHORD"))
                Get_Response = activity.getString(R.string.unable_change);
            else
                Get_Response = activity.getString(R.string.crtordr_unable);
        }
        /*Reading Data by using FOR Loop*/
        String[] response = new String[2];
        response[0] = Get_Response;
        response[1] = Get_Data;
        return response;
    }

    private static String dateFormat(String date) {
        if (date != null && !date.equals("")) {
            if (date.equals("00000000"))
                return date;
            String inputPattern1 = "MMM dd, yyyy";
            String outputPattern1 = "yyyyMMdd";
            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
            try {
                Date date1 = inputFormat1.parse(date);
                String Datefr = outputFormat1.format(date1);
                return Datefr;
            } catch (ParseException e) {
                return date;
            }
        } else {
            return "";
        }
    }

    private static String timeFormat(String time) {
        String tm = "";
        if (time != null && !time.equals("")) {
            if (time.equals("000000"))
                return time;
            String[] tim = time.split(":");
            for (int i = 0; i < tim.length; i++) {
                tm = tm + tim[i];
            }
            return tm;
        } else {
            return "";
        }
    }
}
