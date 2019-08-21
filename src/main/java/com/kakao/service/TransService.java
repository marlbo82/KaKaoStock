package com.kakao.service;

import java.util.List;

import com.kakao.model.TransVO;
import com.kakao.model.TransInfoByCustomerVO;

public interface TransService {
	public List<TransInfoByCustomerVO> getLargestSumAmtCustomerByYear(TransVO transVO);
	public List<TransInfoByCustomerVO> getNoTransCustomerListByYear(TransVO transVO);
}
