package com.movile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movile.resource.CustomerDataResource;
import com.movile.resource.CustomerOrderDataResource;
import com.movile.resource.OrderResource;
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
	
	@GetMapping("customer/{id}")
	public ResponseEntity<Resource<CustomerOrderDataResource>> findById(@PathVariable Long id){
		
		final CustomerOrderDataResource customerOrderDataResource = customerServices.findById(id);
		final List<Link> links = customerOrderDataResource.retrieveLinks();
		
		Resource<CustomerOrderDataResource> resource = new Resource<CustomerOrderDataResource>(customerOrderDataResource, links);
						
		if((customerOrderDataResource.getCustomerOrderId() == null)) {
			customerOrderDataResource.setName("NOT FOUND");			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(resource);		
	}
	
	@PostMapping("customer/{id}/order")
	public ResponseEntity<OrderResource> addOrder(@Valid @RequestBody OrderResource orderResource, BindingResult result) {
		
		return null;
	}


	
}

	
	
	
	
	
	
	
	
	
	
	