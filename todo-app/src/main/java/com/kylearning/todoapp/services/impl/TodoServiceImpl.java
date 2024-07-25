package com.kylearning.todoapp.services.impl;

import com.kylearning.todoapp.entities.Todo;
import com.kylearning.todoapp.repositories.TodoRepository;
import com.kylearning.todoapp.services.TodoService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    @Override
    public List<Todo> getTodoList() {
        return todoRepository.findAll();
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Todo todo) throws Exception {
        Todo existingTodo = getTodoById(todo.getId());
        existingTodo.setTitle(existingTodo.getTitle() == todo.getTitle() ? existingTodo.getTitle(): todo.getTitle());
        existingTodo.setDate(existingTodo.getDate() == todo.getDate() ? existingTodo.getDate(): todo.getDate());
        existingTodo.setStatus(existingTodo.getStatus() == todo.getStatus() ? existingTodo.getStatus(): todo.getStatus());
        existingTodo.setDescription(existingTodo.getDescription() == todo.getDescription() ? existingTodo.getDescription(): todo.getDescription());
        return todoRepository.save(existingTodo);
    }

    @Override
    public Todo getTodoById(Long id) throws Exception {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if(optionalTodo.isEmpty()){
            throw new Exception("No Todo with Id: " + id);
        }

       return optionalTodo.get();
    }

    @Override
    public void deleteTodod(Long id) throws Exception {
        Todo todo = getTodoById(id);
        if(todo != null) {
            todoRepository.delete(todo);
        }
    }
}
