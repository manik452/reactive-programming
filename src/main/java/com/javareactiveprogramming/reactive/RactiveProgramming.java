package com.javareactiveprogramming.reactive;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RactiveProgramming {
    public static void main(String[] args) throws IOException {

        List<String> stringList = Arrays.asList("ab", "cd", "ef");


//        ReactiveSources.userFlux().subscribe(e -> System.out.println(e));
       /* ReactiveSources.intNumbersFlux().subscribe(
                System.out::println,
                err -> System.out.println(err.getMessage()),
                () -> System.out.println("Complete Flux"));*/

        /*Flux<Integer> integerFlux =  ReactiveSources.intNumbersFlux();
        List<Integer> integers = integerFlux.toStream().toList();
        System.out.println(integers);*/


        /*ReactiveSources.intNumberMono().log()
                .subscribe(number -> System.out.println(number),
                        err -> System.out.println(err.getMessage()),
                        () -> System.out.println("Complete"));*/
//        Integer ab = ReactiveSources.intNumberMono().block();

//        System.out.println(ab);


//        ReactiveSources.intNumbersFlux().log().subscribe(new MySubscriber<>());


        Mono<?> monoString = Mono.just("Helloe World")
                .then(Mono.error(new RuntimeException("Exception !!!!!!!")))
                .log();

        monoString.subscribe(System.out::println);

        System.out.println("Press a key to end");
        System.in.read();
    }
}


class MySubscriber<T> extends BaseSubscriber<T>{
    public void hookOnSubscribe(Subscription subscription){
        System.out.println("Subscribe happen");
        request(1);
    }

    public void hookOnNext(T value){
        System.out.println(value+" Received");
        request(1);
    }
}