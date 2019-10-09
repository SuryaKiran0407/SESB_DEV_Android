package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Insert_Orders_Data;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Orders_SER;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Attachments_REST;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import java.text.ParseException;
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

import static android.content.Context.MODE_PRIVATE;

public class Orders_Change_REST
{

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String password = "", url_link = "", username = "",device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "",Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();
    private static Check_Empty checkempty = new Check_Empty();
    private static StringBuffer message = new StringBuffer();
    private static boolean success = false;
    private static String created_aufnr = "";

    public static String[] Post_Create_Order(Context activity, OrdrHeaderPrcbl ohp,String transmitType, String operation, String orderId, String type,String uniqeId, List<Notif_EtDocs_Parcelable> attachments_arraylist)
    {
        try
        {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",null);
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = " +"? and Zactivity = ? and Endpoint = ?", new String[]{"W", "U", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
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

            ArrayList<OrdrCreateSer_Header_REST> ItOrderHeader_Al = new ArrayList<>();
            ArrayList<OrdrCreateSer_Operation_REST> ItOrderOperations_Al = new ArrayList<>();
            ArrayList<OrdrCreateSer_Component_REST> ItOrderComponents_Al = new ArrayList<>();
            ArrayList<OrdrCreateSer_Longtext_REST> ItOrderLongtext_Al = new ArrayList<>();
            ArrayList<OrdrCreateSer_Status_REST> ItOrderStatus_Al = new ArrayList<>();


            OrdrCreateSer_Header_REST ordrHdrSer = new OrdrCreateSer_Header_REST();
            ordrHdrSer.setAUFNR(ohp.getOrdrId());
            ordrHdrSer.setAUART(ohp.getOrdrTypId());
            ordrHdrSer.setKTEXT(ohp.getOrdrShrtTxt());
            ordrHdrSer.setILART(ohp.getActivitytype_id());
            ordrHdrSer.setERNAM(username.toUpperCase().toString());
            ordrHdrSer.setERDAT(dateFormat(ohp.getBasicStart()));
            ordrHdrSer.setPRIOK(ohp.getPriorityId());
            ordrHdrSer.setEQUNR(ohp.getEquipNum());
            ordrHdrSer.setSTRNO(ohp.getFuncLocId());
            ordrHdrSer.setTPLNRINT(ohp.getFuncLocId());
            ordrHdrSer.setGLTRP(dateFormat(ohp.getBasicEnd()));
            ordrHdrSer.setGSTRP(dateFormat(ohp.getBasicStart()));
            ordrHdrSer.setANLZU(ohp.getSysCondId());
            ordrHdrSer.setPARNRVW(ohp.getPerRespId());
            ordrHdrSer.setNAMEVW(ohp.getPerRespName());
            ordrHdrSer.setQMNUM(ohp.getNotifId());
            ordrHdrSer.setINGRP(ohp.getPlnrGrpId());
            ordrHdrSer.setARBPL(ohp.getWrkCntrId());
            ordrHdrSer.setWERKS(ohp.getPlant());
            ordrHdrSer.setAUARTTEXT(ohp.getOrdrTypName());
            ordrHdrSer.setPLTXT(ohp.getFuncLocName());
            ordrHdrSer.setEQKTX(ohp.getEquipName());
            ordrHdrSer.setPRIOKX(ohp.getPriorityTxt());
            ordrHdrSer.setILATX(ohp.getActivitytype_text());
            ordrHdrSer.setPLANTNAME(ohp.getPlantName());
            ordrHdrSer.setWKCTRNAME(ohp.getWrkCntrName());
            ordrHdrSer.setINGRPNAME(ohp.getPlnrGrpName());
            ordrHdrSer.setKOSTL(ohp.getRespCostCntrId());
            ordrHdrSer.setUSR03(ohp.getPosid());
            ordrHdrSer.setREVNR(ohp.getRevnr());
            ItOrderHeader_Al.add(ordrHdrSer);


            /*Adding Order Longtext to Arraylist*/
            String order_header_longtext = ohp.getOrdrLngTxt();
            if (order_header_longtext != null && !order_header_longtext.equals(""))
            {
                if (order_header_longtext.contains("\n"))
                {
                    String[] longtext_array = order_header_longtext.split("\n");
                    for (int i = 0; i < longtext_array.length; i++)
                    {
                        OrdrCreateSer_Longtext_REST mnc = new OrdrCreateSer_Longtext_REST();
                        mnc.setAufnr(ohp.getOrdrId());
                        mnc.setActivity("");
                        mnc.setTextLine(longtext_array[i]);
                        mnc.setTdid("");
                        ItOrderLongtext_Al.add(mnc);
                    }
                }
                else
                {
                    OrdrCreateSer_Longtext_REST mnc = new OrdrCreateSer_Longtext_REST();
                    mnc.setAufnr(ohp.getOrdrId());
                    mnc.setActivity("");
                    mnc.setTextLine(order_header_longtext);
                    mnc.setTdid("");
                    ItOrderLongtext_Al.add(mnc);
                }
            }
            /*Adding Order Longtext to Arraylist*/



            if (ohp.getOrdrOprtnPrcbls() != null)
            {
                for (int i = 0; i < ohp.getOrdrOprtnPrcbls().size(); i++)
                {
                    OrdrCreateSer_Operation_REST oprtnSer = new OrdrCreateSer_Operation_REST();
                    oprtnSer.setAUFNR(ohp.getOrdrOprtnPrcbls().get(i).getOrdrId());
                    oprtnSer.setVORNR(ohp.getOrdrOprtnPrcbls().get(i).getOprtnId());
                    oprtnSer.setUVORN("");
                    oprtnSer.setLTXA1(ohp.getOrdrOprtnPrcbls().get(i).getOprtnShrtTxt());
                    oprtnSer.setARBPL(ohp.getOrdrOprtnPrcbls().get(i).getWrkCntrId());
                    oprtnSer.setWERKS(ohp.getOrdrOprtnPrcbls().get(i).getPlantId());
                    oprtnSer.setSTEUS(ohp.getOrdrOprtnPrcbls().get(i).getCntrlKeyId());
                    oprtnSer.setLARNT(ohp.getOrdrOprtnPrcbls().get(i).getLarnt());
                    oprtnSer.setDAUNO(ohp.getOrdrOprtnPrcbls().get(i).getDuration());
                    oprtnSer.setDAUNE(ohp.getOrdrOprtnPrcbls().get(i).getDurationUnit());
                    oprtnSer.setFSAVD(ohp.getOrdrOprtnPrcbls().get(i).getFsavd());
                    oprtnSer.setSSEDD(ohp.getOrdrOprtnPrcbls().get(i).getSsedd());
                    oprtnSer.setRUECK(ohp.getOrdrOprtnPrcbls().get(i).getRueck());
                    oprtnSer.setAUERU(ohp.getOrdrOprtnPrcbls().get(i).getAueru());
                    oprtnSer.setARBPLTEXT(ohp.getOrdrOprtnPrcbls().get(i).getWrkCntrTxt());
                    oprtnSer.setWERKSTEXT(ohp.getOrdrOprtnPrcbls().get(i).getPlantTxt());
                    oprtnSer.setSTEUSTEXT(ohp.getOrdrOprtnPrcbls().get(i).getCntrlKeyTxt());
                    oprtnSer.setSakto(ohp.getOrdrOprtnPrcbls().get(i).getCostelement());
                    oprtnSer.setAction(ohp.getOrdrOprtnPrcbls().get(i).getStatus());
                    oprtnSer.setUSR02(ohp.getOrdrOprtnPrcbls().get(i).getUsr02());
                    oprtnSer.setUSR03(ohp.getOrdrOprtnPrcbls().get(i).getUsr03());
                    ItOrderOperations_Al.add(oprtnSer);

                    /*Adding Operation Longtext to Arraylist*/
                    String operation_longtext = ohp.getOrdrOprtnPrcbls().get(i).getOprtnLngTxt();
                    if (operation_longtext != null && !operation_longtext.equals(""))
                    {
                        if (operation_longtext.contains("\n"))
                        {
                            String[] longtext_array = operation_longtext.split("\n");
                            for (int j = 0; j < longtext_array.length; j++)
                            {
                                OrdrCreateSer_Longtext_REST mnc = new OrdrCreateSer_Longtext_REST();
                                mnc.setAufnr(ohp.getOrdrId());
                                mnc.setActivity(ohp.getOrdrOprtnPrcbls().get(i).getOprtnId());
                                mnc.setTextLine(longtext_array[j]);
                                mnc.setTdid("");
                                ItOrderLongtext_Al.add(mnc);
                            }
                        }
                        else
                        {
                            OrdrCreateSer_Longtext_REST mnc = new OrdrCreateSer_Longtext_REST();
                            mnc.setAufnr(ohp.getOrdrId());
                            mnc.setActivity(ohp.getOrdrOprtnPrcbls().get(i).getOprtnId());
                            mnc.setTextLine(operation_longtext);
                            mnc.setTdid("");
                            ItOrderLongtext_Al.add(mnc);
                        }
                    }
                    /*Adding Operation Longtext to Arraylist*/

                }
            }


            if (ohp.getOrdrMatrlPrcbls() != null)
            {
                for (int i = 0; i < ohp.getOrdrMatrlPrcbls().size(); i++)
                {
                    OrdrCreateSer_Component_REST matrlSer = new OrdrCreateSer_Component_REST();
                    matrlSer.setAufnr(ohp.getOrdrMatrlPrcbls().get(i).getAufnr());
                    matrlSer.setVornr(ohp.getOrdrMatrlPrcbls().get(i).getOprtnId());
                    matrlSer.setRsnum(ohp.getOrdrMatrlPrcbls().get(i).getRsnum());
                    matrlSer.setRspos(ohp.getOrdrMatrlPrcbls().get(i).getRspos());
                    matrlSer.setMatnr(ohp.getOrdrMatrlPrcbls().get(i).getMatrlId());
                    matrlSer.setWerks(ohp.getOrdrMatrlPrcbls().get(i).getPlantId());
                    matrlSer.setLgort(ohp.getOrdrMatrlPrcbls().get(i).getLocation());
                    matrlSer.setPosnr(ohp.getOrdrMatrlPrcbls().get(i).getPartId());
                    matrlSer.setBdmng(ohp.getOrdrMatrlPrcbls().get(i).getQuantity());
                    matrlSer.setMeins(ohp.getOrdrMatrlPrcbls().get(i).getMeins());
                    matrlSer.setPostp(ohp.getOrdrMatrlPrcbls().get(i).getStockcat_id());
                    matrlSer.setWempf(ohp.getOrdrMatrlPrcbls().get(i).getReceipt());
                    matrlSer.setAblad(ohp.getOrdrMatrlPrcbls().get(i).getUnloading());
                    matrlSer.setMatnrText(ohp.getOrdrMatrlPrcbls().get(i).getMatrlTxt());
                    matrlSer.setWerksText(ohp.getOrdrMatrlPrcbls().get(i).getPlantTxt());
                    matrlSer.setLgortText(ohp.getOrdrMatrlPrcbls().get(i).getLocationTxt());
                    matrlSer.setPostpText(ohp.getOrdrMatrlPrcbls().get(i).getStockcat_text());
                    matrlSer.setCharg(ohp.getOrdrMatrlPrcbls().get(i).getBatch());
                    matrlSer.setAction(ohp.getOrdrMatrlPrcbls().get(i).getStatus());
                    ItOrderComponents_Al.add(matrlSer);
                }
            }



            if (ohp.getOrdrStatusPrcbls() != null)
            {
                for (int i = 0; i < ohp.getOrdrStatusPrcbls().size(); i++)
                {
                    OrdrCreateSer_Status_REST statusSer = new OrdrCreateSer_Status_REST();
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
            }


            ArrayList<Model_Notif_Attachments_REST> attachmentsRestArrayList = new ArrayList<>();
            if(attachments_arraylist.size() > 0)
            {
                for(int i = 0; i < attachments_arraylist.size(); i++)
                {
                    String status = attachments_arraylist.get(i).getStatus();
                    if(status.equalsIgnoreCase("New"))
                    {
                        Model_Notif_Attachments_REST mnc = new Model_Notif_Attachments_REST();
                        mnc.setObjtype(attachments_arraylist.get(i).getObjtype());
                        mnc.setZobjid(attachments_arraylist.get(i).getZobjid());
                        mnc.setZdoctype(attachments_arraylist.get(i).getZdoctype());
                        mnc.setZdoctypeItem(attachments_arraylist.get(i).getZdoctypeitem());
                        mnc.setFilename(attachments_arraylist.get(i).getFilename());
                        mnc.setFiletype(attachments_arraylist.get(i).getFiletype());
                        mnc.setFsize(attachments_arraylist.get(i).getFsize());
                        mnc.setContent(attachments_arraylist.get(i).getContent());
                        mnc.setDocId(attachments_arraylist.get(i).getDocid());
                        mnc.setDocType(attachments_arraylist.get(i).getDoctype());
                        attachmentsRestArrayList.add(mnc);
                    }
                }
            }


            ArrayList Empty_Al = new ArrayList<>();


            Model_Notif_Create_REST.IsDevice isDevice = new Model_Notif_Create_REST.IsDevice();
            isDevice.setMUSER(username.toUpperCase().toString());
            isDevice.setDEVICEID(device_id);
            isDevice.setDEVICESNO(device_serial_number);
            isDevice.setUDID(device_uuid);


            OrdrCreateSer_REST ordrSer = new OrdrCreateSer_REST();
            ordrSer.setIsDevice(isDevice);
            ordrSer.setIvTransmitType("FUNC");
            ordrSer.setIvCommit(true);
            ordrSer.setOperation("CHORD");
            ordrSer.setIt_order_header(ItOrderHeader_Al);
            ordrSer.setIt_oder_longtext(ItOrderLongtext_Al);
            ordrSer.setIt_order_operations(ItOrderOperations_Al);
            ordrSer.setIt_order_components(ItOrderComponents_Al);
            ordrSer.setIt_order_status(ItOrderStatus_Al);
            ordrSer.setEtDocs(Empty_Al);
            ordrSer.setItDocs(attachmentsRestArrayList);
            ordrSer.setEvMessage(Empty_Al);



            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_Orders_SER> call = service.postCreateOrder(url_link, ordrSer, basic, map);
            Response<REST_Orders_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_response_status_code", response_status_code + "...");
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    REST_Orders_SER rs = response.body();
                    //String response_data = new Gson().toJson(rs);

                    message = new StringBuffer();

                    String aufnr = rs.getEvorderno();
                    List<REST_Orders_SER.EtMessages> EtMessages = rs.getEtMessages();
                    for (int j = 0; j < EtMessages.size(); j++)
                    {
                        message.append(EtMessages.get(j).getMessage());
                    }
                    Get_Response = message.toString();
                    Get_Data = aufnr;
                    created_aufnr = aufnr;

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
                        String orders_insert_status = REST_Insert_Orders_Data.Insert_Order_Data(activity, rs, created_aufnr, "CRORD");
                        if (orders_insert_status.equalsIgnoreCase("Success"))
                        {
                            /*String EtOrderHeader_response_data = new Gson().toJson(rs.getETNOTIFHEADER());
                            JSONArray jsonArray1 = new JSONArray(EtOrderHeader_response_data);
                            if (jsonArray1.length() > 0)
                            {
                                for (int i = 0; i < jsonArray1.length(); i++)
                                {
                                    Qmnum = jsonArray1.getJSONObject(i).optString("QMNUM");
                                }
                            }*/
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
                    Get_Response = activity.getString(R.string.unable_change);
                }
            }
            else
            {
                Get_Response = activity.getString(R.string.unable_change);
            }
        }
        catch (Exception e)
        {
            Get_Response = activity.getString(R.string.unable_change);
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


}
