package com.movile.resource;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movile.entity.OrderCustomer;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerOrderResource {
	
	@JsonProperty("customer_id")
	private Long id;
	@JsonProperty("customer_name")
	private String name;
	@JsonProperty("customer_date")
	private LocalDate registrationDate;
	@JsonProperty("customer_orders")
	private List<OrderCustomer> orders;

}
