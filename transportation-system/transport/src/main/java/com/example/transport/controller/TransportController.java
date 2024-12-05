package com.example.transport.controller;

import com.example.transport.model.Task;
import com.example.transport.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    // Create a new transport task
    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        if (transportService.createTask(task)) {
            return ResponseEntity.status(201).body("Task created successfully.");
        } else {
            return ResponseEntity.status(400).body("Failed to create task. No suitable car available.");
        }
    }

    // Get all transport tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return transportService.getAllTasks();
    }

    // Get a specific task by ID
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Task task = transportService.getTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    // Mark a task as completed
    @PostMapping("/tasks/{id}/complete")
    public ResponseEntity<String> completeTask(@PathVariable String id) {
        if (transportService.completeTask(id)) {
            return ResponseEntity.ok("Task marked as completed.");
        } else {
            return ResponseEntity.status(400).body("Failed to complete task.");
        }
    }
}
