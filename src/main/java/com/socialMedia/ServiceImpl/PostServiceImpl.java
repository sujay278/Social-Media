package com.socialMedia.ServiceImpl;

import com.socialMedia.DTO.PostDTO;
import com.socialMedia.Entity.Post;
import com.socialMedia.Exception.ResourceNotFoundException;
import com.socialMedia.Repository.PostRepository;
import com.socialMedia.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
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
                post.getUser().getId()
        );
    }

    @Override
    public List<PostDTO> getAllPosts() {
        if (postRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No posts to show");
        }
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> new PostDTO(
                post.getPostId(),
                post.getDate(),
                post.getCaption(),
                post.getUser().getId()
        )).collect(Collectors.toList());
    }

    //TODO Post is not getting updated
    @Override
    public PostDTO updatePost(Post post) {
        PostDTO existingPost = getPost(post.getPostId());
        existingPost.setDate(post.getDate());
        existingPost.setCaption(post.getCaption());
        return new PostDTO(
                existingPost.getPostId(),
                existingPost.getDate(),
                existingPost.getCaption(),
                existingPost.getUserId()
        );
    }

    @Override
    public void deletePost(int postId) {
        getPost(postId);
        postRepository.deleteById(postId);
    }
}
