package com.movile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movile.resource.CustomerDataResource;
import com.movile.resource.PageResource;
import com.movile.services.CustomerServices;

@RestController
@RequestMapping(value = "/movile", produces = "application/hal+json")
public class CustomerController {

	private CustomerServices customerServices;
	
	@Autowired
	public CustomerController(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}
	
	@GetMapping("customers")
	public ResponseEntity<PageResource<CustomerDataResource>> findAll(
			@RequestParam (value="page", defaultValue="0") int page,
			@RequestParam (value="size", defaultValue="3") int size){
		
		final PageResource<CustomerDataResource> customerDataResourcePage = customerServices.findAll(page, size);
		
		if(customerDataResourcePage.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerDataResourcePage);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(customerDataResourcePage);		
	}

	
}
