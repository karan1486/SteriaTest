package com.swiggyapp.swiggyartefact.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiggyapp.swiggyartefact.exceptionhandler.OrderException;
import com.swiggyapp.swiggyartefact.model.AreaDetails;
import com.swiggyapp.swiggyartefact.model.Order;
import com.swiggyapp.swiggyartefact.model.SwiggyRepresentative;
import com.swiggyapp.swiggyartefact.repository.OrderRepository;
import com.swiggyapp.swiggyartefact.repository.SwiggyRepresentativeRepository;
import com.swiggyapp.swiggyartefact.service.SwiggyAppService;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for Swiggy Application
 * 
 * @author prabhakaranm
 *
 */
@RestController
@RequestMapping(path = "/swiggyOrder/")
public class SwiggyAppController {
	
    Logger logger = LoggerFactory.getLogger(SwiggyAppController.class);


	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private SwiggyRepresentativeRepository swiggyRep;

	@Autowired
	private SwiggyAppService swiggyAppService;

	/**
	 * This methd is used to create a order
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/orderFood")
	@ApiOperation(value = "Order a new Food", response = Order.class)
	public Order orderFood(@RequestBody Order order) {
		logger.info("Inside Order Food Controller");
		order = swiggyAppService.orderFood(order);
		logger.info("Exit Order Food Controller");
		return order;

	}

	/**
	 * This method is used to assign a representative
	 * 
	 * @param id
	 * @param orderId
	 * @return
	 */
	@PostMapping(path = "/assignRepresentative/{areaId}/{orderId}")
	@ApiOperation(value = "Assign a Food Representative", response = SwiggyRepresentative.class)
	public SwiggyRepresentative assignRepresentative(@PathVariable("areaId") Long id,
			@PathVariable("orderId") Long orderId) {
		// orderRepo.save(order);
		logger.info("Inside Assign Food Rep Controller");

		SwiggyRepresentative swiggyRep = swiggyAppService.assignRepresentative(id, orderId);
		logger.info("Exit Assign Food Rep Controller");

		return swiggyRep;

	}

	/**
	 * 
	 * This method is used to change the order status
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@PostMapping(path = "/changeOrderStatus/{id}/{status}")
	@ApiOperation(value = "Change the status of an Order", response = String.class)
	public String changeStatus(@PathVariable("id") Long id, @PathVariable("status") String status) {
		// orderRepo.save(order);
		logger.info("Inside Change Status Controller");

		if (!(status.equalsIgnoreCase("new") || status.equalsIgnoreCase("cancelled") || status.equalsIgnoreCase("closed"))) {
			return "failure";
		}
		String response = swiggyAppService.changeStatus(id, status);
		logger.info("Exit Change Status Controller");
  
		return response;
	}

	/**
	 * 
	 * This method is used to show open orders
	 * 
	 * @param id
	 * @return
	 * @throws OrderException 
	 */
	@PostMapping(path = "/showOpenOrders/{areaId}")
	@ApiOperation(value = "Show Open orders in an area", response = List.class)
	public List<Order> showOpenOrders(@PathVariable("areaId") Long id) throws OrderException {
		logger.info("Inside Show Open orders Controller");

		List<Order> orderDetails = swiggyAppService.showOpenOrders(id);
		if(orderDetails.isEmpty()) {
			throw new OrderException();
		}
		logger.info("Exit Show Open orders Controller");

		return orderDetails;
	}

}
