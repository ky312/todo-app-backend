package com.kylearning.todoapp.services;

import com.kylearning.todoapp.entities.Todo;
import com.kylearning.todoapp.entities.User;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    User createUser(User todo);

    User updateUser(User todo) throws Exception;

    User getUserById(Long id) throws Exception;

    void deleteUserById(Long id) throws Exception;

}
