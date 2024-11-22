package com.javareactiveprogramming;

import com.javareactiveprogramming.mongowithreactive.controller.ProductController;
import com.javareactiveprogramming.mongowithreactive.dto.ProductDto;
import com.javareactiveprogramming.mongowithreactive.entity.ProductEntity;
import com.javareactiveprogramming.mongowithreactive.repository.ProductRepository;
import com.javareactiveprogramming.mongowithreactive.services.ProductService;
import com.javareactiveprogramming.mongowithreactive.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductController.class)
@Import(ProductController.class)
class ReactiveProgrammingApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private ProductRepository productRepository;
    @Test
    public void getProducts() {
        Flux<ProductDto> productEntityFlux = Flux.just(
                new ProductDto("11", "mobile", 2, 2000),
                new ProductDto("15", "sarker", 66, 1001));
        when(productService.getProducts()).thenReturn(productEntityFlux);
//        when(productRepository.getAllProduct()).thenReturn(productEntityFlux);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ProductDto.class)
                .getResponseBody().log();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDto("11", "mobile", 2, 2000))
                .expectNext(new ProductDto("15", "sarker", 66, 1001))
                .verifyComplete();
    }
    @Test
    public void getProduct() {
        Mono<ProductDto> productDtoMono = Mono.just(
                new ProductDto("11", "mobile", 2, 20001)
                );
        when(productService.getProduct(any())).thenReturn(productDtoMono);
        Flux<ProductDto> responseBody = webTestClient.get().uri("/products/12")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ProductDto.class)
                .getResponseBody().log();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(e -> e.getName().equals("mobile"))
                .verifyComplete();
    }

    @Test
    public void saveProduct() {
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("11", "mobil", 2, 2000));
        when(productService.saveProduct(productDtoMono)).thenReturn(productDtoMono);

        webTestClient.post().uri("/products")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

/*
    @Test
    void testMono() {
        Mono<?> monoString = Mono.just("Helloe World")
                .then(Mono.error(new RuntimeException("Exception !!!!!!!")))
                .log();

        monoString.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux() {
        Flux<String> flux = Flux.just("Spring ", "Spring Boot", "Hibernate")
                .concatWithValues("AWS")
                .log()
                .concatWith(Flux.error(new RuntimeException("Exception occured in flux")))
                .concatWithValues("AWS");

        flux.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));
    }*/

}
