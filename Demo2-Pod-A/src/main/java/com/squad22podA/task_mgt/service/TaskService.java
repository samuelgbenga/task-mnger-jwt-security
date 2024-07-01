package com.squad22podA.task_mgt.service;

import com.squad22podA.task_mgt.payload.request.TaskRequest;
import com.squad22podA.task_mgt.payload.response.TaskResponseDto;

public interface TaskService {

    TaskResponseDto createTask(String email, TaskRequest taskRequest);
    TaskResponseDto editTask(String email, Long taskId, TaskRequest taskRequest);

}
