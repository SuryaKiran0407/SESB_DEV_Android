package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class Isolation_Create_Change {

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
    private static String created_aufnr = "";
    private static String wcnr = "";
    private static String wcnr_aufnr = "";

    public static String[] Change_Permit(Activity activity, ArrayList<OrdrPermitPrcbl> wd_al, String transmitType, String operation, String orderId, String type) {
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
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"D8", "U", webservice_type});
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


            ArrayList<IvMchkSer> IvMchk_Al = new ArrayList<>();
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


            Cursor cursor1 = null;
            /*OrdrHdrSer ordrHdrSer = new OrdrHdrSer();
            ordrHdrSer.setAufnr(ohp.getOrdrId());
            ordrHdrSer.setAuart(ohp.getOrdrTypId());
            ordrHdrSer.setKtext(ohp.getOrdrShrtTxt());
            ordrHdrSer.setIlart("");
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
            ordrHdrSer.setIlatx("");
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
            ItOrderHeader_Al.add(ordrHdrSer);

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
                    ItOrderComponents_Al.add(matrlSer);
                }

            if (ohp.getOrdrLngTxt() != null)
                for (String lngTxt : ohp.getOrdrLngTxt().split("\n")) {
                    OrdrLngTxtSer lngTxtSer = new OrdrLngTxtSer();
                    lngTxtSer.setAufnr("");
                    lngTxtSer.setActivity("");
                    lngTxtSer.setTextLine(lngTxt);
                    ItOrderLongtext_Al.add(lngTxtSer);
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
*/
            /*if (ohp.getOrdrStatusPrcbls() != null)
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
                            ItOrderStatus_Al.add(statusSer);
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
                                    ItOrderStatus_Al.add(statusSer);
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
*/
            for (int i = 0; i < wd_al.size(); i++) {
                if (wd_al.get(i).getTagText_al() != null)
                    for (int k = 0; k < wd_al.get(i).getTagText_al().size(); k++) {
//                        if (wd_al.get(i).getTagText_al() != null) {
//                            for (int l = 0; l < ohp.getOrdrPermitPrcbls().get(i).getWaWdPrcbl_Al().get(j).getWaWdPrcbl_Al().get(k).getTagText_al().size(); l++) {
                        ItWcmWdDataTagtext tagSer = new ItWcmWdDataTagtext();
                        tagSer.setAufnr(wd_al.get(i).getTagText_al().get(k).getAufnr());
                        tagSer.setWcnr(wd_al.get(i).getTagText_al().get(k).getWcnr());
                        tagSer.setObjtype(wd_al.get(i).getTagText_al().get(k).getObjtype());
                        tagSer.setFormatCol(wd_al.get(i).getTagText_al().get(k).getFormatCol());
                        tagSer.setTextLine(wd_al.get(i).getTagText_al().get(k).getTextLine());
                        tagSer.setAction(wd_al.get(i).getTagText_al().get(k).getAction());
                        ItWcmWdAddTag_Al.add(tagSer);
                    }


                if (wd_al.get(i).getUnTagText_al() != null)
                    for (int k = 0; k < wd_al.get(i).getUnTagText_al().size(); k++) {
                        ItWcmWdDataUntagtext unTagSer = new ItWcmWdDataUntagtext();
                        unTagSer.setAufnr(wd_al.get(i).getUnTagText_al().get(k).getAufnr());
                        unTagSer.setWcnr(wd_al.get(i).getUnTagText_al().get(k).getWcnr());
                        unTagSer.setObjtype(wd_al.get(i).getUnTagText_al().get(k).getObjtype());
                        unTagSer.setFormatCol(wd_al.get(i).getUnTagText_al().get(k).getFormatCol());
                        unTagSer.setTextLine(wd_al.get(i).getUnTagText_al().get(k).getTextLine());
                        unTagSer.setAction(wd_al.get(i).getUnTagText_al().get(k).getAction());
                        ItWcmWdAddUnTag_Al.add(unTagSer);
                    }

                OrdrWcmWdSer wcmWdSer = new OrdrWcmWdSer();
                wcmWdSer.setAufnr(wd_al.get(i).getAufnr());
                if (wd_al.get(i).getAufnr() != null)
                    wcnr_aufnr = wd_al.get(i).getAufnr();
                wcmWdSer.setObjart(wd_al.get(i).getObjart());
                wcmWdSer.setWcnr(wd_al.get(i).getWcnr());
                wcnr = wd_al.get(i).getWcnr();
                wcmWdSer.setIwerk(wd_al.get(i).getIwerk());
                wcmWdSer.setObjtyp(wd_al.get(i).getObjtyp());
                wcmWdSer.setUsage(wd_al.get(i).getUsage());
                wcmWdSer.setUsagex(wd_al.get(i).getUsagex());
                wcmWdSer.setTrain(wd_al.get(i).getTrain());
                wcmWdSer.setTrainx(wd_al.get(i).getTrainx());
                wcmWdSer.setAnlzu(wd_al.get(i).getAnlzu());
                wcmWdSer.setAnlzux(wd_al.get(i).getAnlzux());
                wcmWdSer.setEtape(wd_al.get(i).getEtape());
                wcmWdSer.setEtapex(wd_al.get(i).getEtapex());
                wcmWdSer.setStxt(wd_al.get(i).getStxt());
                wcmWdSer.setDatefr(dateFormat(wd_al.get(i).getDatefr()));
                wcmWdSer.setTimefr(timeFormat(wd_al.get(i).getTimefr()));
                wcmWdSer.setDateto(dateFormat(wd_al.get(i).getDateto()));
                wcmWdSer.setTimeto(timeFormat(wd_al.get(i).getTimeto()));
                wcmWdSer.setBegru(wd_al.get(i).getBegru());
                wcmWdSer.setBegtx(wd_al.get(i).getBegtx());
                wcmWdSer.setPriok(wd_al.get(i).getPriok());
                wcmWdSer.setPriokx(wd_al.get(i).getPriokx());
                wcmWdSer.setRctime(wd_al.get(i).getRctime());
                wcmWdSer.setRcunit(wd_al.get(i).getRcunit());
                wcmWdSer.setObjnr(wd_al.get(i).getObjnr());
                wcmWdSer.setRefobj(wd_al.get(i).getRefobj());
                wcmWdSer.setCrea(wd_al.get(i).getCrea());
                wcmWdSer.setPrep(wd_al.get(i).getPrep());
                wcmWdSer.setComp(wd_al.get(i).getComp());
                wcmWdSer.setAppr(wd_al.get(i).getAppr());
                wcmWdSer.setPappr(wd_al.get(i).getPappr());
                wcmWdSer.setAction(wd_al.get(i).getAction());
                wcmWdSer.setItWcmWdDataTagtext(ItWcmWdAddTag_Al);
                wcmWdSer.setItWcmWdDataUntagtext(ItWcmWdAddUnTag_Al);

                if (wd_al.get(i).getWcagnsPrcbl_Al() != null)
                    for (int l = 0; l < wd_al.get(i).getWcagnsPrcbl_Al().size(); l++) {
                        OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                        wcmWagnsSer.setAufnr(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getAufnr());
                        wcmWagnsSer.setObjnr(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getObjnr());
                        wcmWagnsSer.setCounter(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getCounter());
                        wcmWagnsSer.setWerks(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getWerks());
                        wcmWagnsSer.setCrname(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getCrname());
                        wcmWagnsSer.setObjart(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getObjart());
                        wcmWagnsSer.setObjtyp(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getObjtyp());
                        wcmWagnsSer.setPmsog(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getPmsog());
                        wcmWagnsSer.setGntxt(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getGntxt());
                        wcmWagnsSer.setGeniakt(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getGeniakt());
                        wcmWagnsSer.setGenvname(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getGenvname());
                        wcmWagnsSer.setGendatum(dateFormat(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getGendatum()));
                        wcmWagnsSer.setGentime(timeFormat(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getGentime()));
                        wcmWagnsSer.setHilvl(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getHilvl());
                        wcmWagnsSer.setProcflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getProcflg());
                        wcmWagnsSer.setDirection(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getDirection());
                        wcmWagnsSer.setCopyflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getCopyflg());
                        wcmWagnsSer.setMandflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getMandflg());
                        wcmWagnsSer.setDeacflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getDeacflg());
                        wcmWagnsSer.setStatus(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getStatus());
                        wcmWagnsSer.setAsgnflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getAsgnflg());
                        wcmWagnsSer.setAutoflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getAutoflg());
                        wcmWagnsSer.setAgent(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getAgent());
                        wcmWagnsSer.setValflg(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getValflg());
                        wcmWagnsSer.setWcmuse(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getWcmuse());
                        wcmWagnsSer.setAction(wd_al.get(i).getWcagnsPrcbl_Al().get(l).getAction());
                        ItWcmWcagns_Al.add(wcmWagnsSer);
                    }

                if (wd_al.get(i).getStatusPrcbl_Al() != null)
                    for (int l = 0; l < wd_al.get(i).getStatusPrcbl_Al().size(); l++) {
                        OrdrStatusSer statusSer = new OrdrStatusSer();
                        statusSer.setAufnr(wd_al.get(i).getStatusPrcbl_Al().get(l).getAufnr());
                        statusSer.setVornr(wd_al.get(i).getStatusPrcbl_Al().get(l).getVornr());
                        statusSer.setObjnr(wd_al.get(i).getStatusPrcbl_Al().get(l).getObjnr());
                        statusSer.setStsma(wd_al.get(i).getStatusPrcbl_Al().get(l).getStsma());
                        statusSer.setInist(wd_al.get(i).getStatusPrcbl_Al().get(l).getInist());
                        statusSer.setStonr(wd_al.get(i).getStatusPrcbl_Al().get(l).getStonr());
                        statusSer.setHsonr(wd_al.get(i).getStatusPrcbl_Al().get(l).getHsonr());
                        statusSer.setNsonr(wd_al.get(i).getStatusPrcbl_Al().get(l).getNsonr());
                        statusSer.setStat(wd_al.get(i).getStatusPrcbl_Al().get(l).getStat());
                        statusSer.setAct(wd_al.get(i).getStatusPrcbl_Al().get(l).getAct());
                        statusSer.setTxt04(wd_al.get(i).getStatusPrcbl_Al().get(l).getTxt04());
                        statusSer.setTxt30(wd_al.get(i).getStatusPrcbl_Al().get(l).getTxt30());
                        statusSer.setAction(wd_al.get(i).getStatusPrcbl_Al().get(l).getAction());
                        ItOrderStatus_Al.add(statusSer);
                    }

                if (wd_al.get(i).getWdItemPrcbl_Al() != null)
                    for (int l = 0; l < wd_al.get(i).getWdItemPrcbl_Al().size(); l++) {
                        OrdrWcmWdItmSer wcmWdItmSer = new OrdrWcmWdItmSer();
                        wcmWdItmSer.setWcnr(wd_al.get(i).getWdItemPrcbl_Al().get(l).getWcnr());
                        wcmWdItmSer.setWcitm(wd_al.get(i).getWdItemPrcbl_Al().get(l).getWctim());
                        wcmWdItmSer.setObjnr(wd_al.get(i).getWdItemPrcbl_Al().get(l).getObjnr());
                        wcmWdItmSer.setItmtyp(wd_al.get(i).getWdItemPrcbl_Al().get(l).getItmtyp());
                        wcmWdItmSer.setSeq(wd_al.get(i).getWdItemPrcbl_Al().get(l).getSeq());
                        wcmWdItmSer.setPred(wd_al.get(i).getWdItemPrcbl_Al().get(l).getPred());
                        wcmWdItmSer.setSucc(wd_al.get(i).getWdItemPrcbl_Al().get(l).getSucc());
                        wcmWdItmSer.setCcobj(wd_al.get(i).getWdItemPrcbl_Al().get(l).getCcobj());
                        wcmWdItmSer.setCctyp(wd_al.get(i).getWdItemPrcbl_Al().get(l).getCctyp());
                        wcmWdItmSer.setStxt(wd_al.get(i).getWdItemPrcbl_Al().get(l).getStxt());
                        wcmWdItmSer.setTggrp(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTggrp());
                        wcmWdItmSer.setTgstep(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgstep());
                        wcmWdItmSer.setTgproc(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgproc());
                        wcmWdItmSer.setTgtyp(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgtyp());
                        wcmWdItmSer.setTgseq(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgseq());
                        wcmWdItmSer.setTgtxt(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgtxt());
                        wcmWdItmSer.setUnstep(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUnstep());
                        wcmWdItmSer.setUnproc(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUnproc());
                        wcmWdItmSer.setUntyp(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUntyp());
                        wcmWdItmSer.setUnseq(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUnseq());
                        wcmWdItmSer.setUntxt(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUntxt());
                        wcmWdItmSer.setPhblflg(wd_al.get(i).getWdItemPrcbl_Al().get(l).getPhblflg());
                        wcmWdItmSer.setPhbltyp(wd_al.get(i).getWdItemPrcbl_Al().get(l).getPhbltyp());
                        wcmWdItmSer.setPhblnr(wd_al.get(i).getWdItemPrcbl_Al().get(l).getPhblnr());
                        wcmWdItmSer.setTgflg(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgflg());
                        wcmWdItmSer.setTgform(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgform());
                        wcmWdItmSer.setTgnr(wd_al.get(i).getWdItemPrcbl_Al().get(l).getTgnr());
                        wcmWdItmSer.setUnform(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUnform());
                        wcmWdItmSer.setUnnr(wd_al.get(i).getWdItemPrcbl_Al().get(l).getUnnr());
                        wcmWdItmSer.setControl(wd_al.get(i).getWdItemPrcbl_Al().get(l).getControl());
                        wcmWdItmSer.setLocation(wd_al.get(i).getWdItemPrcbl_Al().get(l).getLocation());
                        wcmWdItmSer.setBtg(wd_al.get(i).getWdItemPrcbl_Al().get(l).getBtg());
                        wcmWdItmSer.setEtg(wd_al.get(i).getWdItemPrcbl_Al().get(l).getEtg());
                        wcmWdItmSer.setBug(wd_al.get(i).getWdItemPrcbl_Al().get(l).getBug());
                        wcmWdItmSer.setEug(wd_al.get(i).getWdItemPrcbl_Al().get(l).getEug());
                        wcmWdItmSer.setRefobj(wd_al.get(i).getWdItemPrcbl_Al().get(l).getRefobj());
                        wcmWdItmSer.setAction(wd_al.get(i).getWdItemPrcbl_Al().get(l).getAction());
                        ItWcmWdItemData_Al.add(wcmWdItmSer);
                    }
                ItWcmWdData_Al.add(wcmWdSer);
            }

            if (!wcnr.startsWith("WD")) {
                if (wcnr_aufnr != null && !wcnr_aufnr.equals("")) {
                    ItWcmWcagns_Al = new ArrayList<>();
                    try {
                        cursor1 = App_db.rawQuery("select * from EtWcmWcagns where Aufnr = ? and Objart = ? and Objnr like ? order by Hilvl", new String[]{wcnr_aufnr, "WW", "WW%"});
                        if (cursor1 != null && cursor.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                                    wcmWagnsSer.setAufnr(cursor1.getString(2));
                                    wcmWagnsSer.setObjnr(cursor1.getString(3));
                                    wcmWagnsSer.setCounter(cursor1.getString(4));
                                    wcmWagnsSer.setWerks(cursor1.getString(12));
                                    wcmWagnsSer.setCrname(cursor1.getString(13));
                                    wcmWagnsSer.setObjart(cursor1.getString(5));
                                    wcmWagnsSer.setObjtyp(cursor1.getString(6));
                                    wcmWagnsSer.setPmsog(cursor1.getString(7));
                                    wcmWagnsSer.setGntxt(cursor1.getString(8));
                                    wcmWagnsSer.setGeniakt(cursor1.getString(9));
                                    wcmWagnsSer.setGenvname(cursor1.getString(10));
                                    wcmWagnsSer.setGendatum(cursor1.getString(26));
                                    wcmWagnsSer.setGentime(cursor1.getString(27));
                                    wcmWagnsSer.setHilvl(Integer.parseInt(cursor1.getString(14)));
                                    wcmWagnsSer.setProcflg(cursor1.getString(15));
                                    wcmWagnsSer.setDirection(cursor1.getString(16));
                                    wcmWagnsSer.setCopyflg(cursor1.getString(17));
                                    wcmWagnsSer.setMandflg(cursor1.getString(18));
                                    wcmWagnsSer.setDeacflg(cursor1.getString(19));
                                    wcmWagnsSer.setStatus(cursor1.getString(20));
                                    wcmWagnsSer.setAsgnflg(cursor1.getString(21));
                                    wcmWagnsSer.setAutoflg(cursor1.getString(22));
                                    wcmWagnsSer.setAgent(cursor1.getString(23));
                                    wcmWagnsSer.setValflg(cursor1.getString(24));
                                    wcmWagnsSer.setWcmuse(cursor1.getString(25));
                                    wcmWagnsSer.setAction(cursor1.getString(11));
                                    ItWcmWcagns_Al.add(wcmWagnsSer);
                                } while (cursor1.moveToNext());
                            }
                        }
                    } catch (Exception e) {

                    } finally {
                        if (cursor1 != null)
                            cursor1.close();
                    }

                    try {
                        cursor1 = App_db.rawQuery("select * from EtWcmWcagns where Aufnr = ? and Objart = ? and Objnr like ? order by Hilvl", new String[]{wcnr_aufnr, "WA", "WA%"});
                        if (cursor1 != null && cursor.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                                    wcmWagnsSer.setAufnr(cursor1.getString(2));
                                    wcmWagnsSer.setObjnr(cursor1.getString(3));
                                    wcmWagnsSer.setCounter(cursor1.getString(4));
                                    wcmWagnsSer.setWerks(cursor1.getString(12));
                                    wcmWagnsSer.setCrname(cursor1.getString(13));
                                    wcmWagnsSer.setObjart(cursor1.getString(5));
                                    wcmWagnsSer.setObjtyp(cursor1.getString(6));
                                    wcmWagnsSer.setPmsog(cursor1.getString(7));
                                    wcmWagnsSer.setGntxt(cursor1.getString(8));
                                    wcmWagnsSer.setGeniakt(cursor1.getString(9));
                                    wcmWagnsSer.setGenvname(cursor1.getString(10));
                                    wcmWagnsSer.setGendatum(cursor1.getString(26));
                                    wcmWagnsSer.setGentime(cursor1.getString(27));
                                    wcmWagnsSer.setHilvl(Integer.parseInt(cursor1.getString(14)));
                                    wcmWagnsSer.setProcflg(cursor1.getString(15));
                                    wcmWagnsSer.setDirection(cursor1.getString(16));
                                    wcmWagnsSer.setCopyflg(cursor1.getString(17));
                                    wcmWagnsSer.setMandflg(cursor1.getString(18));
                                    wcmWagnsSer.setDeacflg(cursor1.getString(19));
                                    wcmWagnsSer.setStatus(cursor1.getString(20));
                                    wcmWagnsSer.setAsgnflg(cursor1.getString(21));
                                    wcmWagnsSer.setAutoflg(cursor1.getString(22));
                                    wcmWagnsSer.setAgent(cursor1.getString(23));
                                    wcmWagnsSer.setValflg(cursor1.getString(24));
                                    wcmWagnsSer.setWcmuse(cursor1.getString(25));
                                    wcmWagnsSer.setAction(cursor1.getString(11));
                                    ItWcmWcagns_Al.add(wcmWagnsSer);
                                } while (cursor1.moveToNext());
                            }
                        }
                    } catch (Exception e) {

                    } finally {
                        if (cursor1 != null)
                            cursor1.close();
                    }

                    try {
                        cursor1 = App_db.rawQuery("select * from EtWcmWcagns where Aufnr = ? and Objart = ? and Objnr like ? order by Hilvl", new String[]{wcnr_aufnr, "WD", "WD%"});
                        if (cursor1 != null && cursor.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    OrdrWcmWagnsSer wcmWagnsSer = new OrdrWcmWagnsSer();
                                    wcmWagnsSer.setAufnr(cursor1.getString(2));
                                    wcmWagnsSer.setObjnr(cursor1.getString(3));
                                    wcmWagnsSer.setCounter(cursor1.getString(4));
                                    wcmWagnsSer.setWerks(cursor1.getString(12));
                                    wcmWagnsSer.setCrname(cursor1.getString(13));
                                    wcmWagnsSer.setObjart(cursor1.getString(5));
                                    wcmWagnsSer.setObjtyp(cursor1.getString(6));
                                    wcmWagnsSer.setPmsog(cursor1.getString(7));
                                    wcmWagnsSer.setGntxt(cursor1.getString(8));
                                    wcmWagnsSer.setGeniakt(cursor1.getString(9));
                                    wcmWagnsSer.setGenvname(cursor1.getString(10));
                                    wcmWagnsSer.setGendatum(cursor1.getString(26));
                                    wcmWagnsSer.setGentime(cursor1.getString(27));
                                    wcmWagnsSer.setHilvl(Integer.parseInt(cursor1.getString(14)));
                                    wcmWagnsSer.setProcflg(cursor1.getString(15));
                                    wcmWagnsSer.setDirection(cursor1.getString(16));
                                    wcmWagnsSer.setCopyflg(cursor1.getString(17));
                                    wcmWagnsSer.setMandflg(cursor1.getString(18));
                                    wcmWagnsSer.setDeacflg(cursor1.getString(19));
                                    wcmWagnsSer.setStatus(cursor1.getString(20));
                                    wcmWagnsSer.setAsgnflg(cursor1.getString(21));
                                    wcmWagnsSer.setAutoflg(cursor1.getString(22));
                                    wcmWagnsSer.setAgent(cursor1.getString(23));
                                    wcmWagnsSer.setValflg(cursor1.getString(24));
                                    wcmWagnsSer.setWcmuse(cursor1.getString(25));
                                    wcmWagnsSer.setAction(cursor1.getString(11));
                                    ItWcmWcagns_Al.add(wcmWagnsSer);
                                } while (cursor1.moveToNext());
                            }
                        }
                    } catch (Exception e) {

                    } finally {
                        if (cursor1 != null)
                            cursor1.close();
                    }
                }
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

            /*Adding Empty_Al to Arraylist*/
            ArrayList Empty_Al = new ArrayList<>();
            ArrayList<EtWcmWdDataSer> etWcmWdDataSers = new ArrayList<>();
            EtWcmWdDataSer etser = new EtWcmWdDataSer();
            etser.setEtWcmWdDataTagtext(Empty_Al);
            etser.setEtWcmWdDataUntagtext(Empty_Al);
            etWcmWdDataSers.add(etser);

            IvMchkSer ivmo = new IvMchkSer();
            ivmo.setIvAufnr(orderId);
            ivmo.setIvTransmitType(transmitType);
            ivmo.setIvCommit(true);
            ivmo.setFcode("");
            IvMchk_Al.add(ivmo);

            /*Calling Notification Create Model with Data*/
            PermitSer prmtSr = new PermitSer();
            prmtSr.setMuser(username.toUpperCase().toString());
            prmtSr.setDeviceid(device_id);
            prmtSr.setDevicesno(device_serial_number);
            prmtSr.setUdid(device_uuid);
            prmtSr.setOperation(operation);
            prmtSr.setIvMchk(IvMchk_Al);
            prmtSr.setItOrderStatus(ItOrderStatus_Al);
            prmtSr.setItWcmWwData(ItWcmWwData_Al);
            prmtSr.setItWcmWaData(ItWcmWaData_Al);
            prmtSr.setItWcmWaChkReq(ItWcmWaChkReq_Al);
            prmtSr.setItWcmWdData(ItWcmWdData_Al);
            prmtSr.setItWcmWdItemData(ItWcmWdItemData_Al);
            prmtSr.setItWcmWcagns(ItWcmWcagns_Al);
            prmtSr.setItDocs(ItDocs_Al);
