package com.product.service.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.product.model.dto.ProductDTO;

@FeignClient("FUNCTION")
public interface FunctionServiceInterface {
    @RequestMapping(method = RequestMethod.POST, value = "/checkProduct")
    ResponseEntity<String> checkProduct(@RequestBody ProductDTO product);
}
