package com.javareactiveprogramming.reactive.handler;

import com.javareactiveprogramming.reactive.dao.CustomerDao;
import com.javareactiveprogramming.reactive.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {
    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {

        Flux<Customer> customerFlux = customerDao.getCustomerList();
        return ServerResponse.ok().body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> loadCustomerById(ServerRequest request) {

        int id = Integer.parseInt(request.pathVariable("input"));

//return customerDao.getCustomerList().filter(c -> c.getId() == id).take(1).single();
        Mono<Customer> customerMono = customerDao.getCustomerList().filter(c -> c.getId() == id).next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customer = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customer.map(dto -> dto.getId() + ": "+dto.getName());
        return ServerResponse.ok().body(saveResponse, String.class);
    }

}
