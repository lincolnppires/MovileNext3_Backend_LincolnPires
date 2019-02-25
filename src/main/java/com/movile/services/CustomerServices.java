package com.movile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.movile.entity.Customer;
import com.movile.repository.CustomerRepository;
import com.movile.resource.CustomerDataResource;
import com.movile.resource.CustomerOrderDataResource;
import com.movile.resource.CustomerOrderResource;
import com.movile.resource.CustomerResource;
import com.movile.resource.PageResource;

@Service
public class CustomerServices {

	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerServices(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
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

}
