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

import static com.example.todolog.error.errorcode.ErrorCode.CATEGORY_NOT_FOUND;
import static com.example.todolog.error.errorcode.ErrorCode.CATEGORY_STEP_OVER;

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
        int stepCategory = getCategoryList(parentId).getStepCategory();
        if (stepCategory == 3){
            throw new CustomException(CATEGORY_STEP_OVER);
        }
        Category category = new Category(parentId, name);
        categoryRepository.save(category);

        return new CreateCategoryResponseDto(category.getId(), category.getParentId(), category.getName());
    }

    public CategoryResponseDto getCategoryList (Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()){
            throw new CustomException(CATEGORY_NOT_FOUND);
        }
        Category category = optionalCategory.get();

        Deque<String> deque = getStringDeque(category);
        //dequeSize 로 categoryStep(단계)확인
        int categoryStep = deque.size();

        //deque 에 넣어두었던 parent categoryName pollLast()
        String firstCategory = deque.pollLast();
        String secondCategory = deque.pollLast();
        String thirdCategory = deque.pollLast();

        return new CategoryResponseDto(firstCategory , secondCategory , thirdCategory , categoryStep);
    }


    //parent categoryName deque 에 add
    private Deque<String> getStringDeque(Category category) {
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
        return deque;
    }
}
