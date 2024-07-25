package com.kylearning.todoapp.repositories;

import com.kylearning.todoapp.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
