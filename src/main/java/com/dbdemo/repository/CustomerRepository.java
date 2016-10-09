package com.dbdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdemo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
