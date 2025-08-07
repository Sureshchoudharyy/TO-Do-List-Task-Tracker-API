package com.example.ToDoListAPI.Controller;

import com.example.ToDoListAPI.Entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private Map<Long, Task> taskDB = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        return ResponseEntity.ok(new ArrayList<>(taskDB.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task task = taskDB.get(id);
        if(task==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        taskDB.put(task.getId(), task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id){
        Task task = taskDB.remove(id);
        if(task==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Task existingTask = taskDB.get(id);
        if(existingTask==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        taskDB.put(id,task);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> partialUpdate(@PathVariable Long id, @RequestBody Map<String,String> request){
        Task existingTask = taskDB.get(id);
        if(existingTask==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        String newStatus = request.get("status");
        existingTask.setStatus(newStatus);
        taskDB.put(id,existingTask);
        return ResponseEntity.ok(existingTask);
    }
}
