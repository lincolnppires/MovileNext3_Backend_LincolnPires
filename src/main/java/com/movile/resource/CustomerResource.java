package com.movile.resource;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerResource {
	
	@JsonProperty("customer_id")
	private Long id;
	@JsonProperty("customer_name")
	private String name;
	@JsonProperty("customer_date")
	private LocalDate registrationDate;

	@JsonIgnore
	private int page;
	@JsonIgnore
	private int size;
	
	
	
	

}
