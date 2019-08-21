package com.kakao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.model.TransInfoByCustomerVO;
import com.kakao.model.TransVO;
import com.kakao.service.TransService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private TransService transService;
	
	private final int FROM_YEAR = 2018;
	private final int TO_YEAR = 2019;
	
	/**
	 *  1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.
	 *     (단, 취소여부가 ‘Y’ 거래는 취소된 거래임, 합계 금액은 거래금액에서 수수료를 차감한 금액임)
	 */
	@RequestMapping(params = "func=getLargestSumAmtCustomerByYear", method = RequestMethod.GET)
	public Object getLargestSumAmtCustomerByYear() {
		
		TransVO transVO = new TransVO();
		transVO.setFromYear(FROM_YEAR);
		transVO.setToYear(TO_YEAR);
		
		List<TransInfoByCustomerVO> result = transService.getLargestSumAmtCustomerByYear(transVO);
		
		return result;
	}
	
	
	/**
	 *  2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
	 *     (취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@RequestMapping(params = "func=getNoTransCustomerListByYear", method = RequestMethod.GET)
	public Object getNoTransCustomerListByYear() {
		
		TransVO transVO = new TransVO();
		transVO.setFromYear(FROM_YEAR);
		transVO.setToYear(TO_YEAR);
		
		List<TransInfoByCustomerVO> result = transService.getNoTransCustomerListByYear(transVO);
		
		return result;
	}
}
