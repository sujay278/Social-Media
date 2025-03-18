package com.socialMedia.ServiceImpl;

import com.socialMedia.Entity.User;
import com.socialMedia.Exception.ResourceNotFoundException;
import com.socialMedia.Repository.UserRepository;
import com.socialMedia.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with userId : " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No users to display");
        }
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = getUser(user.getUserId());
        existingUser.setName(user.getName());
        existingUser.setUser_name(user.getUser_name());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int userId) {
        getUser(userId);
        userRepository.deleteById(userId);
    }
}
