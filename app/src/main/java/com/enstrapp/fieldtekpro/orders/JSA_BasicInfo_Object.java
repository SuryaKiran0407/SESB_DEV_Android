package com.enstrapp.fieldtekpro.orders;

final class JSA_BasicInfo_Object
{
	private final String title;
	private final String remark;
	private final String job_id;
	private final String job_text;
	private final String aufnr;

	public String getTitle() {
		return title;
	}

	public String getRemark() {
		return remark;
	}

	public String getJob_id() {
		return job_id;
	}

	public String getJob_text() {
		return job_text;
	}

	public String getAufnr() {
		return aufnr;
	}

	public JSA_BasicInfo_Object(String title, String remark, String job_id, String job_text, String aufnr)
	{
		this.title = title;
		this.remark = remark;
		this.job_id = job_id;
		this.job_text = job_text;
		this.aufnr = aufnr;
	}

}