package com.javareactiveprogramming.mongowithreactive.repository;

import com.javareactiveprogramming.mongowithreactive.dto.ProductDto;
import com.javareactiveprogramming.mongowithreactive.entity.ProductEntity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity,String> {

    @Query("From products")
    Flux<ProductEntity> getAllProduct();
    Flux<ProductDto> findByPriceBetween(Range<Double> closed);
}
