package com.socialMedia.DTO;

import com.socialMedia.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private int userId;
    private String name;
    private String username;
    private String email;
    private Map<Integer, String> following;
    private Map<Integer, String> followers;
    private List<PostDTO> posts;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();

        this.following = user.getFollowing().stream()
                .collect(Collectors.toMap(User::getUserId, User::getUsername));

        this.followers = user.getFollowers().stream()
                .collect(Collectors.toMap(User::getUserId, User::getUsername));

        this.posts = user.getPosts().stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());


    }
}
