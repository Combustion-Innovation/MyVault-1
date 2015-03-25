package com.myvault.myvault;

import java.io.Serializable;

public class Insurance implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String insurancecompany;
	String member;
	String memid;
	String groupnum;
	String type;
	String code;
	String notes;
	
	public Insurance() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInsurancecompany() {
		return insurancecompany;
	}

	public void setInsurancecompany(String insurancecompany) {
		this.insurancecompany = insurancecompany;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getMemid() {
		return memid;
	}

	public void setMemid(String memid) {
		this.memid = memid;
	}

	public String getGroupnum() {
		return groupnum;
	}

	public void setGroupnum(String groupnum) {
		this.groupnum = groupnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	

}
