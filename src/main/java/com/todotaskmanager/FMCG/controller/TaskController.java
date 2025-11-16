package com.todotaskmanager.FMCG.controller;

import com.todotaskmanager.FMCG.model.Task;
import com.todotaskmanager.FMCG.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // **1. View/Read Task (Read All)**
    // GET /api/tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // **2. View/Read Task (Read One)**
    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // **3. Add/Create Task (Create)**
    // POST /api/tasks
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // **4. Edit/Update Task & Mark as Complete (Update)**
    // PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskRepository.update(id, taskDetails);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // **5. Delete Task (Delete)**
    // DELETE /api/tasks/{id}
    //ram
    //hello
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskComplete(@PathVariable Long id, @RequestParam boolean status) {
        // 1. Call the new repository method to update only the 'completed' field
        Task updatedTask = taskRepository.markComplete(id, status);

        // 2. Check the result and return the appropriate HTTP status
        if (updatedTask != null) {
            // Return 200 OK with the partially updated task data
            return ResponseEntity.ok(updatedTask);
        } else {
            // Return 404 Not Found if the ID was invalid
            return ResponseEntity.notFound().build();
        }
    }
}