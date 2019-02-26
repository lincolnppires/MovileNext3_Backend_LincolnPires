package com.movile.resource;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderResource {
	
	public OrderResource() {
	}
	
	public OrderResource(Long customer_id, String description, BigDecimal value) {
		this.customer_id = customer_id;
		this.description = description;
		this.value = value;
	}
	
	private Long customer_id;
	private String description;
	private BigDecimal value;

}
