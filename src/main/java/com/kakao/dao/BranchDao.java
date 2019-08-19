package com.kakao.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.kakao.model.BranchVO;

@Component
public class BranchDao {

	public List<BranchVO> getBranchList() {
		List<BranchVO> brList = new ArrayList<BranchVO>();
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
         
			String brName = details[0];
			String brCode = details[1];
         
			BranchVO p = new BranchVO(brName, brCode);
			brList.add(p);
        }
		
		return brList;		
	}
}
