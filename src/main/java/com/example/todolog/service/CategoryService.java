package com.example.todolog.service;


import com.example.todolog.dto.category.CategoryResponseDto;
import com.example.todolog.dto.category.CreateCategoryResponseDto;
import com.example.todolog.entity.Category;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import com.example.todolog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CreateCategoryResponseDto createCategory(String name){
        Category category = new Category(name);
        categoryRepository.save(category);

        return new CreateCategoryResponseDto(category.getId(),category.getParentId(),category.getName());
    }

    public CreateCategoryResponseDto createSubCategory(Long parentId , String name){
        Category findCategory = categoryRepository.findByIdOrElseThrow(parentId);
        Category category = new Category(parentId, name);
        categoryRepository.save(category);

        return new CreateCategoryResponseDto(category.getId(), category.getParentId(), category.getName());
    }

    public CategoryResponseDto getCategoryList (Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.get();
        Deque<String> deque = new LinkedList<>();
        deque.add(category.getName());
        if (category.getParentId() != null){
            Optional<Category> optionalCategory2 = categoryRepository.findById(category.getParentId());
            Category category2 = optionalCategory2.get();
            deque.add(category2.getName());
            if (category2.getParentId() != null){
                Optional<Category> optionalCategory3 = categoryRepository.findById(category2.getParentId());
                Category category3 = optionalCategory3.get();
                deque.add(category3.getName());
            }
        }
        String firstCategory = deque.pollLast();
        String secondCategory = deque.pollLast();
        String thirdCategory = deque.pollLast();

        return new CategoryResponseDto(firstCategory,secondCategory,thirdCategory);

    }
}
