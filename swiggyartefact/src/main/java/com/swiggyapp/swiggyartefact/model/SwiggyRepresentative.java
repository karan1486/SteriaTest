package com.swiggyapp.swiggyartefact.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

enum RepresentativeStatus {
	assigned, free
}

@Entity
@Table(name = "swiggyrep_details")
public class SwiggyRepresentative {



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rep_id;

	@Column(name = "rep_name")
	private String representativeName;

	@Column(name = "rep_status")
	private String repStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "area_id")
	@JsonIgnore
	private AreaDetails customerArea;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "repDetails")

	@JsonIgnore
	private Set<Order> orderDetails;
	
	
	public SwiggyRepresentative(Long rep_id, String representativeName, String repStatus) {
		super();
		this.rep_id = rep_id;
		this.representativeName = representativeName;
		this.repStatus = repStatus;
	}
	
	

	public SwiggyRepresentative() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return rep_id;
	}

	public void setId(Long id) {
		rep_id = id;
	}

	public String getRepresentativeName() {
		return representativeName;
	}

	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}

	public String getRepStatus() {
		return repStatus;
	}

	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}

	public AreaDetails getCustomerArea() {
		return customerArea;
	}

	public void setCustomerArea(AreaDetails customerArea) {
		this.customerArea = customerArea;
	}

	public Long getRep_id() {
		return rep_id;
	}

	public void setRep_id(Long rep_id) {
		this.rep_id = rep_id;
	}

	public Set<Order> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<Order> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
