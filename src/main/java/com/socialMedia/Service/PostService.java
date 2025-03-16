package com.socialMedia.Service;

import com.socialMedia.DTO.PostDTO;
import com.socialMedia.Entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post);
    PostDTO getPost(int postId);
    List<PostDTO> getAllPosts();
    PostDTO updatePost(Post post);
    void deletePost(int postId);
}
