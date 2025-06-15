package com.socialMedia.DTO;

import com.socialMedia.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int postId;
    private int userId;  // Added this field to indicate the post owner
    private OffsetDateTime timestamp;
    private String caption;
    private int likeCount;
    private List<Map<String, Object>> likedBy;
    private List<CommentDTO> comments;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.userId = post.getUser().getUserId(); // Fetch the post owner's userId
        this.timestamp = post.getTimestamp();
        this.caption = post.getCaption();
        this.likeCount = post.getLikedBy().size();
        this.likedBy = post.getLikedBy().stream()
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", user.getUserId());
                    map.put("username", user.getUsername());
                    return map;
                })
                .collect(Collectors.toList());
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}

