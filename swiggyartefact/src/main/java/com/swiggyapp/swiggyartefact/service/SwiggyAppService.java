package com.swiggyapp.swiggyartefact.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.swiggyapp.swiggyartefact.controller.SwiggyAppController;
import com.swiggyapp.swiggyartefact.model.AreaDetails;
import com.swiggyapp.swiggyartefact.model.Order;
import com.swiggyapp.swiggyartefact.model.SwiggyRepresentative;
import com.swiggyapp.swiggyartefact.repository.OrderRepository;
import com.swiggyapp.swiggyartefact.repository.SwiggyRepresentativeRepository;

/**
 * @author prabhakaranm
 *
 */
@Service
public class SwiggyAppService {
	
    Logger logger = LoggerFactory.getLogger(SwiggyAppService.class);


	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private SwiggyRepresentativeRepository swiggyRep;



	/**
	 * 
	 * This service is used to order food
	 * @param order
	 * @return
	 */
	public Order orderFood(Order order) {
		order.setOrderStatus("open");
		order = orderRepo.save(order);
		return order;
	}

	/**
	 * 
	 * This service is used to assign representative
	 * @param id
	 * @param orderId
	 * @return
	 */
	public SwiggyRepresentative assignRepresentative(Long id, Long orderId) {
		// TODO Auto-generated method stub
		AreaDetails areaDetail = new AreaDetails();
		areaDetail.setId(id);
		Order openOrder = orderRepo.findById(orderId).get();
		openOrder.setOrderStatus("InProgress");
		List<SwiggyRepresentative> swiggyReps = swiggyRep.findActiveSwiggyReps(areaDetail);
		SwiggyRepresentative openSwiggyRep = swiggyReps.get(0);
		openSwiggyRep.setRepStatus("assigned");
		Set<Order> openOrderSet = new HashSet<Order>();
		openOrderSet.add(openOrder);
		openSwiggyRep.setOrderDetails(openOrderSet);
		swiggyRep.save(openSwiggyRep);
		openOrder.setRepDetails(openSwiggyRep);
		orderRepo.save(openOrder);
		return openSwiggyRep;

	}

	/**
	 * 
	 * This service is used to change status
	 * @param id
	 * @param status
	 * @return
	 */
	public String changeStatus(Long id, String status) {
		// TODO Auto-generated method stub
		Order orderDetails = orderRepo.findById(id).get();
		orderDetails.setOrderStatus(status);
		orderDetails = orderRepo.save(orderDetails);
		Long repId = orderDetails.getRepDetails().getId();
		if ("cancelled".equals(status) || "closed".equals(status)) {
			Set<Order> openOrderSet = new HashSet<Order>();
			openOrderSet.add(orderDetails);
			SwiggyRepresentative swiggyRepresentative = swiggyRep.findById(repId).get();
			swiggyRepresentative.setRepStatus("unassigned");
			swiggyRep.save(swiggyRepresentative);
		}
		
		return "success";
	}

	/**
	 * This service is used to show open orders
	 * @param id
	 * @return
	 */
	public List<Order> showOpenOrders(Long id) {
		
		AreaDetails areaDetail=new AreaDetails();
		areaDetail.setId(id);
		List<Order> orderDetails=orderRepo.findOpenOrder(areaDetail);
		orderDetails.stream().forEach(order->{
			order.setCustomerDetails(null);
			order.setRepDetails(null);
		});
		return orderDetails;
		// TODO Auto-generated method stub
	}

}
