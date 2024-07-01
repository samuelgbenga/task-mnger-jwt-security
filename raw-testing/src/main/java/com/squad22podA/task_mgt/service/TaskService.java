package com.squad22podA.task_mgt.service;

import com.squad22podA.task_mgt.entity.enums.Status;
import com.squad22podA.task_mgt.payload.request.TaskRequest;
import com.squad22podA.task_mgt.payload.response.TaskResponseDto;

import java.util.List;

public interface TaskService {

    TaskResponseDto createTask(String email, TaskRequest taskRequest);
    TaskResponseDto editTask(String email, Long taskId, TaskRequest taskRequest);
    TaskResponseDto getTask(String email, Long taskId);
    List<TaskResponseDto> getTaskByStatus(String email);
    List<TaskResponseDto> getAllTask(String email);

}
