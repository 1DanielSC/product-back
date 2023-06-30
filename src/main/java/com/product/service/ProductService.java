package com.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.exception.NotFoundException;
import com.product.model.Product;
import com.product.model.dto.ProductDTO;
import com.product.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repository;

    private ProductResilience productResilience;

    @Autowired
    public ProductService(ProductResilience productResilience){
        this.productResilience = productResilience;
    }

    @Caching(
        put = {
            @CachePut(value = "product", key = "#entity.name")
        },
        evict = {
            @CacheEvict(value = "products", allEntries = true)
        }
    )
    public Product save(Product entity){
        //ProductDTO productDto = new ProductDTO(product);

        /*  SPRING FUNCTION */
        // ResponseEntity<String> responseFromFunction = productResilience.checkProduct(productDto);
        // String body = responseFromFunction.getBody();

        // if(responseFromFunction.getStatusCode() == HttpStatus.OK && !body.equals("[\"OK\"]"))
        //     return null;
        try {
            Product product = findByName(entity.getName());
            product.setQuantity(product.getQuantity()+entity.getQuantity());
            return repository.save(product);
            
        } catch (Exception e) {
            // TODO: handle exception
        }

        return repository.save(entity);
        
    }


    @Cacheable("products")
    public List<Product> findAll(){
        System.out.println("FindAll: indo no banco...");
        return repository.findAll();
    }

    @Cacheable(value = "product", key = "#entity.name")
    private Product cacheByProductName(Product entity){
        return entity;
    }

    @Cacheable(value = "product", key = "#name")
    public Product findByName(String name){
        System.out.println("FindByName: indo no banco...");
        return repository.findByName(name)
        .orElseThrow(() -> new NotFoundException("Product with name " + name + " was not found."));
    }

    @Cacheable(value = "product", key = "#id")
    public Product findById(Long id){
        System.out.println("FindById: indo no banco...");
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product with this id was not found."));
    }

    public Product update(Product entity){
        findByName(entity.getName());
        return updateCache(entity);
    }
    
    @CacheEvict(value = "products", allEntries = true)
    @Caching(
        put = {
            @CachePut(value = "product", key = "#entity.id"),
            @CachePut(value = "product", key = "#entity.name")
        }
    )
    private Product updateCache(Product entity){
        return repository.save(entity);
    }

    @Caching(
        evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true)
        }
    )
    public Product deleteById(Long id){
        Product product = findById(id);
        repository.deleteById(id);
        return product;
    }

    @Caching(
        evict = {
            @CacheEvict(value = "product", key = "#name"),
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true)
        }
    )
    public Product deleteByName(String name){
        Product product = findByName(name);
        return deleteById(product.getId());
    }


    public Product requestProduct(Product entity){
        Product product = findByName(entity.getName());

        if(product.getQuantity() >= entity.getQuantity()){
            long quantityLeft = product.getQuantity() - entity.getQuantity();
            product.setQuantity(quantityLeft);
            update(product);
            entity.setPrice(product.getPrice());
            return entity;
        }

        return null;
    }

    public List<Product> increaseQuantity(List<Product> products){
        if(products.size() == 0)
            return null;

        for (Product item : products) {

            try {
                
                Product product = findByName(item.getName());
                if(product!=null){
                    product.setQuantity(item.getQuantity()+product.getQuantity());
                    //repository.save(product);
                    update(product);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }

        return products;
    }

}
