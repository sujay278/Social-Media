package com.socialMedia.Service.Impl;

import com.socialMedia.DTO.PostDTO;
import com.socialMedia.Entity.Post;
import com.socialMedia.Entity.User;
import com.socialMedia.Exception.ResourceNotFoundException;
import com.socialMedia.Repository.PostRepository;
import com.socialMedia.Repository.UserRepository;
import com.socialMedia.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post) {
        User user = userRepository.findById(post.getUser().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with userId : " + post.getUser().getUserId()));
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post getPost(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with postId : " + postId));
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Post updatePost(Post post) {

        Post existingPost = postRepository.findById(post.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("No post found with postId : " + post.getPostId()));

        existingPost.setDate(post.getDate());
        existingPost.setCaption(post.getCaption());

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(int postId) {
        getPost(postId);
        postRepository.deleteById(postId);
    }
}
