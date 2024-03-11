package com.yanfaisn.restapi.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanfaisn.restapi.dto.ProductRequest;
import com.yanfaisn.restapi.dto.ProductResponse;
import com.yanfaisn.restapi.dto.ResponseData;
import com.yanfaisn.restapi.entity.Product;
import com.yanfaisn.restapi.service.ProductService;
import com.yanfaisn.restapi.utility.ErrorParsingUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user/products")
    public ResponseEntity<ResponseData<List<ProductResponse>>> findAll() {
        ResponseData<List<ProductResponse>> response = new ResponseData<>();
        List<Product> products = productService.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());

        response.setStatus(true);
        response.getMessage().add("Berhasil menampilkan produk");
        response.setPayload(productResponses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/product/{id}")
    public ResponseEntity<ResponseData<?>> findById(@PathVariable("id") String id) {
        ResponseData<ProductResponse> response = new ResponseData<>();
        Product product = productService.findById(id);
        response.setStatus(true);
        response.getMessage().add("berhasil menampilkan satu produk");
        response.setPayload(modelMapper.map(product, ProductResponse.class));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/product")
    public ResponseEntity<ResponseData<?>> create(@Valid @RequestBody ProductRequest productRequest, Errors errors) {

        ResponseData<ProductResponse> response = new ResponseData<>();
        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Product product = modelMapper.map(productRequest, Product.class);
            product.setId(UUID.randomUUID().toString());
            product = productService.create(product);
            response.setStatus(true);
            response.getMessage().add("Product tersimpan");
            response.setPayload(modelMapper.map(product, ProductResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add((ex.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PutMapping("/user/product")
    public ResponseEntity<ResponseData<?>> edit(@Valid @RequestBody ProductRequest productRequest, Errors errors) {
        ResponseData<ProductResponse> response = new ResponseData<>();
        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Product product = modelMapper.map(productRequest, Product.class);
            product.setId(UUID.randomUUID().toString());
            productService.edit(product);
            response.setStatus(true);
            response.getMessage().add("berhasil mengedit produk");
            response.setPayload(modelMapper.map(product, ProductResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/user/product/{id}")
    public void deleteById(@PathVariable("id") String id) {
        productService.deleteById(id);
    }

}
