package com.example.todolog.controller;

import com.example.todolog.dto.category.CategoryResponseDto;
import com.example.todolog.dto.category.CreateCategoryRequestDto;
import com.example.todolog.dto.category.CreateCategoryResponseDto;
import com.example.todolog.dto.comment.CommentResponseDto;
import com.example.todolog.entity.User;
import com.example.todolog.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping()
    public ResponseEntity<?> createCategory(
            @RequestParam(required = false) Long parentId,
            @Validated @RequestBody CreateCategoryRequestDto requestDto,
            BindingResult bindingResult) {

        ResponseEntity<?> errorMap = getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;

        CreateCategoryResponseDto createCategoryResponseDto;
        if (parentId == null){
            createCategoryResponseDto = categoryService.createCategory(requestDto.getName());
        }else {
            createCategoryResponseDto = categoryService.createSubCategory(parentId,requestDto.getName());
        }

        return new ResponseEntity<>(createCategoryResponseDto , HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long categoryId){
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryList(categoryId);
        return new ResponseEntity<>(categoryResponseDto , HttpStatus.OK);
    }










    static ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }


}
