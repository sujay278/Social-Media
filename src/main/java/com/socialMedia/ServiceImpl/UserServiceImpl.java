package com.socialMedia.ServiceImpl;

import com.socialMedia.Entity.User;
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
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = getUser(user.getId());
        existingUser.setUser_name(user.getUser_name());
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
