package com.socialMedia.DTO;

import com.socialMedia.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int commentId;
    private OffsetDateTime timestamp;
    private String comment;
    private int postId;

    // Constructor to map Entity to DTO
    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.timestamp = comment.getTimestamp();
        this.comment = comment.getComment();
        this.postId = comment.getPost().getPostId();
    }
}
