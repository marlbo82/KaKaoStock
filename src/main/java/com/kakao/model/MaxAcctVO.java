package com.kakao.model;

import org.springframework.stereotype.Component;

@Component
public class MaxAcctVO {
	
	private Integer maxSumAmt = 0;
	private String acctNo;
	
	public Integer getMaxSumAmt() {
		return maxSumAmt;
	}
	public void setMaxSumAmt(Integer maxSumAmt) {
		this.maxSumAmt = maxSumAmt;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
}
