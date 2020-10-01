package com.swiggyapp.swiggyartefact.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swiggyapp.swiggyartefact.model.AreaDetails;
import com.swiggyapp.swiggyartefact.model.Order;
import com.swiggyapp.swiggyartefact.model.SwiggyRepresentative;

public interface SwiggyRepresentativeRepository  extends JpaRepository<SwiggyRepresentative, Long>{

	@Query("Select swiggyRep FROM SwiggyRepresentative swiggyRep where swiggyRep.customerArea=:areaDetail and repStatus='unassigned'")
	public List<SwiggyRepresentative> findActiveSwiggyReps(AreaDetails areaDetail);
	
	@Query("Select swiggyRep FROM SwiggyRepresentative swiggyRep where swiggyRep.orderDetails IN :orderDetails")
	public List<SwiggyRepresentative> findSwiggyRepsOnOrder(Set<Order> orderDetails);
}
