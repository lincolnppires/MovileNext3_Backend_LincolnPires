package com.movile.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movile.controller.CustomerController;
import com.movile.entity.OrderCustomer;

public class CustomerOrderDataResource {
	
	private CustomerOrderResource customerOrderResource;
	
	@Autowired
	public CustomerOrderDataResource(CustomerOrderResource customerOrderResource) {
		this.customerOrderResource = customerOrderResource;
	}
	
	public List<Link> retrieveLinks() {
		Link customerOrderLink = linkTo(methodOn(CustomerController.class).findById(customerOrderResource.getId())).withSelfRel();
		return Arrays.asList(customerOrderLink);
	}

	public CustomerOrderResource getCustomerOrderResource() {
		return customerOrderResource;
	}

	@JsonIgnore
	public Long getCustomerOrderId() {
		return customerOrderResource.getId();
	}

	@JsonIgnore
	public String getName() {
		return customerOrderResource.getName();
	}

	@JsonIgnore
	public List<OrderCustomer> getOrders() {
		return customerOrderResource.getOrders();
	}

	@JsonIgnore
	public LocalDate getRegistrationDate() {
		return customerOrderResource.getRegistrationDate();
	}

	public void setCustomerOrderId(Long id) {
		customerOrderResource.setId(id);
	}

	public void setName(String name) {
		customerOrderResource.setName(name);
	}

	public void setOrders(List<OrderCustomer> orders) {
		customerOrderResource.setOrders(orders);
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		customerOrderResource.setRegistrationDate(registrationDate);
	}
	
}
