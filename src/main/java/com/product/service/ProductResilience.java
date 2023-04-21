package com.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.model.dto.ProductDTO;
import com.product.service.interfaces.FunctionServiceInterface;

// import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ProductResilience {

    private FunctionServiceInterface functionInterface;

    @Autowired
    public ProductResilience(FunctionServiceInterface functionInterface){
        this.functionInterface = functionInterface;
    }

    @CircuitBreaker(name="checkProductBreaker", fallbackMethod = "buildFallBack")
    @Retry(name = "retryservicebeta", fallbackMethod = "retryFallBack") 
    public ResponseEntity<String> checkProduct(ProductDTO product){
        return functionInterface.checkProduct(product);
    }

    public ResponseEntity<String> buildFallBack(ProductDTO product, Throwable t){
        System.err.println("CIRCUIT BREAKER: Falha no product " + product.getName());
        return ResponseEntity.ok("Fail: Circuit Breaker");
    }

    public ResponseEntity<String> checkProductBulkheadFallBack(ProductDTO product, Throwable t){
        System.err.println("BULKHEAD (GET) - Falha no product " + product.getName() +"\n\n");
        return ResponseEntity.ok("Fail: BULKHEAD");
    } 

    public ResponseEntity<String> retryFallBack(ProductDTO product, Throwable t){
        System.err.println("\n\nSERVIÇO CAIU - Falha no product " + product.getName() + "\n\n");
        return ResponseEntity.ok("Fail: Retry");
    }   

}
