package com.kakao.model;

import org.springframework.stereotype.Component;

@Component
public class BranchVO extends SearchCriteria {
	
	private String brName;
	private String brCode;
	
	public BranchVO() {}
	
	public BranchVO(String brName, String brCode) {
		this.brName = brName;
		this.brCode = brCode;
	}
	
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getBrCode() {
		return brCode;
	}
	public void setBrCode(String brCode) {
		this.brCode = brCode;
	}
}
