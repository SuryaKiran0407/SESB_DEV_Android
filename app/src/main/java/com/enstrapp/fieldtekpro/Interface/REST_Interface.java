package com.enstrapp.fieldtekpro.Interface;


import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Auth_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_BOM_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_FLOC_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_LoadSettings_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_MeasurementPoint_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Notifications_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Orders_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_SyncMap_SER;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_VHLP_SER;
import com.enstrapp.fieldtekpro.Initialload.BOM_SER;
import com.enstrapp.fieldtekpro.Initialload.Calibration_SER;
import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.MIS.BreakStatsPie_SER;
import com.enstrapp.fieldtekpro.MIS.BreakStatsPie_SER_REST;
import com.enstrapp.fieldtekpro.MIS.NotifAnalysis_REST_SER;
import com.enstrapp.fieldtekpro.MIS.NotifAnalysis_SER;
import com.enstrapp.fieldtekpro.MIS.OrderAnalysis_SER;
import com.enstrapp.fieldtekpro.MIS.OrderAnalysis_SER_REST;
import com.enstrapp.fieldtekpro.Utilities.MAC_Model1;
import com.enstrapp.fieldtekpro.Utilities.Material_Availability_Check_SER;
import com.enstrapp.fieldtekpro.Utilities.Model_BOM_RESV_REST;
import com.enstrapp.fieldtekpro.Utilities.SER_BOM_Reservation;
import com.enstrapp.fieldtekpro.Utilities.SER_BOM_Reservation_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.EQ_MaintenancePlan_SER_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.EquipmentHistory_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.EquipmentHistory_SER_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.EquipmentInspection_BreakStatics_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.EquipmentInspection_BreakStatics_SER_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.Equipment_InspChk_SER_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.Geo_Tag_Details_Model_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.INSPCHK_SER;
import com.enstrapp.fieldtekpro.equipment_inspection.INSPCHK_SER_REST;
import com.enstrapp.fieldtekpro.equipment_inspection.MODEL_EQUIP_MAINT_PLAN;
import com.enstrapp.fieldtekpro.equipment_inspection.Model_INSP_CHK_REST;
import com.enstrapp.fieldtekpro.equipment_update.EQ_Update_Model_REST;
import com.enstrapp.fieldtekpro.equipment_update.SER_EQUIP_UPDATE_REST;
import com.enstrapp.fieldtekpro.history.History_SER;
import com.enstrapp.fieldtekpro.history.History_SER_REST;
import com.enstrapp.fieldtekpro.login.REST_SER_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;
import com.enstrapp.fieldtekpro.login.SER_Login;
import com.enstrapp.fieldtekpro.maintenance_plan.MaintenancePlan_SER;
import com.enstrapp.fieldtekpro.maintenance_plan.MaintenancePlan_SER_REST;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Complete_REST;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Create_REST;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_RELEASE_REST;
import com.enstrapp.fieldtekpro.orders.MAC_Model_REST;
import com.enstrapp.fieldtekpro.orders.Model_Order_RELEASE_REST;
import com.enstrapp.fieldtekpro.orders.OrderPartialConfirm_Ser;
import com.enstrapp.fieldtekpro.orders.OrderPartialConfirm_Ser_REST;
import com.enstrapp.fieldtekpro.orders.OrdrCreateSer;
import com.enstrapp.fieldtekpro.orders.OrdrCreateSer_REST;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface REST_Interface
{

    @POST
    Call<REST_SER_Login> postLoginDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<REST_SyncMap_SER> postSyncmapDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<REST_LoadSettings_SER> postLoadSettingsDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<REST_Auth_SER> postAuthDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<REST_VHLP_SER> postVHLPDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<REST_Orders_SER> postORDERDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<REST_Notifications_SER> postNOTIFDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<REST_BOM_SER> postBOMDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);





    @POST
    Call<REST_FLOC_SER> postFLOCDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<REST_MeasurementPoint_SER> postMPointDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<BOM_SER> postDeviceToken(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<Calibration_SER> postCalibrationDetails(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<REST_Notifications_SER> postNotifCreateData(@Url String anEmptyString, @Body Model_Notif_Create_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);




    @POST
    Call<SER_BOM_Reservation_REST> postBOMReservation(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers, @Body Model_BOM_RESV_REST bom_data);



    @POST
    Call<SER_EQUIP_UPDATE_REST> postEQUIUPDATE(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers, @Body EQ_Update_Model_REST bom_data);



    @POST
    Call<MaintenancePlan_SER_REST> postMaintenancePlanData(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<History_SER_REST> getHistoryData(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<NotifAnalysis_REST_SER> getNotifAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);




    @POST
    Call<BreakStatsPie_SER_REST> postBreakStatsPieAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<OrderAnalysis_SER_REST> postOrderAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);


    @POST
    Call<EquipmentInspection_BreakStatics_SER_REST> postEquipmentInspectionAnalysis(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<EquipmentHistory_SER_REST> postEQUIPHistoryData(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);



    @POST
    Call<Equipment_InspChk_SER_REST> postINSPListData(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body Rest_Model_Login body);


    @POST
    Call<REST_Notifications_SER> Post_Notif_Release(@Url String anEmptyString, @Body Model_Notif_RELEASE_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<REST_Notifications_SER> postNotif_Complete(@Url String anEmptyString, @Body Model_Notif_Complete_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<REST_Notifications_SER> Post_Notif_PostPone(@Url String anEmptyString, @Body Model_Notif_RELEASE_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);



    @POST
    Call<INSPCHK_SER_REST> postINSPCHK(@Url String anEmptyString, @Body Model_INSP_CHK_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<SER_Login> PostGeoTagData(@Url String anEmptyString, @Body Geo_Tag_Details_Model_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<EQ_MaintenancePlan_SER_REST> postEQMaintenancePlanData(@Url String anEmptyString, @Header("Authorization") String authHeader, @Body MODEL_EQUIP_MAINT_PLAN body);



    @POST
    Call<REST_Orders_SER> postCreateOrder(@Url String anEmptyString, @Body OrdrCreateSer_REST ordrCreateSer, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<REST_Orders_SER> getORDERDetails(@Url String anEmptyString, @Body Model_Order_RELEASE_REST body, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<REST_Orders_SER> postConfirmOrder(@Url String anEmptyString, @Body OrderPartialConfirm_Ser_REST orderPartialConfirmSer, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers);


    @POST
    Call<Material_Availability_Check_SER> getMaterial_Availability_Check(@Url String anEmptyString, @Header("Authorization") String authHeader, @HeaderMap Map<String, String> headers, @Body MAC_Model_REST macm);
}
