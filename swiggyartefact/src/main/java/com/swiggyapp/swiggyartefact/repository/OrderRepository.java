package com.swiggyapp.swiggyartefact.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swiggyapp.swiggyartefact.model.AreaDetails;
import com.swiggyapp.swiggyartefact.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
@Query("Select order FROM Order order where order.customerDetails.customerArea=:areaDetail and orderStatus='open'")
	public List<Order> findOpenOrder(AreaDetails areaDetail);

}
