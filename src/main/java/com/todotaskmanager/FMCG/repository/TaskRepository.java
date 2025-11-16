package com.todotaskmanager.FMCG.repository;

import com.todotaskmanager.FMCG.model.Task;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {

    // Simulates the temporary in-memory database
    private static final List<Task> taskList = new ArrayList<>();
    // Used to generate unique IDs for new tasks
    private static final AtomicLong counter = new AtomicLong();

    // Initial dummy data for testing
    static {
        taskList.add(new Task(counter.incrementAndGet(), "Setup Project", "Configure Spring Boot and dependencies.", false));
        taskList.add(new Task(counter.incrementAndGet(), "Implement CRUD", "Create all REST endpoints.", false));
        taskList.add(new Task(counter.incrementAndGet(), "Write Documentation", "Document the API endpoints.", true));
    }

    // C - Create Task (Add)
    public Task save(Task task) {
        task.setId(counter.incrementAndGet()); // Assign a new unique ID
        taskList.add(task);
        return task;
    }

    // R - Read All Tasks (View/Read)
    public List<Task> findAll() {
        return taskList;
    }

    // R - Read Single Task (View/Read)
    public Optional<Task> findById(Long id) {
        return taskList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    // U - Update Task (Edit/Update & Mark as Complete)
    public Task update(Long id, Task updatedTask) {
        Optional<Task> existingTaskOptional = findById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted()); // Covers "Mark as Complete"
            return existingTask;
        }
        return null; // Task not found
    }

    // D - Delete Task (Delete)
    public boolean deleteById(Long id) {
        return taskList.removeIf(t -> t.getId().equals(id));
    }
}
