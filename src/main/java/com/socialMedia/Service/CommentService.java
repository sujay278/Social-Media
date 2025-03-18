package com.socialMedia.Service;

import com.socialMedia.DTO.CommentDTO;
import com.socialMedia.Entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment);

    CommentDTO getComment(int commentId);

    List<CommentDTO> getAllComment();

    CommentDTO updateComment(Comment comment);

    void deleteComment(int commentId);

}
