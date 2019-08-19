package com.kakao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.model.TransHistoryVO;
import com.kakao.model.TransInfoByCustomerVO;
import com.kakao.service.TransHistoryService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private TransHistoryService transHistoryService;
		
	@RequestMapping(name = "get_largest_sumAmt_customer_by_year", value = "/amounts", method = RequestMethod.GET)
	public @ResponseBody Object getSumAmtCustomerList(HttpServletRequest request,
													  @RequestParam(value = "fromYear", required = false) @DateTimeFormat(pattern="yyyy") int fromDt,
													  @RequestParam(value = "toYear", required = false) @DateTimeFormat(pattern="yyyy") int toDt) {
		
		TransHistoryVO transHistoryVO = new TransHistoryVO();
		transHistoryVO.setFromYear(fromDt);
		transHistoryVO.setToYear(toDt);
		
		List<TransInfoByCustomerVO> result = transHistoryService.getSumAmtByCustomer(transHistoryVO);
		
		return result;
	}
	
	@RequestMapping(name = "get_noTrans_customer_by_year", value = "/trans", method = RequestMethod.GET)
	public @ResponseBody Object getNoTransCustomerList(HttpServletRequest request,
													   @RequestParam(value = "fromYear", required = false) @DateTimeFormat(pattern="yyyy") int fromDt,
													   @RequestParam(value = "toYear", required = false) @DateTimeFormat(pattern="yyyy") int toDt) {
		
		TransHistoryVO transHistoryVO = new TransHistoryVO();
		transHistoryVO.setFromYear(fromDt);
		transHistoryVO.setToYear(toDt);
		
		List<TransInfoByCustomerVO> result = transHistoryService.getNoTransCustomers(transHistoryVO);
		
		return result;
	}
}
