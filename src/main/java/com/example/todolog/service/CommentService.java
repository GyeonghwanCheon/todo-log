package com.example.todolog.service;

import com.example.todolog.dto.comment.CommentResponseDto;
import com.example.todolog.entity.Comment;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.error.exception.CustomException;
import com.example.todolog.repository.CommentRepository;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.todolog.error.errorcode.ErrorCode.UNAUTHORIZED_USER;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;


    public CommentResponseDto save(Long feedId , Long userId , String detail){

        User findUser = userRepository.findByIdOrElseThrow(userId);

        Feed findFeed = feedRepository.findByOrElseThrow(feedId);

        Comment comment = new Comment(detail);
        comment.setUser(findUser);
        comment.setFeed(findFeed);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId() , comment.getFeed().getId() , comment.getUser().getNickname() , comment.getDetail() );

    }

    public CommentResponseDto findById(Long commentId){
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        return new CommentResponseDto(comment.getId() , comment.getFeed().getId() ,comment.getUser().getNickname() , comment.getDetail());
    }

    @Transactional
    public void updateComment (Long commentId , Long loginUserId , String detail){

        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);
        Long commentUserid = findComment.getUser().getUserId();
        //요청유저가 작성자인지 판별
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);
        if (!Objects.equals(commentUserid, findUser.getUserId())){
            throw new CustomException(UNAUTHORIZED_USER);
        }
        findComment.updateDetail(detail);
    }


    public void delete(Long commentId, Long loginUserId){

        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);
        Long commentUserid=findComment.getUser().getUserId();
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);
        //요청유저가 작성자인지 판별
        if (!Objects.equals(commentUserid, findUser.getUserId())){
            throw new CustomException(UNAUTHORIZED_USER);
        }
        commentRepository.delete(findComment);
    }







}
