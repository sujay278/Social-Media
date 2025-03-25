package com.socialMedia.Controller;

import com.socialMedia.Entity.Post;
import com.socialMedia.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/post")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post with postId : " + id + " deleted successfully !");
    }

}
