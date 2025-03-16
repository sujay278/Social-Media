package com.socialMedia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int postId;
    private Date date;
    private String caption;
    private int userId;
}
