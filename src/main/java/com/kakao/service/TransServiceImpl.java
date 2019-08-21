package com.kakao.service;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.dao.AcctDao;
import com.kakao.dao.TransDao;
import com.kakao.model.AcctVO;
import com.kakao.model.MaxAcctVO;
import com.kakao.model.TransInfoByBranchVO;
import com.kakao.model.TransInfoByCustomerVO;
import com.kakao.model.TransVO;

@Service("com.kakao.service.TransService")
public class TransServiceImpl implements TransService {
	
	@Autowired
	private TransDao transDao;
	
	@Autowired
	private AcctDao acctDao;

	@Override
	public List<TransInfoByCustomerVO> getLargestSumAmtCustomerByYear(TransVO transVO) {
		/** return 결과 값을 담기 위한 Object */
		List<TransInfoByCustomerVO> result = new ArrayList<TransInfoByCustomerVO>();
		
		/** Date 형태로 검색 날짜 형변환 */
		Date fromTgtDate = new Date();
		Date toTgtDate = new Date();
		try {
			fromTgtDate = new SimpleDateFormat("yyyy").parse(transVO.getFromYear()+"");
			toTgtDate = new SimpleDateFormat("yyyy").parse((transVO.getToYear()+1)+"");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/** 년도 범위내, 년도별 합계 금액이 많은 고객의 acctNo 및 sumAmt를 담기 위한 Map 세팅 */
		Map<Integer, MaxAcctVO> sumAmtMap = new HashMap<Integer, MaxAcctVO>();
		for (int i = 0; i < ((transVO.getToYear() - transVO.getFromYear()) + 1); i++) {
			MaxAcctVO tmpMaxAcctVO = new MaxAcctVO(); 
			sumAmtMap.put((transVO.getFromYear() + i), tmpMaxAcctVO);
		}
		
		/** 년도 범위내, 년도별 합계 금액이 많은 고객의 acctNo 및 sumAmt 추출 */
		Map<String, TransInfoByCustomerVO> map = new HashMap<String, TransInfoByCustomerVO>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
		for (TransVO item : transDao.getTransList()) {
			/** 취소되지 않은 거래들 중, 검색 조건으로 주어진 년도 범위내 데이터만 검색 */
			if ("Y".equals(item.getIsCancel()) && item.getTransDate().after(fromTgtDate)
											   && item.getTransDate().before(toTgtDate)) {
				int sumAmt = 0;
				if (map.get(item.getAcctNo()) != null) {
					sumAmt = map.get(item.getAcctNo()).getSumAmt() + (item.getAmount() - item.getCharge());
					map.get(item.getAcctNo()).setSumAmt(sumAmt);
				} else {
					sumAmt += (item.getAmount() - item.getCharge());
					TransInfoByCustomerVO tmpVO = new TransInfoByCustomerVO();
					tmpVO.setAcctNo(item.getAcctNo());
					tmpVO.setSumAmt(sumAmt);
					map.put(item.getAcctNo(), tmpVO);
				}
				
				/** 년도 범위내, 년도를 key로 삼는 Map에 각 년도별 최대 거래값과 거래 고객번호 갱신 */
				int tgtYear = Integer.parseInt(simpleDateFormat.format(item.getTransDate()));
				if (sumAmtMap.get(tgtYear).getMaxSumAmt() < sumAmt) {
					sumAmtMap.get(tgtYear).setMaxSumAmt(sumAmt);
					sumAmtMap.get(tgtYear).setAcctNo(item.getAcctNo());
				}
				
				map.get(item.getAcctNo()).setYear(tgtYear);
			}
		}

		/** 년도 범위내, 년도별 최대 거래 고객의 정보 조합 */
		GregorianCalendar gcal = new GregorianCalendar();
		int currentYear = gcal.get(Calendar.YEAR);
		for (int key : sumAmtMap.keySet()) {
			if (currentYear >= key && sumAmtMap.get(key) != null) {
				TransInfoByCustomerVO maxCustomer = new TransInfoByCustomerVO(); 
				maxCustomer.setYear(key);
				maxCustomer.setName(acctDao.getAcctListMap().get(sumAmtMap.get(key).getAcctNo()).getAcctName());
				maxCustomer.setAcctNo(sumAmtMap.get(key).getAcctNo());
				maxCustomer.setSumAmt(sumAmtMap.get(key).getMaxSumAmt());
				result.add(maxCustomer);
			}			
		}
		
		return result;
	}

	@Override
	public List<TransInfoByCustomerVO> getNoTransCustomerListByYear(TransVO transVO) {
		/** return 결과 값을 담기 위한 Object */
		List<TransInfoByCustomerVO> result = new ArrayList<TransInfoByCustomerVO>();
		
		/** Date 형태로 검색 날짜 형변환 */
		Date fromTgtDate = new Date();
		Date toTgtDate = new Date();
		try {
			fromTgtDate = new SimpleDateFormat("yyyy").parse(transVO.getFromYear()+"");
			toTgtDate = new SimpleDateFormat("yyyy").parse((transVO.getToYear()+1)+"");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/** 년도 범위내, 년도별 거래가 없는 고객 acctNo 추출 */
		Map<Integer, Map<String, AcctVO>> map = new HashMap<Integer, Map<String, AcctVO>>();
		Map<String, AcctVO> actListMap = acctDao.getAcctListMap();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
		for (TransVO item : transDao.getTransList()) {
			/** 취소되지 않은 거래들 중, 검색 조건으로 주어진 년도 범위내 데이터만 검색 */
			if ("Y".equals(item.getIsCancel()) && item.getTransDate().after(fromTgtDate)
											   && item.getTransDate().before(toTgtDate)) {
				int tgtYear = Integer.parseInt(simpleDateFormat.format(item.getTransDate()));
				if (map.get(tgtYear) != null) {
					map.get(tgtYear).remove(item.getAcctNo());
				} else {					
					Gson gson = new Gson();
					String jsonString = gson.toJson(actListMap);
					Type type = new TypeToken<HashMap<String, AcctVO>>(){}.getType();
					
					HashMap<String, AcctVO> clonedMap = gson.fromJson(jsonString, type);	// acctListMap 초기 세팅 
					clonedMap.remove(item.getAcctNo());
					map.put(tgtYear, clonedMap);
				}
			}
		}
		
		/** 년도 범위내, 년도별 최대 거래 고객의 정보 조합 */
		GregorianCalendar gcal = new GregorianCalendar();
		int currentYear = gcal.get(Calendar.YEAR);
		for (int key : map.keySet()) {
			if (currentYear >= key && map.get(key) != null) {
				Collection<AcctVO> tmpList = map.get(key).values();
				for (AcctVO acctVO : tmpList) {
					TransInfoByCustomerVO maxCustomer = new TransInfoByCustomerVO(); 
					maxCustomer.setYear(key);
					maxCustomer.setName(acctVO.getAcctName());
					maxCustomer.setAcctNo(acctVO.getAcctNo());
					result.add(maxCustomer);
				}
			}
		}
		
		return result;
	}
}