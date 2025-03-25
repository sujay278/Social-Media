package com.socialMedia.Service.Impl;

import com.socialMedia.DTO.CommentDTO;
import com.socialMedia.Entity.Comment;
import com.socialMedia.Exception.ResourceNotFoundException;
import com.socialMedia.Repository.CommentsRepository;
import com.socialMedia.Repository.PostRepository;
import com.socialMedia.Service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentsRepository.save(comment);
    }

    @Override
    public CommentDTO getComment(int commentId) {
        return new CommentDTO(commentsRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("No comment found with commentId: " + commentId)));
    }

    @Override
    public List<CommentDTO> getAllComment() {
        return commentsRepository.findAll().stream()
                .map(CommentDTO::new) // Directly using the constructor
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO updateComment(Comment comment) {
        Comment existingComment = commentsRepository.findById(comment.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("No comment found with commentId: " + comment.getCommentId()));

        existingComment.setComment(comment.getComment());
        existingComment.setDate(comment.getDate());
        Comment updatedComment = commentsRepository.save(existingComment);

        return new CommentDTO(updatedComment); // Using constructor
    }

    @Override
    public void deleteComment(int commentId) {
        getComment(commentId);
        commentsRepository.deleteById(commentId);
    }
}
