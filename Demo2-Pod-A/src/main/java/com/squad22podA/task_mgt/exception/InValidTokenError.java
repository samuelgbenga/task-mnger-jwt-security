package com.squad22podA.task_mgt.exception;

public class InValidTokenError extends RuntimeException{
    public InValidTokenError(String message) {
        super(message);
    }
}
