package com.kakao.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kakao.model.AcctVO;
import com.kakao.model.BranchVO;
import com.kakao.model.TransAllInfoVO;
import com.kakao.model.TransVO;

@Component
public class TransDao {
	
	@Autowired
	AcctDao acctDao;
	
	@Autowired
	BranchDao branchDao;
	
	/**
	 * 모든 거래 내역을 List 형태로 가져오기 
	 * @return List<TransVO>
	 */
	public List<TransVO> getTransList() {
		List<TransVO> transList = new ArrayList<TransVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_거래내역.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		sc.nextLine();

        while(sc.hasNextLine()){
        	try {
	            String line = sc.nextLine();
	            String[] details = line.split(",");
	           
	            String tmpTransDate = details[0];
	            SimpleDateFormat transFormat = new SimpleDateFormat("yyyymmdd");
	            Date transDate = transFormat.parse(tmpTransDate);
	            String acctNo = details[1];
	            String transNo = details[2];
	            int amount = Integer.parseInt(details[3]);
	            int charge = Integer.parseInt(details[4]);
	            String isCancel = details[5];
	           
	            TransVO p = new TransVO(transDate, acctNo, transNo, amount, charge, isCancel);
	            transList.add(p);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		
		return transList;
	}
	
	
	/**
	 * 모든 관리점의 모든 계좌 정보별 모든 거래 내역 가져오기 
	 * @return List<TransAllInfoVO>
	 */
	public List<TransAllInfoVO> getJoinedAllList() {
		
		Map<String, AcctVO> acctMap = acctDao.getAcctListMap();
		Map<String, BranchVO> brMap = branchDao.getBranchListMap();
		
		List<TransAllInfoVO> transList = new ArrayList<TransAllInfoVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_거래내역.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		sc.nextLine();

        while(sc.hasNextLine()){
        	try {
	            String line = sc.nextLine();
	            String[] details = line.split(",");
	           
	            String tmpTransDate = details[0];
	            SimpleDateFormat transFormat = new SimpleDateFormat("yyyymmdd");
	            Date transDate = transFormat.parse(tmpTransDate);
	            String acctNo = details[1];
	            String transNo = details[2];
	            int amount = Integer.parseInt(details[3]);
	            int charge = Integer.parseInt(details[4]);
	            String isCancel = details[5];
	            String acctName = acctMap.get(acctNo).getAcctName();
	            String brCode = acctMap.get(acctNo).getBrCode();
	            String brName = brMap.get(brCode).getBrName();
	           
	            TransAllInfoVO p = new TransAllInfoVO(transDate, acctNo, transNo, amount, charge, isCancel, acctName, brCode, brName);
	            transList.add(p);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		
		return transList;
	}
	
	
	/**
	 * acctNo List를 이용해 모든 거래 내역 가져오기 
	 * @param List<String> acctNoList
	 * @return List<TransVO>
	 */
	public List<TransVO> getTransListByAcctList(List<String> acctNoList) {
		List<TransVO> transList = new ArrayList<TransVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_거래내역.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		sc.nextLine();

        while(sc.hasNextLine()){
        	try {
	            String line = sc.nextLine();
	            String[] details = line.split(",");
	           
	            String tmpTransDate = details[0];
	            SimpleDateFormat transFormat = new SimpleDateFormat("yyyymmdd");
	            Date transDate = transFormat.parse(tmpTransDate);
	            String acctNo = details[1];
	            String transNo = details[2];
	            int amount = Integer.parseInt(details[3]);
	            int charge = Integer.parseInt(details[4]);
	            String isCancel = details[5];
	            
	            if (acctNoList.contains(acctNo)) {
	            	TransVO p = new TransVO(transDate, acctNo, transNo, amount, charge, isCancel);
	            	transList.add(p);	            	
	            }
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		
		return transList;
	}
}
