package com.socialMedia.Service.Impl;

import com.socialMedia.Config.Securiy.JwtUtil;
import com.socialMedia.DTO.LoginRequest;
import com.socialMedia.DTO.UserDTO;
import com.socialMedia.Entity.User;
import com.socialMedia.Exception.ResourceNotFoundException;
import com.socialMedia.Repository.UserRepository;
import com.socialMedia.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // TODO Temporary hardcoded user ID (Replace with actual JWT extraction later)
    private static final int LOGGED_IN_USER_ID = 1;

    @Override
    public UserDTO getUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users to display");
        }
        return users.stream()
                .map(UserDTO::new) // Convert User to UserDTO
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int userId) {
        getUser(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User with this email already registered!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    @Transactional
    public UserDTO updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setName(user.getName());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        User updatedUser = userRepository.save(existingUser);

        return new UserDTO(updatedUser);
    }

    /*@Override
    public String followUser(int userId) {
        User loggedInUser = userRepository.findById(LOGGED_IN_USER_ID)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        User userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (loggedInUser.equals(userToFollow)) {
            return "You cannot follow yourself.";
        }

        if (loggedInUser.getFollowing().contains(userToFollow)) {
            return "Already following this user.";
        }

        loggedInUser.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(loggedInUser);

        userRepository.save(loggedInUser);
        userRepository.save(userToFollow);

        return "Successfully followed user: " + userToFollow.getUsername();
    }*/
    @Override
    public String followUser(int userId) {
        String email = getLoggedInUserEmail();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        User userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (loggedInUser.equals(userToFollow)) {
            return "You cannot follow yourself.";
        }

        if (loggedInUser.getFollowing().contains(userToFollow)) {
            return "Already following this user.";
        }

        loggedInUser.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(loggedInUser);

        userRepository.save(loggedInUser);
        userRepository.save(userToFollow);

        return "Successfully followed user: " + userToFollow.getUsername();
    }

    @Override
    public String unfollowUser(int userId) {
        String email = getLoggedInUserEmail();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        User userToUnfollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        if (loggedInUser.equals(userToUnfollow)) {
            return "You cannot unfollow yourself.";
        }

        if (!loggedInUser.getFollowing().contains(userToUnfollow)) {
            return "You are not following this user.";
        }

        loggedInUser.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(loggedInUser);

        userRepository.save(loggedInUser);
        userRepository.save(userToUnfollow);

        return "Successfully unfollowed user: " + userToUnfollow.getUsername();
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return new UserDTO(user);
    }

    // Extract logged-in user's email from Spring Security context
    private String getLoggedInUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }

}
