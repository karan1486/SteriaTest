package com.swiggyapp.swiggyartefact.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "order_details")
public class Order {
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Long id, String orderStatus, Date orderTime) {
		super();
		Id = id;
		this.orderStatus = orderStatus;
		this.orderTime = orderTime;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customerDetails;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "rep_id", nullable = true)
	private SwiggyRepresentative repDetails;
	
	@Column(name = "status")
	private String orderStatus;
	
	@Column(name = "order_time")
	private Date orderTime;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Customer getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(Customer customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public SwiggyRepresentative getRepDetails() {
		return repDetails;
	}

	public void setRepDetails(SwiggyRepresentative repDetails) {
		this.repDetails = repDetails;
	}



}
