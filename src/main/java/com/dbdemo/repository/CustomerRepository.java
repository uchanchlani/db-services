package com.dbdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbdemo.model.Customer;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
