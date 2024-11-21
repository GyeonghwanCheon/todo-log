package com.example.todolog.repository;

import com.example.todolog.entity.Comment;
import com.example.todolog.entity.Like;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


    Optional<Like> findByFeed_IdAndUser_Id(Long feedId , Long userId);
    Optional<Like> findByComment_IdAndUser_Id(Long commentId , Long userId);

    default Like findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->
                        new CustomException(ErrorCode.LIKE_NOT_FOUND));
    }

    default Like findByFeed_IdAndUser_IdOrElseThrow(Long feedId , Long userId){
        return findByFeed_IdAndUser_Id(feedId,userId)
                .orElseThrow(()->
                        new CustomException(ErrorCode.FEED_NOT_FOUND));
    }

    default Like findByComment_IdAndUser_IdOrElseThrow(Long commentId , Long userId){
        return findByComment_IdAndUser_Id(commentId,userId)
                .orElseThrow(()->
                        new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }


    int countByFeed_IdAndLikeStatus(Long feedId, Boolean likeStatus);

    int countByComment_IdAndLikeStatus(Long commentId, Boolean likeStatus);
}
