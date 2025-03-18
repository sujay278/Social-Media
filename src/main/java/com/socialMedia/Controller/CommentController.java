package com.socialMedia.Controller;

import com.socialMedia.DTO.CommentDTO;
import com.socialMedia.Entity.Comment;
import com.socialMedia.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.createComment(comment));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComment() {
        return ResponseEntity.ok(commentService.getAllComment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PutMapping("/comment")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment with commentId : " + id + " deleted successfully !");
    }
}
