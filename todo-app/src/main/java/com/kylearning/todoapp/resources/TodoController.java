package com.kylearning.todoapp.resources;

import com.kylearning.todoapp.entities.Todo;
import com.kylearning.todoapp.services.TodoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todo")
@AllArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<Todo>> getTodoList() {
        return new ResponseEntity<>(todoService.getTodoList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) throws Exception {
        return new ResponseEntity<>(todoService.updateTodo(todo), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) throws Exception {
        todoService.deleteTodod(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
