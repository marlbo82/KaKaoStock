package com.kakao.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TransAllInfoVO extends SearchCriteria {
	
	private Date transDate;
	private String acctNo;;
	private String transNo;
	private Integer amount;
	private Integer charge;
	private String isCancel;
	private String acctName;
	private String brCode;
	private String brName;
	
	public TransAllInfoVO() {}
	
	public TransAllInfoVO(Date transDate, String acctNo, String transNo, Integer amount, Integer charge, String isCancel, String acctName, String brCode, String brName) {
		this.transDate = transDate;
		this.acctNo = acctNo;
		this.transNo = transNo;
		this.amount = amount;
		this.charge = charge;
		this.isCancel = isCancel;
		this.acctName = acctName; 
		this.brCode = brCode;
		this.brName = brName;
	}

	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
	public String getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getBrCode() {
		return brCode;
	}

	public void setBrCode(String brCode) {
		this.brCode = brCode;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}
}
