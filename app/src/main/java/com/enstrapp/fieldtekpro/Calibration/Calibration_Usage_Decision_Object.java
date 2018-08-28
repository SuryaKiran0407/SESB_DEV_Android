package com.enstrapp.fieldtekpro.Calibration;

final class Calibration_Usage_Decision_Object
{
	private final String udcode_id;
	private final String udcode_text;
	private final String quality_score;
	private final String notes;

	public String getUdcode_id() {
		return udcode_id;
	}

	public String getUdcode_text() {
		return udcode_text;
	}

	public String getQuality_score() {
		return quality_score;
	}

	public String getNotes() {
		return notes;
	}

	public Calibration_Usage_Decision_Object(String udcode_id, String udcode_text, String quality_score, String notes)
	{
		this.udcode_id = udcode_id;
		this.udcode_text = udcode_text;
		this.quality_score = quality_score;
		this.notes = notes;
	}



}