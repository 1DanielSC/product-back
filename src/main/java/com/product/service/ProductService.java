package com.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ProductRepository productRepository;

    private ProductResilience productResilience;

    @Autowired
    public ProductService(ProductResilience productResilience){
        this.productResilience = productResilience;
    }

    public Product save(Product product){
        //ProductDTO productDto = new ProductDTO(product);

        /*  SPRING FUNCTION */
        // ResponseEntity<String> responseFromFunction = productResilience.checkProduct(productDto);
        // String body = responseFromFunction.getBody();

        // if(responseFromFunction.getStatusCode() == HttpStatus.OK && !body.equals("[\"OK\"]"))
        //     return null;
        
        Product p = findByName(product.getName());
        if(p==null)
            return productRepository.save(product);

        p.setQuantity(p.getQuantity()+product.getQuantity());
        return productRepository.save(p);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findByName(String name){
        Optional<Product> product = productRepository.findByName(name);
        return product.isPresent() ? product.get() : null;
    }

    public Product findById(Long id){
        Optional<Product> product = productRepository.findById(id);
        return product.isPresent() ? product.get() : null;
    }

    public Product update(Product product){
        Optional<Product> prod = productRepository.findById(product.getId());
        return prod.isPresent() ? productRepository.save(product) : null;
    }

    public Product deleteById(Long id){
        Optional<Product> prod = productRepository.findById(id);

        if(prod.isPresent()){
            productRepository.deleteById(id);
            return prod.get();
        }
        return  null;
    }

    public Product deleteByName(String name){
        Optional<Product> prod = productRepository.findByName(name);

        if(prod.isPresent()){
            productRepository.deleteById(prod.get().getId());
            return prod.get();
        }
        return  null;
    }


    public Product requestProduct(Product entity){
        Product product = findByName(entity.getName());
        if(product == null)
            throw new NotFoundException("Product not found");

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

            Product product = findByName(item.getName());
            if(product!=null){
                product.setQuantity(item.getQuantity()+product.getQuantity());
                productRepository.save(product);
            }
            
        }

        return products;
    }
}
