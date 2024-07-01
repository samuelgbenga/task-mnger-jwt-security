package com.squad22podA.task_mgt.repository;

import com.squad22podA.task_mgt.entity.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
