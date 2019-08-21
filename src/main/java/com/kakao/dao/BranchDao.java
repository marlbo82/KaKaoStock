package com.kakao.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.kakao.model.BranchVO;
import com.kakao.model.TransInfoByBranchVO;

@Component
public class BranchDao {

	/**
	 * 특정 관리점 정보 가져오기
	 * @param String branchName
	 * @return BranchVO
	 */
	public BranchVO getBranch(String branchName) {
		BranchVO result = null;
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_관리점정보.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		sc.nextLine();
		
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			String[] details = line.split(",");
			
			String brCode = details[0];
			String brName = details[1];
			
			if (branchName.equals(brName)) {
				BranchVO p = new BranchVO(brCode, brName);
				result = p;
			}
		}
		
		return result;		
	}
	
	
	/**
	 * 모든 관리점 정보를 Map 형태로 가져오기
	 * @return Map<String, BranchVO> (key : brCode)
	 */
	public Map<String, BranchVO> getBranchListMap() {
		Map<String, BranchVO> brListMap = new HashMap<String, BranchVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_관리점정보.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		sc.nextLine();
		
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			String[] details = line.split(",");
			
			String brCode = details[0];
			String brName = details[1];
			
			BranchVO p = new BranchVO(brCode, brName);
			brListMap.put(p.getBrCode(), p);
		}
		
		return brListMap;		
	}
	
	
	/**
	 * 모든 관리점 정보를 Map 형태로 가져오기
	 * @return Map<String, BranchVO> (key : brCode)
	 */
	public Map<String, TransInfoByBranchVO> getTransInfoByBranchMap() {
		Map<String, TransInfoByBranchVO> brListMap = new HashMap<String, TransInfoByBranchVO>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("./src/데이터_관리점정보.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		sc.nextLine();
		
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			String[] details = line.split(",");
			
			String brCode = details[0];
			String brName = details[1];
			
			TransInfoByBranchVO p = new TransInfoByBranchVO();
			p.setBrName(brName);
			p.setBrCode(brCode);
			p.setSumAmt(0);
			
			brListMap.put(p.getBrCode(), p);
		}
		
		return brListMap;		
	}
}
