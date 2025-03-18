package com.socialMedia.DTO;

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
}
