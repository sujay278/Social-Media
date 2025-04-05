package com.socialMedia.Controller;

import com.socialMedia.DTO.UserDTO;
import com.socialMedia.Entity.User;
import com.socialMedia.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with userId : " + id + " deleted successfully !");
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<String> followUser(@PathVariable int userId) {
        String response = userService.followUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<String> unfollowUser(@PathVariable int userId) {
        String response = userService.unfollowUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/followers")
    public ResponseEntity<List<Object>> getFollowers() {
        return ResponseEntity.ok(userService.getFollowers());
    }

    @GetMapping("/followings")
    public ResponseEntity<List<Object>> getFollowings() {
        return ResponseEntity.ok(userService.getFollowings());
    }


}