package com.enstrapp.fieldtekpro.Interface;


import com.enstrapp.fieldtekpro.Calibration.Model_Notif_Calibration;
import com.enstrapp.fieldtekpro.Initialload.Auth_SER;
import com.enstrapp.fieldtekpro.Initialload.BOM_SER;
import com.enstrapp.fieldtekpro.Initialload.Calibration_SER;
import com.enstrapp.fieldtekpro.Initialload.FLOC_SER;
import com.enstrapp.fieldtekpro.Initialload.JSA_SER;
import com.enstrapp.fieldtekpro.Initialload.LoadSettings_SER;
import com.enstrapp.fieldtekpro.Initialload.MeasurementPoint_SER;
import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Initialload.SyncMap_SER;
import com.enstrapp.fieldtekpro.Initialload.Token_SER;
import com.enstrapp.fieldtekpro.Initialload.VHLP_SER;
import com.enstrapp.fieldtekpro.Initialload.VHLP_WCM_SER;
import com.enstrapp.fieldtekpro.MIS.BreakStatsPie_SER;
import com.enstrapp.fieldtekpro.MIS.NotifAnalysis_SER;
import com.enstrapp.fieldtekpro.MIS.OrderAnalysis_SER;
import com.enstrapp.fieldtekpro.MIS.Permit_Reports_SER;
import com.enstrapp.fieldtekpro.PermitList.Model_Permit_Isolation;
import com.enstrapp.fieldtekpro.Utilities.MAC_Model1;
import com.enstrapp.fieldtekpro.Utilities.Material_Availability_Check_SER;
import com.enstrapp.fieldtekpro.Utilities.Model_BOM_Reservation;
import com.enstrapp.fieldtekpro.Utilities.SER_BOM_Reservation;
import com.enstrapp.fieldtekpro.equipment_inspection.EQ_MaintenancePlan_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.EquipmentHistory_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.EquipmentInspection_BreakStatics_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.Equipment_InspChk_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.Geo_Tag_Model;
import com.enstrapp.fieldtekpro.equipment_inspection.INSPCHK_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.Model_INSP_CHK;
import com.enstrapp.fieldtekpro.history.History_SER;
import com.enstrapp.fieldtekpro.login.SER_Login;
import com.enstrapp.fieldtekpro.maintenance_plan.MaintenancePlan_SER;
import com.enstrapp.fieldtekpro.notifications.Attachment_Download_SER;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create;
import com.enstrapp.fieldtekpro.orders.JSA_Create_SER;
import com.enstrapp.fieldtekpro.orders.JSA_List_SER;
import com.enstrapp.fieldtekpro.orders.JSA_Model_Create;
import com.enstrapp.fieldtekpro.orders.OrderPartialConfirm_Ser;
import com.enstrapp.fieldtekpro.orders.OrdrCreateSer;
import com.enstrapp.fieldtekpro.orders.PermitSer;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Interface
{

    @GET
    Call<SER_Login> getLoginDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<SyncMap_SER> getSyncmapDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<LoadSettings_SER> getLoadSettingsDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<VHLP_SER> getVHLPDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<VHLP_WCM_SER> getVHLPWCMDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Orders_SER> getORDERDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Notifications_SER> getNOTIFICATIONDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<BOM_SER> getBOMDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<FLOC_SER> getFLOCDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Auth_SER> getAuthDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Calibration_SER> getCalibrationDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<JSA_SER> getJRADetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<BOM_SER> setDeviceToken(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<MeasurementPoint_SER> getMPointDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Token_SER> getToken(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Material_Availability_Check_SER> getMaterial_Availability_Check(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers, @Body MAC_Model1 macm);

    @GET
    Call<History_SER> getHistoryData(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<MaintenancePlan_SER> getMaintenancePlanData(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @GET
    Call<EQ_MaintenancePlan_SER> getEQMaintenancePlanData(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<JSA_List_SER> getJSAListData(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<EquipmentHistory_SER> getEQUIPHistoryData(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Equipment_InspChk_SER> getINSPListData(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Notifications_SER> Notif_Release(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Notifications_SER> Notif_Complete(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Notifications_SER> getNotifCreateData(@Url String anEmptyString, @Body Model_Notif_Create body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Calibration_SER> PostCalibrationData(@Url String anEmptyString, @Body Model_Notif_Calibration body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<SER_BOM_Reservation> postBOMReservation(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers, @Body Model_BOM_Reservation bom_data);

    @POST
    Call<INSPCHK_SER> postINSPCHK(@Url String anEmptyString, @Body Model_INSP_CHK body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Orders_SER> postConfirmOrder(@Url String anEmptyString, @Body OrderPartialConfirm_Ser orderPartialConfirmSer, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Orders_SER> postCreateOrder(@Url String anEmptyString, @Body OrdrCreateSer ordrCreateSer, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Orders_SER> postpermitisolationData(@Url String anEmptyString, @Body Model_Permit_Isolation body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<OrderAnalysis_SER> getOrderAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<NotifAnalysis_SER> getNotifAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<EquipmentInspection_BreakStatics_SER> getEquipmentInspectionAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<JSA_Create_SER> postJSACreateData(@Url String anEmptyString, @Body JSA_Model_Create body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<BreakStatsPie_SER> getBreakStatsPieAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @GET
    Call<Permit_Reports_SER> getPermitReport_Analysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

    @POST
    Call<Orders_SER> postPermitChange(@Url String anEmptyString, @Body PermitSer permitSer, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<SER_Login> PostGeoTagData(@Url String anEmptyString, @Body Geo_Tag_Model body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @GET
    Call<Attachment_Download_SER> getAttachmentURLDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);

}
