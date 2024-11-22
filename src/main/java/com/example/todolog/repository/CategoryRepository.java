package com.example.todolog.repository;

import com.example.todolog.entity.Category;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category findByIdOrElseThrow(Long categoryId){
        return findById(categoryId)
                .orElseThrow(()->
                        new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
