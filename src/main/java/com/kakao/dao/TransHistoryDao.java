package com.kakao.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.kakao.model.TransHistoryVO;

@Component
public class TransHistoryDao {

	public List<TransHistoryVO> getTransHistoryList() {
		List<TransHistoryVO> transHistoryList = new ArrayList<TransHistoryVO>();
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
	           
	            TransHistoryVO p = new TransHistoryVO(transDate, acctNo, transNo, amount, charge, isCancel);
	            transHistoryList.add(p);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		
		return transHistoryList;
	}
}
