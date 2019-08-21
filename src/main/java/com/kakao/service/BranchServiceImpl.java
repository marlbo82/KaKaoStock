package com.kakao.service;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.dao.AcctDao;
import com.kakao.dao.BranchDao;
import com.kakao.dao.TransDao;
import com.kakao.model.BranchVO;
import com.kakao.model.TransAllInfoVO;
import com.kakao.model.TransInfoByBranchVO;
import com.kakao.model.TransVO;

@Service("com.kakao.service.BranchService")
public class BranchServiceImpl implements BranchService {
	
	@Autowired
	private TransDao transDao;
	
	@Autowired
	private BranchDao branchDao;
	
	@Autowired
	private AcctDao acctDao;
	
	@Override
	public List<Map<String, Object>> getSumAmtBranchList(TransVO transVO) {		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<Integer, Map<String, TransInfoByBranchVO>> sumAmtBranchByYearMap = new HashMap<Integer, Map<String, TransInfoByBranchVO>>();
		
		/** Date 형태로 검색 날짜 형변환 */
		Date fromTgtDate = new Date();
		Date toTgtDate = new Date();
		try {
			fromTgtDate = new SimpleDateFormat("yyyy").parse(transVO.getFromYear()+"");
			toTgtDate = new SimpleDateFormat("yyyy").parse((transVO.getToYear()+1)+"");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<TransAllInfoVO> transList = transDao.getJoinedAllList();
		Map<String, TransInfoByBranchVO> initAllBranchMap = branchDao.getTransInfoByBranchMap();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
		for (TransAllInfoVO item : transList) {
			/** 취소되지 않은 거래들 중, 검색 조건으로 주어진 년도 범위내 데이터만 검색 */
			if ("Y".equals(item.getIsCancel()) && item.getTransDate().after(fromTgtDate)
											   && item.getTransDate().before(toTgtDate)) {
				
				int tgtYear = Integer.parseInt(simpleDateFormat.format(item.getTransDate()));
				
				/** 기존에 있는 년도 정보가 나오면, 
				 *  brCode를 key로 하고, 관리점 정보(거래 합계 포함) VO를 value로 하는 map(Map<String, TransInfoByBranchVO>) 세팅 후,
				 *  해당 년도 값을 key로 하는 Map value로 update 해주기
				 */
				if (sumAmtBranchByYearMap.get(tgtYear) != null) {
					/** brCode를 key로 하고,
					 *  관리점 정보(거래 합계 포함) VO를 value로 하는 map(Map<String, TransInfoByBranchVO>) 세팅
					 */
					if (sumAmtBranchByYearMap.get(tgtYear).get(item.getBrCode()) != null) {
						sumAmtBranchByYearMap.get(tgtYear).get(item.getBrCode()).setSumAmt((sumAmtBranchByYearMap.get(tgtYear).get(item.getBrCode()).getSumAmt() == null ? 0 : sumAmtBranchByYearMap.get(tgtYear).get(item.getBrCode()).getSumAmt()) + item.getAmount());
					} else {
						sumAmtBranchByYearMap.get(tgtYear).get(item.getBrCode()).setSumAmt(item.getAmount());
					}
				} else {
					/** 새로운 년도 정보가 나오면, 
					 *  brCode를 key로 하고, 관리점 정보(거래 합계 포함) VO를 value로 하는 map(Map<String, TransInfoByBranchVO>) 세팅 후,
					 *  새로운 년도 값을 key로 하는 Map value로 mapping 해주기
					 */
					Gson gson = new Gson();
					String jsonString = gson.toJson(initAllBranchMap);
					Type type = new TypeToken<HashMap<String, TransInfoByBranchVO>>(){}.getType();
					
					HashMap<String, TransInfoByBranchVO> clonedMap = gson.fromJson(jsonString, type);	// 전체 관리점 정보 초기값으로 복제 (sumAmt가 0인 경우도 표현해주기 위함) 
					clonedMap.get(item.getBrCode()).setSumAmt(item.getAmount());
					sumAmtBranchByYearMap.put(tgtYear, clonedMap);
				}
			}
		}
		
		/** 내림차순 정렬을 위한 Array 생성 및 주입, 정렬 */
		TransInfoByBranchVO[] arr = null;
		int arrIdx;
		for (int keyi : sumAmtBranchByYearMap.keySet()) {
			arrIdx = 0;
			arr = new TransInfoByBranchVO[sumAmtBranchByYearMap.get(keyi).keySet().size()];
			for (String keyj : sumAmtBranchByYearMap.get(keyi).keySet()) {
				arr[arrIdx] = sumAmtBranchByYearMap.get(keyi).get(keyj);
				arrIdx++;
			}
			
			Arrays.sort(arr, new Comparator<TransInfoByBranchVO>() {
				@Override
				public int compare(TransInfoByBranchVO o1, TransInfoByBranchVO o2) {
					if(o1.getSumAmt() > o2.getSumAmt()) {
						return -1;
					} else if(o1.getSumAmt() < o2.getSumAmt()) {
						return 1;
					}
					
					return 0;						
				}
			});
			
			Arrays.asList(arr);
			Map<String, Object> resultByYear = new HashMap<String, Object>();
			resultByYear.put("year", keyi);
			resultByYear.put("dataList", Arrays.asList(arr));
			result.add(resultByYear);
		}
		
		return result;
	}
	
	@Override
	public TransInfoByBranchVO getSumAmtBranch(BranchVO branchVO) throws Exception {
		TransInfoByBranchVO result = new TransInfoByBranchVO();
		
		/** 관리점명이 없을 경우 Exception 발생 */
		if ("".equals(branchVO.getBrName()) || branchVO.getBrName() == null) throw new Exception("br name not found error");
		
		/** 분당점 입력이 들어올 경우 Exception 발생 */
		if ("분당점".equals(branchVO.getBrName())) throw new Exception("br code not found error");
		
		/** 검색 관리점 정보 가져오기 */
		BranchVO branchInfo = branchDao.getBranch(branchVO.getBrName());
		
		/** 검색 관리점 정보가 null이면 없는 관리점이라고 Exception 발생 */
		if (branchInfo == null) throw new Exception("br name not found error");
		
		/** 검색 관리점 정보 세팅하기 */
		result.setBrCode(branchInfo.getBrCode());
		result.setBrName(branchInfo.getBrName());
		
		/** 검색된 관리점 정보를 이용해, 해당 관리점에서 관리하는 모든 acctNo list 가져오기 */
		List<String> acctNoList = acctDao.getAcctNoListAfterIntgration(branchInfo);
		
		/** 검색된 관리점에서 관리하는 모든 acctNo list를 이용해, 해당하는 모든 거래 가져오기 */
		List<TransVO> transList = transDao.getTransListByAcctList(acctNoList);
		
		/** 관리점 전체 거래량 계산하기 */
		int sumAmt = 0;
		for (TransVO item : transList) {
			if ("Y".equals(item.getIsCancel())) {
				sumAmt += item.getAmount();
			}
		}
		
		/** 전체 거래량 세팅하기 */
		result.setSumAmt(sumAmt);
		
		return result;
	}
}