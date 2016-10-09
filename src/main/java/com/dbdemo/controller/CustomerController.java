package com.dbdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.dbdemo.model.Customer;
import com.dbdemo.repository.CustomerRepository;

@RestController
@RequestMapping("api/v1")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public List<Customer> list() {
		System.out.println("From CustomerController");
		return customerRepository.findAll();
	}
	
	@RequestMapping(value = "customers", method = RequestMethod.POST)
	public Customer create(@RequestBody Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}
	
	
}
