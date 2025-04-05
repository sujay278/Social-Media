package com.socialMedia.Service;


import com.socialMedia.DTO.LoginRequest;
import com.socialMedia.DTO.UserDTO;
import com.socialMedia.Entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    UserDTO getUser(int userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(User user);

    void deleteUser(int userId);

    String registerUser(User user);

    String login(LoginRequest request);

    String followUser(int userId);

    String unfollowUser(int userId);

    UserDTO getUserByUsername(String username);

    List<Object> getFollowers();

    List<Object> getFollowings();
}
