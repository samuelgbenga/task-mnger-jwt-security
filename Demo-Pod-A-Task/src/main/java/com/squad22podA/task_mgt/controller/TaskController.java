package com.squad22podA.task_mgt.controller;


import com.squad22podA.task_mgt.payload.request.TaskRequest;
import com.squad22podA.task_mgt.payload.response.TaskResponseDto;
import com.squad22podA.task_mgt.payload.response.TaskResponseInfo;
import com.squad22podA.task_mgt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/new-task")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequest taskRequest, @RequestParam String email) {

        return ResponseEntity.ok(taskService.createTask(email, taskRequest));

    }

    // edit the task
    @PutMapping("/edit-task")
    public ResponseEntity<TaskResponseDto> editTask(@Valid @RequestBody TaskRequest taskRequest, @RequestParam String email, @RequestParam Long id) {

        return ResponseEntity.ok(taskService.editTask(email, id, taskRequest));

    }
}
