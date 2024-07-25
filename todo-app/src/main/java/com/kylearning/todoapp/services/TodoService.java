package com.kylearning.todoapp.services;

import com.kylearning.todoapp.entities.Todo;

import java.util.List;

public interface TodoService {
    public List<Todo> getTodoList();

    Todo createTodo(Todo todo);

    Todo updateTodo(Todo todo) throws Exception;

    Todo getTodoById(Long id) throws Exception;

    void deleteTodod(Long id) throws Exception;
}
