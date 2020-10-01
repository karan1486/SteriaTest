package com.swiggyapp.swiggyartefact.model;

import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_details")
public class Customer {
	
	public Customer() {
		super();
	}

	public Customer(Long customer_id, String customerName) {
		super();
		this.customer_id = customer_id;
		this.customerName = customerName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long customer_id;
	
	@Column(name = "customer_name")
	@JsonIgnore
	private String customerName;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "area_id")
	@JsonIgnore
	private AreaDetails customerArea;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerDetails")
	@JsonIgnore
	private Set<Order> orderDetails;

	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public AreaDetails getCustomerArea() {
		return customerArea;
	}

	public void setCustomerArea(AreaDetails customerArea) {
		this.customerArea = customerArea;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public Set<Order> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<Order> orderDetails) {
		this.orderDetails = orderDetails;
	}



}
