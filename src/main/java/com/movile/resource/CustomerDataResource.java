package com.movile.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movile.controller.CustomerController;


public class CustomerDataResource extends ResourceSupport {
	
	private CustomerResource customerResource;

	@Autowired
	public CustomerDataResource(CustomerResource customerResource) {
		this.customerResource = customerResource;
	}
	
	public void addLinks() {			
		add(linkTo(methodOn(CustomerController.class).findAll(customerResource.getPage(), customerResource.getSize()))
				.withSelfRel());
		add(linkTo(methodOn(CustomerController.class).findById(customerResource.getId())).withRel("orders"));
	}
	
	public CustomerResource getCustomerResource() {
		return customerResource;
	}
	
	@JsonIgnore
	public Long getCustomer_id() {
		return customerResource.getId();
	}

	@JsonIgnore
	public String getName() {
		return customerResource.getName();
	}

	@JsonIgnore
	public LocalDate getRegistrationDate() {
		return customerResource.getRegistrationDate();
	}

	@JsonIgnore
	public int getPage() {
		return customerResource.getPage();
	}
	
	@JsonIgnore
	public int getSize() {
		return customerResource.getSize();
	}

	public void setCustomerId(Long id) {
		customerResource.setId(id);
	}

	public void setName(String name) {
		customerResource.setName(name);
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		customerResource.setRegistrationDate(registrationDate);
	}

	public void setPage(int page) {
		customerResource.setPage(page);
	}

	public void setSize(int size) {
		customerResource.setSize(size);
	}
	
}
