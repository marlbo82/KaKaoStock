package com.kakao.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Component
@SuppressWarnings("deprecation")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class TransInfoByCustomerVO {
	
	private Integer year;
	private String name;
	private String acctNo;
	private Integer sumAmt;
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public Integer getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(Integer sumAmt) {
		this.sumAmt = sumAmt;
	}
}
