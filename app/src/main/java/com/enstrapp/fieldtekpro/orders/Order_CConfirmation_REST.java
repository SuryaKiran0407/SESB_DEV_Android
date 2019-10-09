package com.enstrapp.fieldtekpro.orders;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Insert_Orders_Data;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Orders_SER;
import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

public class Order_CConfirmation_REST
{

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String password = "", url_link = "", username = "",
            device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static boolean success = false;

    public static String Get_Data(Context activity, ArrayList<ConfirmOrder_Prcbl> cop_al,
                                  ArrayList<Measurement_Parceble> mpo_al,
                                  String transmitType, String operation, String orderId,
                                  String type, String longtext_text) {
        try
        {
            Get_Response = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",null);
            if (type.equals("pc"))
            {
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where " +"Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"W", "Z", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
            }
            else
            {
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where " +"Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"W", "CC", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
            }
            device_id = Settings.Secure.getString(activity.getContentResolver(),Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(),Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            String URL = activity.getString(R.string.ip_address);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);

            Map<String, String> map = new HashMap<>();
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");

            ArrayList<ItConfirmOrderColl_Ser_REST> ItConfirmOrderColl_Al = new ArrayList<>();
            ArrayList<ItConfirmOrder_Ser_REST> ItConfirmOrder_Al = new ArrayList<>();
            ArrayList<ItImrg_Ser_REST> ItImrg_Al = new ArrayList<>();
            ArrayList<OrdrCreateSer_Longtext_REST> ItOrderLongtext_Al = new ArrayList<>();
            ArrayList<OrdrDocSer> ItDocs_Al = new ArrayList<>();

            if (type.equals("TECO"))
            {
                for (ConfirmOrder_Prcbl cop : cop_al)
                {
                    ItConfirmOrderColl_Ser_REST confirmOrderSer = new ItConfirmOrderColl_Ser_REST();
                    confirmOrderSer.setAufnr(cop.getAufnr());
                    confirmOrderSer.setStatus(cop.getStatus());
                    ItConfirmOrderColl_Al.add(confirmOrderSer);
                }
            }
            else if (type.equals("ORCC"))
            {
                for (ConfirmOrder_Prcbl cop : cop_al)
                {
                    ItConfirmOrderColl_Ser_REST confirmOrderSer = new ItConfirmOrderColl_Ser_REST();
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


                if (mpo_al != null)
                    if (mpo_al.size() > 0)
                    {
                        for (Measurement_Parceble mpo : mpo_al)
                        {
                            ItImrg_Ser_REST itImrgSer = new ItImrg_Ser_REST();
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
            }
            else
            {
                for (ConfirmOrder_Prcbl cop : cop_al)
                {
                    ItConfirmOrder_Ser_REST confirmOrderSer = new ItConfirmOrder_Ser_REST();
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
                    confirmOrderSer.setWorkCntr(cop.getWorkCntr());
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
                        if (order_confirm_longtext.contains("\n"))
                        {
                            String[] longtext_array = order_confirm_longtext.split("\n");
                            for (int i = 0; i < longtext_array.length; i++)
                            {
                                OrdrCreateSer_Longtext_REST mnc = new OrdrCreateSer_Longtext_REST();
                                mnc.setAufnr(cop.getAufnr());
                                mnc.setActivity(cop.getVornr());
                                mnc.setTextLine(longtext_array[i]);
                                mnc.setTdid("RMEL");
                                ItOrderLongtext_Al.add(mnc);
                            }
                        }
                        else
                        {
                            OrdrCreateSer_Longtext_REST mnc = new OrdrCreateSer_Longtext_REST();
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

            ArrayList Empty_Al = new ArrayList<>();


            Model_Notif_Create_REST.IsDevice isDevice = new Model_Notif_Create_REST.IsDevice();
            isDevice.setMUSER(username.toUpperCase().toString());
            isDevice.setDEVICEID(device_id);
            isDevice.setDEVICESNO(device_serial_number);
            isDevice.setUDID(device_uuid);


            OrdrCreateSer_REST ordrSer = new OrdrCreateSer_REST();
            OrderPartialConfirm_Ser_REST ordrpSer = new OrderPartialConfirm_Ser_REST();
            if (type.equals("pc"))
            {
                ordrpSer.setIsDevice(isDevice);
                ordrpSer.setIvTransmitType(transmitType);
                ordrpSer.setIvCommit(true);
                ordrpSer.setOperation(operation);
                ordrpSer.setIt_confirm_order(ItConfirmOrder_Al);
                ordrpSer.setIt_oder_longtext(ItOrderLongtext_Al);
                ordrpSer.setIt_imrg(ItImrg_Al);
                //ordrSer.setItDocs(ItDocs_Al);
                ordrpSer.setEvMessage(Empty_Al);
            }
            else
            {
                ordrSer.setIsDevice(isDevice);
                ordrSer.setIvTransmitType(transmitType);
                ordrSer.setIvCommit(true);
                ordrSer.setOperation(operation);
                ordrSer.setIt_confirm_order(ItConfirmOrderColl_Al);
                ordrSer.setIt_oder_longtext(ItOrderLongtext_Al);
                ordrSer.setIt_imrg(ItImrg_Al);
                //ordrSer.setItDocs(ItDocs_Al);
                ordrSer.setEvMessage(Empty_Al);
            }
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_Orders_SER> call = null;
            if (type.equals("pc"))
            {
                call = service.postConfirmOrder(url_link, ordrpSer, basic, map);
            }
            else
            {
                call = service.postCreateOrder(url_link, ordrSer, basic, map);
            }
            Response<REST_Orders_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    StringBuffer message = new StringBuffer();
                    REST_Orders_SER rs = response.body();
                    List<REST_Orders_SER.EtMessages> EtMessages = rs.getEtMessages();
                    for (int j = 0; j < EtMessages.size(); j++)
                    {
                        if(j == 0)
                        {
                            message.append(EtMessages.get(j).getMessage());
                            message.append("\n");
                        }
                        else
                        {
                            message.append(EtMessages.get(j).getMessage().substring(1));
                            message.append("\n");
                        }
                    }
                    Get_Response = message.toString();
                    if(message.toString().startsWith("S"))
                    {
                        success = true;
                    }
                    else
                    {
                        success = false;
                    }

                    if (success)
                    {
                        String orders_insert_status = REST_Insert_Orders_Data.Insert_Order_Data(activity, rs, orderId, "CRORD");
                        if (orders_insert_status.equalsIgnoreCase("Success"))
                        {
                            Get_Response = message.toString();
                        }
                        else
                        {
                            Get_Response = "Exception";
                        }
                    }
                    else
                    {
                        Get_Response = message.toString();
                    }
                }
                else
                {
                    Get_Response = activity.getString(R.string.cnfrmordr_unable);
                }
            }
            else
            {
                Get_Response = activity.getString(R.string.cnfrmordr_unable);
            }
        }
        catch (Exception e)
        {
            Get_Response = activity.getString(R.string.cnfrmordr_unable);
        }
        return Get_Response;
    }
}