//            ordrSer.setIsGeo(IsGeo_Al);
           /* prmtSr.setEsAufnr(Empty_Al);
            prmtSr.setEtOrderHeader(Empty_Al);
            prmtSr.setEtOrderOperations(Empty_Al);
            prmtSr.setEtOrderLongtext(Empty_Al);
            prmtSr.setItOrderPermits(Empty_Al);
            prmtSr.setEtOrderOlist(Empty_Al);*/
            prmtSr.setEtOrderStatus(Empty_Al);
            prmtSr.setEtDocs(Empty_Al);
            prmtSr.setEtWcmWwData(Empty_Al);
            prmtSr.setEtWcmWaData(Empty_Al);
            prmtSr.setEtWcmWdData(etWcmWdDataSers);
            prmtSr.setEtWcmWdItemData(Empty_Al);
            prmtSr.setEtWcmWaChkReq(Empty_Al);
            prmtSr.setEtWcmWcagns(Empty_Al);
            prmtSr.setEtMessages(Empty_Al);
            prmtSr.setEtWcmWdDup(Empty_Al);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call = service.postPermitChange(url_link, prmtSr, basic, map);
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

                        success = false;
                        message = new StringBuffer();
                        if (operation.equals("CRORD")) {

                            if (jsonObject.has("EsAufnr")) {
                                try {
                                    String EtOrderHeader_response_data1 = new Gson().toJson(rs.getD().getEsAufnr().getResults());
                                    JSONArray jsonArray1 = new JSONArray(EtOrderHeader_response_data1);

                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        String Aufnr = jsonArray1.getJSONObject(j).optString("Aufnr");
                                        message.append(jsonArray1.getJSONObject(j).optString("Message"));
                                    }
                                    if (message.toString().startsWith("S")) {
                                        success = true;
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
                        } else {
                            if (jsonObject.has("EtMessages")) {
                                try {
                                    String EtOrderHeader_response_data1 = new Gson().toJson(rs.getD().getEtMessages().getResults());
                                    JSONArray jsonArray1 = new JSONArray(EtOrderHeader_response_data1);

                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        message.append(jsonArray1.getJSONObject(j).optString("Message"));
                                    }
                                    if (message.toString().startsWith("S")) {
                                        success = true;
                                        App_db.execSQL("delete from EtOrderStatus where Aufnr = ?", new String[]{orderId});
                                        App_db.execSQL("delete from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{orderId});
                                        App_db.execSQL("delete from EtWcmWwData where Aufnr = ?", new String[]{orderId});
                                        App_db.execSQL("delete from EtWcmWaData where Aufnr = ?", new String[]{orderId});
                                        App_db.execSQL("delete from EtWcmWdData where Aufnr = ?", new String[]{orderId});
                                        App_db.execSQL("delete from EtWcmWcagns where Aufnr = ?", new String[]{orderId});
                                        App_db.execSQL("delete from EtWcmWdDataTagtext where Aufnr = ?", new String[]{orderId});
                                        App_db.execSQL("delete from EtWcmWdDataUntagtext where Aufnr = ?", new String[]{orderId});

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
                        }

                        if (success) {
                            /*if (jsonObject.has("EtOrderHeader")) {
                                try {
                                    String EtOrderHeader_response_data = new Gson().toJson(rs.getD().getEtOrderHeader().getResults());
                                    JSONArray jsonArray = new JSONArray(EtOrderHeader_response_data);*/
                                   /* for (int j = 0; j < jsonArray.length(); j++) {
                                        String Aufnr = jsonArray.getJSONObject(j).optString("Aufnr");*/
                            HashMap<String, String> uuid_hashmap = new HashMap<String, String>();
                            Cursor cursor2 = null;
                            try {
                                cursor2 = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{orderId});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            uuid_hashmap.put("UUID", cursor2.getString(1));
                                            uuid_hashmap.put("Aufnr", checkempty.check_empty(orderId));
                                        } while (cursor2.moveToNext());
                                    }
                                }
                            } catch (Exception e) {

                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }
                            orders_uuid_list.add(uuid_hashmap);
                                   /* }
                                } catch (Exception e) {
                                }
                            }*/
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
                                            created_aufnr = Aufnr;
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
                                                String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext (UUID, Aufnr, Activity, TextLine) values(?,?,?,?);";
                                                SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                                                EtOrderLongtext_statement.clearBindings();
                                                EtOrderLongtext_statement.bindString(1, uuid);
                                                EtOrderLongtext_statement.bindString(2, Aufnr);
                                                EtOrderLongtext_statement.bindString(3, jsonArray.getJSONObject(j).optString("Activity"));
                                                EtOrderLongtext_statement.bindString(4, jsonArray.getJSONObject(j).optString("TextLine"));
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
                                        String EtDocs_sql = "Insert into DUE_ORDERS_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype) values(?,?,?,?,?,?,?,?,?,?,?);";
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
                                            EtWcmWdData_statement.execute();
                                        }
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
                            Get_Data = created_aufnr;
                        } else {
                            if (jsonObject.has("EtWcmWdDup")) {
                                try {
                                    String EtWcmWdDup_response_data = new Gson().toJson(rs.getD().getEtWcmWdDup().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWdDup_response_data);
                                    if (jsonArray.length() > 0) {
                                        Get_Response = "WCD";
                                        Get_Data = EtWcmWdDup_response_data;
                                    } else {
                                        Get_Response = "";
                                        Get_Data = "";
                                    }

                                } catch (Exception e) {
                                    Get_Response = "";
                                    Get_Data = "";
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                    /*Reading Data by using FOR Loop*/
                }
            } else {
                Get_Response = "Unable to Process your request. Please try again.";
            }
        } catch (
                Exception e)

        {
            Log.v("kiran_ee", e.getMessage() + "...");
            Get_Response = "Unable to Process your request. Please try again.";
        }

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
