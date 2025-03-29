package com.socialMedia.Utils;

import com.socialMedia.Entity.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonUtils {

    public static List<Map<String, Object>> getPostsOfUser(User user) {

        return user.getPosts().stream().map(post -> {
            Map<String, Object> postMap = new LinkedHashMap<>();
            postMap.put("postId", post.getPostId());
            postMap.put("caption", post.getCaption());
            postMap.put("post date", post.getDate());

            postMap.put("comments", post.getComments().stream().map(comment -> {
                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("commentId", comment.getCommentId());
                commentMap.put("comment", comment.getComment());
                commentMap.put("comment date",comment.getDate());
                return commentMap;
            }).collect(Collectors.toList()));

            return postMap;
        }).collect(Collectors.toList());
    }
}
