package com.enstrapp.fieldtekpro.notifications;

import java.util.ArrayList;
import java.util.HashMap;

final class Notifications_Create_Header_Object
{
	public String getNotification_type_id() {
		return notification_type_id;
	}

	public String getNotification_type_text() {
		return notification_type_text;
	}

	public String getNotifshtTxt() {
		return notifshtTxt;
	}

	public String getFunctionlocation_id() {
		return functionlocation_id;
	}

	public String getFunctionlocation_text() {
		return functionlocation_text;
	}

	public String getEquipment_id() {
		return equipment_id;
	}

	public String getEquipment_text() {
		return equipment_text;
	}

	public String getWorkcenter_id() {
		return workcenter_id;
	}

	public String getWorkcenter_text() {
		return workcenter_text;
	}

	public String getPriority_type_id() {
		return priority_type_id;
	}

	public String getPriority_type_text() {
		return priority_type_text;
	}

	public String getPlannergroup_id() {
		return plannergroup_id;
	}

	public String getPlannergroup_text() {
		return plannergroup_text;
	}

	public String getReportedby() {
		return reportedby;
	}

	public String getPrimUsrResp() {
		return primUsrResp;
	}

	public String getPersonresponsible_id() {
		return personresponsible_id;
	}

	public String getPersonresponsible_text() {
		return personresponsible_text;
	}

	public String getReq_stdate_date_formated() {
		return req_stdate_date_formated;
	}

	public String getReq_stdate_time_formatted() {
		return req_stdate_time_formatted;
	}

	public String getReq_end_date_formatted() {
		return req_end_date_formatted;
	}

	public String getReq_end_time_formatted() {
		return req_end_time_formatted;
	}

	public String getMal_st_date_formatted() {
		return mal_st_date_formatted;
	}

	public String getMal_st_time_formatted() {
		return mal_st_time_formatted;
	}

	public String getMal_end_date_formatted() {
		return mal_end_date_formatted;
	}

	public String getMal_end_time_formatted() {
		return mal_end_time_formatted;
	}

	public String getEffect_id() {
		return effect_id;
	}

	public String getEffect_text() {
		return effect_text;
	}

	public String getPlant_id() {
		return plant_id;
	}
	public String getLongtext_text() {
		return longtext_text;
	}
	public ArrayList<HashMap<String, String>> getCustom_info_arraylist() {
		return custom_info_arraylist;
	}

	private final String notification_type_id;
	private final String notification_type_text;
	private final String notifshtTxt;
	private final String functionlocation_id;
	private final String functionlocation_text;
	private final String equipment_id;
	private final String equipment_text;
	private final String workcenter_id;
	private final String workcenter_text;
	private final String priority_type_id;
	private final String priority_type_text;
	private final String plannergroup_id;
	private final String plannergroup_text;
	private final String reportedby;
	private final String primUsrResp;
	private final String personresponsible_id;
	private final String personresponsible_text;
	private final String req_stdate_date_formated;
	private final String req_stdate_time_formatted;
	private final String req_end_date_formatted;
	private final String req_end_time_formatted;
	private final String mal_st_date_formatted;
	private final String mal_st_time_formatted;
	private final String mal_end_date_formatted;
	private final String mal_end_time_formatted;
	private final String effect_id;
	private final String effect_text;
	private final String plant_id;
	private final String longtext_text;
	private final ArrayList<HashMap<String, String>> custom_info_arraylist;


	public Notifications_Create_Header_Object(String notification_type_id, String notification_type_text, String notifshtTxt, String functionlocation_id, String functionlocation_text, String equipment_id, String equipment_text, String workcenter_id, String workcenter_text, String priority_type_id, String priority_type_text, String plannergroup_id, String plannergroup_text, String reportedby, String primUsrResp, String personresponsible_id, String personresponsible_text, String req_stdate_date_formated, String req_stdate_time_formatted, String req_end_date_formatted, String req_end_time_formatted, String mal_st_date_formatted, String mal_st_time_formatted, String mal_end_date_formatted, String mal_end_time_formatted, String effect_id, String effect_text, String plant_id, String longtext_text, ArrayList<HashMap<String, String>> custom_info_arraylist)
	{
		this.notification_type_id = notification_type_id;
		this.notification_type_text = notification_type_text;
		this.notifshtTxt = notifshtTxt;
		this.functionlocation_id = functionlocation_id;
		this.functionlocation_text = functionlocation_text;
		this.equipment_id = equipment_id;
		this.equipment_text = equipment_text;
		this.workcenter_id = workcenter_id;
		this.workcenter_text = workcenter_text;
		this.priority_type_id = priority_type_id;
		this.priority_type_text = priority_type_text;
		this.plannergroup_id = plannergroup_id;
		this.plannergroup_text = plannergroup_text;
		this.reportedby = reportedby;
		this.primUsrResp = primUsrResp;
		this.personresponsible_id = personresponsible_id;
		this.personresponsible_text = personresponsible_text;
		this.req_stdate_date_formated = req_stdate_date_formated;
		this.req_stdate_time_formatted = req_stdate_time_formatted;
		this.req_end_date_formatted = req_end_date_formatted;
		this.req_end_time_formatted = req_end_time_formatted;
		this.mal_st_date_formatted = mal_st_date_formatted;
		this.mal_st_time_formatted = mal_st_time_formatted;
		this.mal_end_date_formatted = mal_end_date_formatted;
		this.mal_end_time_formatted = mal_end_time_formatted;
		this.effect_id = effect_id;
		this.effect_text = effect_text;
		this.plant_id = plant_id;
		this.longtext_text = longtext_text;
		this.custom_info_arraylist = custom_info_arraylist;
	}



}