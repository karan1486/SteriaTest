package com.swiggyapp.swiggyartefact.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggyapp.swiggyartefact.SwiggyartefactApplication;
import com.swiggyapp.swiggyartefact.model.Customer;
import com.swiggyapp.swiggyartefact.model.Order;
import com.swiggyapp.swiggyartefact.model.SwiggyRepresentative;
import com.swiggyapp.swiggyartefact.service.SwiggyAppService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SwiggyartefactApplication.class)
public class SwiggyAppControllerTest {

	private MockMvc mockMvc;
	
    
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@MockBean
	private SwiggyAppService swiggyAppService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}
	
	
	@Test
	public void testChangeStatusInvalidStatus() throws Exception {
		String uri = "/swiggyOrder/changeOrderStatus/1/1";
		Mockito.when(
				swiggyAppService.changeStatus(Mockito.anyObject(),Mockito.anyObject())).thenReturn("success");
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		 String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals("failure", content);

	}
	
	@Test
	public void testChangeStatusIncorrectStatus() throws Exception {
		String uri = "/swiggyOrder/changeOrderStatus/1/IncorrectStatus";
		Mockito.when(
				swiggyAppService.changeStatus(Mockito.anyObject(),Mockito.anyObject())).thenReturn("success");
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		 String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals("failure", content);

	}

	@Test
	public void testOrderFoodSuccessResponse() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomer_id(1L);
		mockOrder.setCustomerDetails(mockCustomer);
		Mockito.when(
				swiggyAppService.orderFood(Mockito.anyObject())).thenReturn(mockOrder);
		String uri = "/swiggyOrder/orderFood";
		
		String inputJson = mapToJson(mockOrder);
       
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	
	@Test
	public void testOrderFoodFailureResponse() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomer_id(1L);
		mockOrder.setCustomerDetails(mockCustomer);
		Mockito.when(
				swiggyAppService.orderFood(Mockito.anyObject())).thenReturn(mockOrder);
		String uri = "/swiggyOrder";
		
		String inputJson = mapToJson(mockOrder);
       
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	

	@Test
	public void testAssignRepresentativeSuccess() throws Exception {
		SwiggyRepresentative mockRep= new SwiggyRepresentative(1L, "mockRep", "assigned");
		String uri = "/swiggyOrder/assignRepresentative/1/1";
		Mockito.when(
				swiggyAppService.assignRepresentative(Mockito.anyObject(),Mockito.anyObject())).thenReturn(mockRep);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void testAssignRepresentativeFailure() throws Exception {
		SwiggyRepresentative mockRep= new SwiggyRepresentative(1L, "mockRep", "assigned");
		String uri = "/swiggyOrder/assignRepresentative";
		Mockito.when(
				swiggyAppService.assignRepresentative(Mockito.anyObject(),Mockito.anyObject())).thenReturn(mockRep);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}



	@Test
	public void testShowOpenOrdersSuccess() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		
		List<Order> mockOrderList= new ArrayList<Order>();
		mockOrderList.add(mockOrder);
		
		String uri = "/swiggyOrder/showOpenOrders/1";
		
		Mockito.when(
				swiggyAppService.showOpenOrders(Mockito.anyObject())).thenReturn(mockOrderList);
		MvcResult mvcResult = mockMvc 
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	
	@Test
	public void testShowOpenOrdersFailure() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		
		List<Order> mockOrderList= new ArrayList<Order>();
		mockOrderList.add(mockOrder);
		
		String uri = "/swiggyOrder/showOpenOrders";
		
		Mockito.when(
				swiggyAppService.showOpenOrders(Mockito.anyObject())).thenReturn(mockOrderList);
		MvcResult mvcResult = mockMvc 
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}

	@Test
	public void testChangeStatusSuccess() throws Exception {
		String uri = "/swiggyOrder/changeOrderStatus/1/closed";
		Mockito.when(
				swiggyAppService.changeStatus(Mockito.anyObject(),Mockito.anyObject())).thenReturn("success");
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void testChangeStatusFailure() throws Exception {
		String uri = "/swiggyOrder/changeOrderStatus/1/closed";
		Mockito.when(
				swiggyAppService.changeStatus(Mockito.anyObject(),Mockito.anyObject())).thenReturn("success");
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	

	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	public <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
