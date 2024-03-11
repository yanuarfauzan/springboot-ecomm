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

import com.yanfaisn.restapi.dto.CategoryRequest;
import com.yanfaisn.restapi.dto.CategoryResponse;
import com.yanfaisn.restapi.dto.ResponseData;
import com.yanfaisn.restapi.entity.Category;
import com.yanfaisn.restapi.service.CategoryService;
import com.yanfaisn.restapi.utility.ErrorParsingUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user/categories")
    public ResponseEntity<ResponseData<List<CategoryResponse>>> findAll() {
        ResponseData<List<CategoryResponse>> response = new ResponseData<>();
        List<Category> categories = categoryService.findAll();
        if (categories != null) {
            List<CategoryResponse> categoryResponses = categories.stream()
                    .map(category -> modelMapper.map(category, CategoryResponse.class)).collect(Collectors.toList());
            response.setStatus(true);
            response.getMessage().add("berhasil menampilkan kategori");
            response.setPayload(categoryResponses);
            return ResponseEntity.ok(response);
        } else {
            response.setStatus(false);
            response.getMessage().add("gagal menampilkan kategori");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/user/category/{id}")
    public ResponseEntity<ResponseData<?>> findById(@PathVariable("id") String id) {
        ResponseData<CategoryResponse> response = new ResponseData<>();
        Category category = categoryService.findById(id);
        response.setStatus(true);
        response.getMessage().add("berhasil menampilkan satu kategori");
        response.setPayload(modelMapper.map(category, CategoryResponse.class));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/category")
    public ResponseEntity<ResponseData<?>> create(@Valid @RequestBody CategoryRequest categoryRequest, Errors errors) {
        ResponseData<CategoryResponse> response = new ResponseData<>();
        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Category category = modelMapper.map(categoryRequest, Category.class);
            category.setId(UUID.randomUUID().toString());
            categoryService.create(category);
            response.setStatus(true);
            response.getMessage().add("berhasil menambahkan kategori");
            response.setPayload(modelMapper.map(category, CategoryResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/user/category")
    public ResponseEntity<ResponseData<?>> edit(@Valid @RequestBody CategoryRequest categoryRequest,
            Errors errors) {
        ResponseData<CategoryResponse> response = new ResponseData<>();

        if (errors.hasErrors()) {
            response.setStatus(false);
            response.setMessage(ErrorParsingUtility.parse(errors));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            Category category = modelMapper.map(categoryRequest, Category.class);
            categoryService.edit(category);
            response.setStatus(true);
            response.getMessage().add("berhasil mengedit kategori");
            response.setPayload(modelMapper.map(category, CategoryResponse.class));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(false);
            response.getMessage().add(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/user/category/{id}")
    public void deleteById(@PathVariable("id") String id) {
        categoryService.deleteById(id);
    }

}
