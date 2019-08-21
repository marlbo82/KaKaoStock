package com.kakao.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.kakao.model.AcctVO;
import com.kakao.model.BranchVO;

@Component
public class AcctDao {

	/**
	 * 모든 계좌 정보를 List 형태로 가져오기 
	 * @return List<AcctVO>
	 */
	public List<AcctVO> getAcctList() {
		List<AcctVO> acctList = new ArrayList<AcctVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_계좌정보.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		sc.nextLine();

        while(sc.hasNextLine()){
        	String line = sc.nextLine();
			String[] details = line.split(",");
         
			String acctNo = details[0];
			String acctName = details[1];
			String brCode = details[2];
         
			AcctVO p = new AcctVO(acctNo, acctName, brCode);
			acctList.add(p);
        }
		
		return acctList;		
	}
	
	
	/**
	 * 모든 계좌 정보를 Map 형태로 가져오기
	 * @return Map<String, AcctVO> (key : acctNo)
	 */
	public Map<String, AcctVO> getAcctListMap() {
		Map<String, AcctVO> acctListMap = new HashMap<String, AcctVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_계좌정보.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		sc.nextLine();

        while(sc.hasNextLine()){
        	String line = sc.nextLine();
			String[] details = line.split(",");
         
			String acctNo = details[0];
			String acctName = details[1];
			String brCode = details[2];
         
			AcctVO p = new AcctVO(acctNo, acctName, brCode);
			acctListMap.put(p.getAcctNo(), p);
        }
		
		return acctListMap;
	}

	
	/**
	 * 일부 관리점 통합 이후, 특정 관리점의 모든 계좌 정보의 acctNo를 List<String> 형태로 가져오기
	 * @param BranchVO
	 * @return List<String>
	 */
	public List<String> getAcctNoListAfterIntgration(BranchVO branchVO) throws Exception {
		List<String> acctList = new ArrayList<String>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_계좌정보.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		sc.nextLine();

        while(sc.hasNextLine()){
        	String line = sc.nextLine();
			String[] details = line.split(",");
         
			String acctNo = details[0];
			String brCode = details[2];
			
			if ("판교점".equals(branchVO.getBrName())) {
				if ("A".equals(brCode) || "B".equals(brCode)) {
					acctList.add(acctNo);
				}
			} else if ("분당점".equals(branchVO.getBrName())) {
				throw new Exception("br code not found error"); 
			} else {
				if (brCode.equals(branchVO.getBrCode())) {
					acctList.add(acctNo);
				}				
			}
         
        }
		
		return acctList;
	}
}
