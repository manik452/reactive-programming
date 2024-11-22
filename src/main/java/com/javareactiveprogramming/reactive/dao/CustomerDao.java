package com.javareactiveprogramming.reactive.dao;

import com.javareactiveprogramming.reactive.model.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    private static void threadSleep(int i){
        try {
            System.out.println("thread sleep value is "+ i);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Customer> getCustomer() {
        return IntStream.rangeClosed(1, 50)
                .peek(CustomerDao::threadSleep)
                .peek(i -> System.out.println("Processing count "+ i))
                .mapToObj(i -> new Customer(i, "customer" + i))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomerStream() {
        return Flux.range(1, 50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Processing count "+ i))
                .map(i -> new Customer(i, "customer" + i));
              /*  .peek(CustomerDao::threadSleep)
                .peek(i -> System.out.println("Processing count "+ i))

                .collect(Collectors.toList());*/
    }

    public Flux<Customer> getCustomerList() {
        return Flux.range(1, 50)
                .doOnNext(i -> System.out.println("Processing count "+ i))
                .map(i -> new Customer(i, "customer" + i));

    }


}
