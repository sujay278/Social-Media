package com.socialMedia.Utils;

import com.socialMedia.Entity.User;
import com.socialMedia.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommonUtils {

    @Autowired
    private UserRepository userRepository;

    public static List<Map<String, Object>> getPostsOfUser(User user) {

        return user.getPosts().stream().map(post -> {
            Map<String, Object> postMap = new LinkedHashMap<>();
            postMap.put("postId", post.getPostId());
            postMap.put("caption", post.getCaption());
            postMap.put("post date", post.getTimestamp());

            postMap.put("comments", post.getComments().stream().map(comment -> {
                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("commentId", comment.getCommentId());
                commentMap.put("comment", comment.getComment());
                commentMap.put("comment date", comment.getTimestamp());
                return commentMap;
            }).collect(Collectors.toList()));

            return postMap;
        }).collect(Collectors.toList());
    }

    // Extract logged-in user from Spring Security context
    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return userRepository.findByEmail(((UserDetails) principal).getUsername())
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }
}
