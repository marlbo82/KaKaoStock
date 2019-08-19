package com.kakao.model;

import org.springframework.stereotype.Component;

@Component
public class AcctVO extends SearchCriteria {
	
	private String acctNo;
	private String acctName;
	private String brCode;
	
	public AcctVO() {}
	
	public AcctVO(String acctNo, String acctName, String brCode) {
		this.acctNo = acctNo;
		this.acctName = acctName;
		this.brCode = brCode;
	}
	
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
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
}
