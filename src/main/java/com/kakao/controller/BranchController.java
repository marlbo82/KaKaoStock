package com.kakao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.model.BranchVO;
import com.kakao.model.TransVO;
import com.kakao.service.BranchService;

@RestController
@RequestMapping("/branches")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	private final int FROM_YEAR = 2018;
	private final int TO_YEAR = 2019;
	
	/**
	 *  3. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
	 *     (취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@RequestMapping(params = "func=getSumAmtBranchListByYear", method = RequestMethod.GET)
	public Object getSumAmtBranchList() {
		
		TransVO transVO = new TransVO();
		transVO.setFromYear(FROM_YEAR);
		transVO.setToYear(TO_YEAR);
		
		List<Map<String, Object>> result = branchService.getSumAmtBranchList(transVO);
		
		return result;
	}
	
	
	/**
	 *  4. 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다.
	 *     지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
	 *     (취소여부가 ‘Y’ 거래는 취소된 거래임)
	 */
	@RequestMapping(params = "func=getSumAmtBranch", method = RequestMethod.GET)
	public Object getSumAmtBranch(HttpServletResponse response,
								  @RequestBody BranchVO branchVO) {
		
		Object result = null;
		
		try {
			result = branchService.getSumAmtBranch(branchVO);
		} catch (Exception e) {
			return handleNotFoundException(e, response);
		}
		
		return result;
	}
	
	/**
	 * ExceptionHandler
	 */
	@ExceptionHandler(Exception.class)
	public Object handleNotFoundException(Exception e, HttpServletResponse response) {
		response.setStatus(404);

		Map<String, String> exceptionMap = new HashMap<String, String>();
		exceptionMap.put("code", response.getStatus()+"");
		exceptionMap.put("메세지", e.getMessage());
		
		return exceptionMap;
	}
}
