package com.kakao.service;

import java.util.List;
import java.util.Map;

import com.kakao.model.BranchVO;
import com.kakao.model.TransVO;
import com.kakao.model.TransInfoByBranchVO;

public interface BranchService {
	public List<Map<String, Object>> getSumAmtBranchList(TransVO transVO);
	public TransInfoByBranchVO getSumAmtBranch(BranchVO branchVO) throws Exception;
}
