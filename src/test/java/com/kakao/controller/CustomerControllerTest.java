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
public class CustomerControllerTest {
	
	@Autowired
	WebApplicationContext webApplicationContext;

	protected static MockMvc mvc;
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void t01_getLargestSumAmtCustomerByYear_성공() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/customers?func=getLargestSumAmtCustomerByYear")).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void t02_getNoTransCustomerListByYear_성공() throws Exception {		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/customers?func=getNoTransCustomerListByYear")).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
