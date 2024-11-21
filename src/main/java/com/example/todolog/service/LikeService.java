package com.example.todolog.service;

import com.example.todolog.dto.like.LikeCommentResponseDto;
import com.example.todolog.dto.like.LikeFeedResponseDto;
import com.example.todolog.entity.Comment;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.Like;
import com.example.todolog.entity.User;
import com.example.todolog.repository.CommentRepository;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.LikeRepository;
import com.example.todolog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public LikeFeedResponseDto likeFeed (Long feedId , Long userId){

        Feed findFeed = feedRepository.findByOrElseThrow(feedId);
        User fineUser = userRepository.findByIdOrElseThrow(userId);
        //feedId,userId 에 대한 like data
        Optional<Like> optionalLike = likeRepository.findByFeed_IdAndUser_Id(feedId,userId);

        //처음 좋아요 기능 구현시 like data 생성
        if (optionalLike.isEmpty()){
            Like like = new Like();
            like.setFeed(findFeed);
            like.setUser(fineUser);
            like.updateLikeStatus(false);
            likeRepository.save(like);
        }
        //이후 좋아요 기능 구현시 like data 수정
        Like findlike = likeRepository.findByFeed_IdAndUser_IdOrElseThrow(feedId,userId);
        findlike.updateLikeStatus(!findlike.getLikeStatus());

        Like like = likeRepository.findByFeed_IdAndUser_IdOrElseThrow(feedId,userId);

        return new LikeFeedResponseDto(like.getId() , feedId , userId , like.getLikeStatus());
    }

    @Transactional
    public LikeCommentResponseDto likeComment (Long commentId , Long userId){
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);
        User fineUser = userRepository.findByIdOrElseThrow(userId);

        Optional<Like> optionalLike = likeRepository.findByComment_IdAndUser_Id(commentId,userId);

        if (optionalLike.isEmpty()){
            Like like = new Like();
            like.setComment(findComment);
            like.setUser(fineUser);
            like.updateLikeStatus(false);
            likeRepository.save(like);
        }

       Like findlike = likeRepository.findByComment_IdAndUser_IdOrElseThrow(commentId,userId);
       findlike.updateLikeStatus(!findlike.getLikeStatus());

       Like like = likeRepository.findByComment_IdAndUser_IdOrElseThrow(commentId,userId);

        return new LikeCommentResponseDto(like.getId() , commentId , userId , like.getLikeStatus());
    }

    public int likeCountByFeedId(Long feedId){
        return likeRepository.countByFeed_IdAndLikeStatus(feedId,true);
    }

    public int likeCountByCommentId(Long commentId){
        return likeRepository.countByComment_IdAndLikeStatus(commentId,true);
    }
}
