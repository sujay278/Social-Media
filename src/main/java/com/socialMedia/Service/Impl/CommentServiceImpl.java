package com.socialMedia.Service.Impl;

import com.socialMedia.DTO.CommentDTO;
import com.socialMedia.Entity.Comment;
import com.socialMedia.Entity.Post;
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
        Post post = postRepository.findById(comment.getPost().getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with userId : " + comment.getPost().getPostId()));
        comment.setPost(post);
        return commentsRepository.save(comment);
    }

    @Override
    public CommentDTO getComment(int commentId) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with postId : " + commentId));
        return new CommentDTO(
                comment.getCommentId(),
                comment.getDate(),
                comment.getComment(),
                comment.getPost().getPostId()
        );
    }

    @Override
    public List<CommentDTO> getAllComment() {
        List<Comment> comments = commentsRepository.findAll();
        if (comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments to show");
        }
        return comments.stream().map(comment -> new CommentDTO(
                comment.getCommentId(),
                comment.getDate(),
                comment.getComment(),
                comment.getPost().getPostId()
        )).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO updateComment(Comment comment) {

        Comment existingComment = commentsRepository.findById(comment.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("No post found with postId : " + comment.getCommentId()));

        existingComment.setComment(comment.getComment());
        existingComment.setDate(comment.getDate());
        Comment updatedComment = commentsRepository.save(existingComment);

        return new CommentDTO(
                updatedComment.getCommentId(),
                updatedComment.getDate(),
                updatedComment.getComment(),
                updatedComment.getPost().getPostId()
        );
    }

    @Override
    public void deleteComment(int commentId) {
        getComment(commentId);
        commentsRepository.deleteById(commentId);
    }
}
