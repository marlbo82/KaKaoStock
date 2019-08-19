package com.kakao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.dao.AcctDao;
import com.kakao.dao.BranchDao;
import com.kakao.dao.TransHistoryDao;
import com.kakao.model.TransHistoryVO;
import com.kakao.model.TransInfoByCustomerVO;

@Service("com.kakao.service.BranchService")
public class BranchServiceImpl implements BranchService {
	
	@Autowired
	private TransHistoryDao transHistoryDao;
	
	@Autowired
	private AcctDao acctDao;
	
	@Autowired
	private BranchDao branchDao;

	@Override
	public List<Map<String, Object>> getSumAmtByCustomer(TransHistoryVO transHistoryVO) {
		return null;
	}

	@Override
	public List<TransInfoByCustomerVO> getNoTransCustomers(TransHistoryVO transHistoryVO) {
		return null;
	}
}