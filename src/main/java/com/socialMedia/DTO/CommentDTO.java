package com.socialMedia.DTO;

import com.socialMedia.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int commentId;
    private Date date;
    private String comment;
    private int postId;

    // Constructor to map Entity to DTO
    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.date = comment.getDate();
        this.comment = comment.getComment();
        this.postId = comment.getPost().getPostId();
    }
}
