package com.dbdemo.db_services;

import com.dbdemo.model.Customer;
import com.dbdemo.repository.CustomerEsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utkarshc on 11/10/16.
 */
@RestController
@RequestMapping("api/v3")
public class CustomerEsController {
    CustomerEsRepository repository = new CustomerEsRepository();

    @RequestMapping(value = "customers", method = RequestMethod.GET)
    public List<Customer> list() {
        System.out.println("From CustomerController");
        try {
            return repository.displayAll();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "customers", method = RequestMethod.POST)
    public Customer create(@RequestBody Customer customer) {
        try {
            repository.createCustomer(customer);
            return customer;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "customers", method = RequestMethod.PUT)
    public Customer update(@RequestBody Customer customer) {
        try {
            repository.updateCustomer(customer);
            return customer;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "customers/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable(value="id") String id) {
        return repository.deleteCustomer(id);
    }
}
