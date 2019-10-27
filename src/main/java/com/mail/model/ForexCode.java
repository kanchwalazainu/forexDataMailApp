package com.mail.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FOREXCODE")
public class ForexCode {
	@Id
	private String code;
	
	public ForexCode() {
		
	}

	public ForexCode(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
