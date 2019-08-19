package com.kakao.service;

import java.util.List;
import java.util.Map;

import com.kakao.model.TransHistoryVO;
import com.kakao.model.TransInfoByCustomerVO;

public interface BranchService {
	public List<Map<String, Object>> getSumAmtByCustomer(TransHistoryVO transHistoryVO);
	public List<TransInfoByCustomerVO> getNoTransCustomers(TransHistoryVO transHistoryVO);
}
