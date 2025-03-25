package com.socialMedia.Service;

import com.socialMedia.DTO.PostDTO;
import com.socialMedia.Entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post);

    Post getPost(int postId);

    List<PostDTO> getAllPosts();

    Post updatePost(Post post);

    void deletePost(int postId);
}
