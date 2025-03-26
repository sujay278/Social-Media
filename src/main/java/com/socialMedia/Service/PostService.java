package com.socialMedia.Service;

import com.socialMedia.DTO.PostDTO;
import com.socialMedia.Entity.Post;

import java.util.List;
import java.util.Map;

public interface PostService {

    Post createPost(Post post);

    Post getPost(int postId);

    List<PostDTO> getAllPosts();

    Post updatePost(Post post);

    void deletePost(int postId);

    List<Map<String, Object>> getPostsByUserId(int userId);

    List<Map<String, Object>> getPostsByUsername(String username);

}
