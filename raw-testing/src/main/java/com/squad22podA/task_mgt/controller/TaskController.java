package com.squad22podA.task_mgt.controller;


import com.squad22podA.task_mgt.exception.TaskNotFoundOrWrongUserException;
import com.squad22podA.task_mgt.payload.request.TaskRequest;
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
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest taskRequest, @RequestParam String email) {

        try {
            return ResponseEntity.ok(taskService.createTask(email, taskRequest));
        } catch (Exception e) {
            throw new TaskNotFoundOrWrongUserException("Something went Wrong: " + e.getMessage());
        }

    }

    // edit the task
    @PutMapping("/edit-task/{id}")
    public ResponseEntity<?> editTask(@Valid @RequestBody TaskRequest taskRequest, @RequestParam String email, @PathVariable Long id) {

        try {
            return ResponseEntity.ok(taskService.editTask(email, id, taskRequest));
        } catch (Exception e) {
            throw new TaskNotFoundOrWrongUserException("Something went Wrong: " + e.getMessage());
        }

    }

    // get task by id
    @GetMapping("/get-task/{id}")
    public ResponseEntity<?> getTask( @RequestParam String email, @PathVariable Long id) {

        try {
            return ResponseEntity.ok(taskService.getTask(email, id));
        } catch (Exception e) {
            throw new TaskNotFoundOrWrongUserException("Something went Wrong: " + e.getMessage());
        }


    }

    // get task by status
    @GetMapping("/completed-task")
    public ResponseEntity<?> getCompletedTask(@RequestParam String email) {
        try {
            return ResponseEntity.ok(taskService.getTaskByStatus(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot Get Completed task for " + e.getMessage() + " user");
        }
    }

    // get all task for a single user
    @GetMapping("/get-all-task")
    public ResponseEntity<?> getAllTask(@RequestParam String email) {
        try {
            return ResponseEntity.ok(taskService.getAllTask(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot Get all Task for " + e.getMessage() + " user");
        }
    }
}
// settled exceptions
// for null user: user that does not exist and want to create task
// for wrong date format
// for invalid argument supplied as input e.g null value.
// settle for editing task
