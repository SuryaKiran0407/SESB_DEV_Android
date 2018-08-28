package com.enstrapp.fieldtekpro.orders;

final class JSA_JobLocation_Object
{
	private final String opstatus_type_id;
	private final String opstatus_type_text;
	private final String loc_type_id;
	private final String loc_type_text;

	public String getOpstatus_type_id() {
		return opstatus_type_id;
	}

	public String getOpstatus_type_text() {
		return opstatus_type_text;
	}

	public String getLoc_type_id() {
		return loc_type_id;
	}

	public String getLoc_type_text() {
		return loc_type_text;
	}

	public JSA_JobLocation_Object(String opstatus_type_id, String opstatus_type_text, String loc_type_id, String loc_type_text)
	{
		this.opstatus_type_id = opstatus_type_id;
		this.opstatus_type_text = opstatus_type_text;
		this.loc_type_id = loc_type_id;
		this.loc_type_text = loc_type_text;
	}

}