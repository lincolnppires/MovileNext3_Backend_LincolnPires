package com.movile.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.movile.entity.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	Page<Customer> findAll(Pageable pageable);
}
