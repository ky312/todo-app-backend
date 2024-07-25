package com.kylearning.todoapp.services.impl;

import com.kylearning.todoapp.entities.User;
import com.kylearning.todoapp.exceptions.UserNotFoundException;
import com.kylearning.todoapp.repositories.UserRepository;
import com.kylearning.todoapp.services.UserService;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserNotFoundException("Username already exists!!");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws Exception {
        return null;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("No user available with id: " + id);
        }

        return optionalUser.get();
    }

    @Override
    public void deleteUserById(Long id) throws Exception {

    }
}
