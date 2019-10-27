package com.mail.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DATADETAILS")
public class DataDetails {
	@Id
	private String datadate;
	private String data;
	
	
	public DataDetails() {
		
	}
	
	public DataDetails(String date, String data) {
		
		this.datadate = date;
		this.data = data;
	}
	public String getDatadate() {
		return datadate;
	}
	public void setDatadate(String date) {
		this.datadate = date;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
