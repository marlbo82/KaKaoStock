package com.kakao.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kakao.KakaoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KakaoApplication.class)
@WebAppConfiguration
public class BranchControllerTest {
	
	@Autowired
	WebApplicationContext webApplicationContext;

	protected static MockMvc mvc;
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void t01_getSumAmtBranchList_성공() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/branches?func=getSumAmtBranchListByYear")).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void t02_getSumAmtBranch_성공() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/branches?func=getSumAmtBranch")
																.content("{\"brName\" : \"판교점\"}")
																.contentType("application/json")
										 ).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void t03_getSumAmtBranch_실패_brName_분당점() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/branches?func=getSumAmtBranch")
																.content("{\"brName\" : \"분당점\"}")
																.contentType("application/json")
										 ).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void t04_getSumAmtBranch_실패_brName_없는_관리점() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/branches?func=getSumAmtBranch")
																.content("{\"brName\" : \"요하네스버그점\"}")
																.contentType("application/json")
										 ).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void t05_getSumAmtBranch_실패_requestParam_brName_없음() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/branches?func=getSumAmtBranch")
																.content("{\"brName\" : \"\"}")
																.contentType("application/json")
										 ).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
}
