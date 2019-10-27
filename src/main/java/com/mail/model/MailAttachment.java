package com.mail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MailAttachment {
	
	@JsonProperty
	private double close;
	
	
	public MailAttachment() {
		
	}
	
public MailAttachment(double close) {
	
	this.close=close;
		
	}
	

	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}

	
	@Override
    public String toString() {
        return "MailAttachment{ close=" + close +
                '}';
    }
}
