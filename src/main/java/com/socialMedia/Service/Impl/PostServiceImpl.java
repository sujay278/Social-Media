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
    public PostDTO getPost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with postId : " + postId));
        return new PostDTO(
                post.getPostId(),
                post.getDate(),
                post.getCaption(),
                post.getUser().getUserId()
        );
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts to show");
        }
        return posts.stream().map(post -> new PostDTO(
                post.getPostId(),
                post.getDate(),
                post.getCaption(),
                post.getUser().getUserId()
        )).collect(Collectors.toList());
    }

    @Override
    public PostDTO updatePost(Post post) {

        Post existingPost = postRepository.findById(post.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("No post found with postId : " + post.getPostId()));

        existingPost.setDate(post.getDate());
        existingPost.setCaption(post.getCaption());

        Post updatedPost = postRepository.save(existingPost);
        return new PostDTO(
                updatedPost.getPostId(),
                updatedPost.getDate(),
                updatedPost.getCaption(),
                updatedPost.getUser().getUserId()
        );
    }

    @Override
    public void deletePost(int postId) {
        getPost(postId);
        postRepository.deleteById(postId);
    }
}
