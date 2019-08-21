package com.kakao.model;

import org.springframework.stereotype.Component;

@Component
public class BranchVO extends SearchCriteria {
	
	private String brCode;
	private String brName;
	
	public BranchVO() {}
	
	public BranchVO(String brCode, String brName) {
		this.brCode = brCode;
		this.brName = brName;
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
