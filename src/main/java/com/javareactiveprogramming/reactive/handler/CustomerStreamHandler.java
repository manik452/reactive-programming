package com.javareactiveprogramming.reactive.handler;

import com.javareactiveprogramming.reactive.dao.CustomerDao;
import com.javareactiveprogramming.reactive.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {
    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomersStream(ServerRequest request){

        Flux<Customer>  customerFlux = customerDao.getCustomerStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerFlux,Customer.class);
    }

}
