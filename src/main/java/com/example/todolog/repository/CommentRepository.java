package com.example.todolog.repository;

import com.example.todolog.entity.Comment;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->
                        new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
