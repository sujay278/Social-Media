package com.socialMedia.Service;


import com.socialMedia.Entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUser(int userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(int userId);
}
