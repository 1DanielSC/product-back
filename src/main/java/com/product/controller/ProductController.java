package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.model.Product;
import com.product.service.ProductService;

@Controller
@RequestMapping(value = "product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Product>> findAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(value = "id") Long id){
        Product product = productService.findById(id);
        
        if(product != null)
            return ResponseEntity.ok(product);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/findByName/{name}")
    public ResponseEntity<Product> findProductByName(@PathVariable(value = "name") String name){
        Product product = productService.findByName(name.toLowerCase());
        
        if(product != null)
            return ResponseEntity.ok(product);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Product> save(@RequestBody Product product){
        Product productSaved = productService.save(product);

        if(productSaved != null)
            return ResponseEntity.ok().body(product);
        else
            return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Product> update(@RequestBody Product product){
        Product productSaved = productService.update(product);

        if(productSaved != null)
            return ResponseEntity.ok().body(product);
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable(value = "id") Long id){
        Product productSaved = productService.deleteById(id);

        if(productSaved != null)
            return ResponseEntity.ok().body(productSaved);
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/deleteByName/{name}")
    public ResponseEntity<Product> deleteByName(@PathVariable(value = "name") String name){
        Product productSaved = productService.deleteByName(name.toLowerCase());

        if(productSaved != null)
            return ResponseEntity.ok().body(productSaved);
        else
            return ResponseEntity.badRequest().build();
    }
}
