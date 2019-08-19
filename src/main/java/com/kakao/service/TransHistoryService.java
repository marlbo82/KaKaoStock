package com.kakao.service;

import java.util.List;

import com.kakao.model.TransHistoryVO;
import com.kakao.model.TransInfoByCustomerVO;

public interface TransHistoryService {
	public List<TransInfoByCustomerVO> getSumAmtByCustomer(TransHistoryVO transHistoryVO);
	public List<TransInfoByCustomerVO> getNoTransCustomers(TransHistoryVO transHistoryVO);
}
