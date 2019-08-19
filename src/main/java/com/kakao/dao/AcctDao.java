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

@Component
public class AcctDao {

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
}
