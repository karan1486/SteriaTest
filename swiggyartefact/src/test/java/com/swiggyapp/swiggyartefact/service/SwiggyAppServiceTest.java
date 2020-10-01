package com.swiggyapp.swiggyartefact.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.swiggyapp.swiggyartefact.repository.OrderRepository;
import com.swiggyapp.swiggyartefact.repository.SwiggyRepresentativeRepository;
import com.swiggyapp.swiggyartefact.service.SwiggyAppService;

import junit.framework.Assert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SwiggyartefactApplication.class)
public class SwiggyAppServiceTest {

	private MockMvc mockMvc;

	private MockRestServiceServer mockServer;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	private SwiggyAppService swiggyAppService;

	@MockBean
	private OrderRepository orderRepo;

	@MockBean
	private SwiggyRepresentativeRepository swiggyRep;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testOrderFood() throws Exception {

		OrderRepository orderRepoMock = mock(OrderRepository.class);

		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomer_id(1L);
		mockOrder.setCustomerDetails(mockCustomer);
		Mockito.when(orderRepo.save(Mockito.anyObject())).thenReturn(mockOrder);

		String inputJson = mapToJson(mockOrder);
		Assert.assertEquals(swiggyAppService.orderFood(mockOrder), mockOrder);
	}

	@Test
	public void testAssignRepresentative() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		SwiggyRepresentative mockRep = new SwiggyRepresentative(1L, "mockRep", "assigned");
		mockOrder.setRepDetails(mockRep);
		Mockito.when(orderRepo.findById(Mockito.anyObject())).thenReturn(Optional.of(mockOrder));
		List<SwiggyRepresentative> swiggyReps = new ArrayList<SwiggyRepresentative>();
		swiggyReps.add(mockRep);
		Mockito.when(swiggyRep.findActiveSwiggyReps(Mockito.anyObject())).thenReturn(swiggyReps);

		Mockito.when(swiggyRep.save(Mockito.anyObject())).thenReturn(mockRep);
		Assert.assertEquals(swiggyAppService.assignRepresentative(1L, 1L), mockRep);

	}

	@Test
	public void testShowOpenOrders() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		SwiggyRepresentative mockRep = new SwiggyRepresentative(1L, "mockRep", "assigned");
		mockOrder.setRepDetails(mockRep);
		List<Order> mockOrderList = new ArrayList<Order>();
		mockOrderList.add(mockOrder);

		Mockito.when(orderRepo.findOpenOrder(Mockito.anyObject())).thenReturn(mockOrderList);
		Assert.assertEquals(swiggyAppService.showOpenOrders(1L), mockOrderList);

	}

	@Test
	public void testChangeStatus() throws Exception {
		Order mockOrder = new Order();
		mockOrder.setOrderStatus("new");
		mockOrder.setOrderTime(new Date());
		SwiggyRepresentative mockRep = new SwiggyRepresentative(1L, "mockRep", "assigned");
		mockOrder.setRepDetails(mockRep);
		List<Order> mockOrderList = new ArrayList<Order>();
		mockOrderList.add(mockOrder);
		Mockito.when(orderRepo.findById(Mockito.anyObject())).thenReturn(Optional.of(mockOrder));
		Mockito.when(orderRepo.save(Mockito.anyObject())).thenReturn(mockOrder);

		Assert.assertEquals(swiggyAppService.changeStatus(1L,"pending"), "success");

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
