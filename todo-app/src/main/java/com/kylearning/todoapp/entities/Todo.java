package com.kylearning.todoapp.entities;

import com.kylearning.todoapp.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String date;

    private TodoStatus status;

    private String description;
}
