package com.javareactiveprogramming.reactive.controller;

import com.javareactiveprogramming.reactive.model.Customer;
import com.javareactiveprogramming.reactive.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {


    private final CustomerServices customerServices;

    @Autowired
    public CustomerController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    @GetMapping("/getall")
    public @ResponseBody List<Customer> getAllCustomer() {
        List<Customer> customers= customerServices.loadAllCustomers();
        return customers;
    }

    @GetMapping(value = "/getallstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public @ResponseBody Flux<Customer> getAllCustomerStreamFlux() {
        return customerServices.loadAllCustomersStream();
    }
}
