package com.product.model.dto;

import com.product.model.Product;

public class ProductDTO {
    public String name;
    public Double price;
    public Long quantity;

    public ProductDTO(){
        
    }
    public ProductDTO(Product product){
        setName(product.getName());
        setPrice(product.getPrice());
        setQuantity(product.getQuantity());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Long getQuantity() {
        return quantity;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
}
