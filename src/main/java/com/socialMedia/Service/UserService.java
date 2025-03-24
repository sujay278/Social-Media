package com.socialMedia.Service;


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

    String login(String email, String password);

    String followUser(int userId);

    String unfollowUser(int userId);
}
