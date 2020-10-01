package com.swiggyapp.swiggyartefact.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area_details")
public class AreaDetails {
	
	public AreaDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AreaDetails(Long area_id, String areaName) {
		super();
		this.area_id = area_id;
		this.areaName = areaName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long area_id;
	
	@Column(name = "area_name")
	private String areaName;
	
	

	public Long getId() {
		return area_id;
	}

	public void setId(Long id) {
		this.area_id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
