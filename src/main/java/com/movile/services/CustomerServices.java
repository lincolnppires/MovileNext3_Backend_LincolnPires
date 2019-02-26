package com.movile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.movile.entity.Customer;
import com.movile.entity.OrderCustomer;
import com.movile.entity.StatusOrder;
import com.movile.repository.CustomerRepository;
import com.movile.resource.CustomerDataResource;
import com.movile.resource.CustomerOrderDataResource;
import com.movile.resource.CustomerOrderResource;
import com.movile.resource.CustomerResource;
import com.movile.resource.OrderResource;
import com.movile.resource.PageResource;
import com.movile.statemachine.Events;
import com.movile.statemachine.States;

@Service
public class CustomerServices {

	private CustomerRepository customerRepository;
	private StateMachine<States, Events> stateMachine;
	
	@Autowired
	public CustomerServices(CustomerRepository customerRepository, StateMachine<States, Events> stateMachine) {
		this.customerRepository = customerRepository;
		this.stateMachine = stateMachine;
	}

	public PageResource<CustomerDataResource> findAll(int page, int size) {
		
		final PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		final Page<Customer> customersPage = customerRepository.findAll(pageRequest);
		
		List<CustomerDataResource> listCustomerResource = new ArrayList<CustomerDataResource>(); 
		
		for (Customer customer : customersPage) {
			final CustomerDataResource customerDataResource = new CustomerDataResource(new CustomerResource());
			
			customerDataResource.setCustomerId(customer.getId());
			customerDataResource.setName(customer.getName());
			customerDataResource.setRegistrationDate(customer.getRegistrationDate());
			customerDataResource.setPage(pageRequest.getPageNumber());
			customerDataResource.setSize(pageRequest.getPageSize());
			customerDataResource.addLinks();
			
			listCustomerResource.add(customerDataResource);
		}
		
		Page<CustomerDataResource> customerDataResourcePage = 
				new PageImpl<CustomerDataResource>(listCustomerResource, customersPage.getPageable(), customersPage.getTotalElements());
		
		return new PageResource<CustomerDataResource>(customerDataResourcePage, "page", "size");		
	}

	public CustomerOrderDataResource findById(Long id) {
		CustomerOrderDataResource customerOrderDataResource = new CustomerOrderDataResource(new CustomerOrderResource());
		Optional<Customer> customerData = customerRepository.findById(id);
		
		if(customerData.isPresent()) {
			customerOrderDataResource.setCustomerOrderId(customerData.get().getId());
			customerOrderDataResource.setName(customerData.get().getName());
			customerOrderDataResource.setRegistrationDate(customerData.get().getRegistrationDate());
			customerOrderDataResource.setOrders(customerData.get().getOrders());			
 		}
		return customerOrderDataResource;
	}

	public boolean save(@Valid OrderResource orderResource) {		
		Optional<Customer> customer = customerRepository.findById((Long)orderResource.getCustomer_id());
		
		OrderCustomer order = new OrderCustomer();
		order.setStatus(StatusOrder.OPEN);
		order.setValue(orderResource.getValue());
		order.setDescription(orderResource.getDescription());
		customer.get().getOrders().add(order);		
		
		customerRepository.save(customer.get());
		
		boolean sendEvent = stateMachine.sendEvent(Events.OPEN);
		return sendEvent;
	}
	

}
