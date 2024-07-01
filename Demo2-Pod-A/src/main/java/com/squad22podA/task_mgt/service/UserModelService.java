package com.squad22podA.task_mgt.service;

import com.squad22podA.task_mgt.payload.request.LoginRequestDto;
import com.squad22podA.task_mgt.payload.request.LoginResponse;
import com.squad22podA.task_mgt.payload.request.UserRegistrationRequest;

public interface UserModelService {

    void registerUser(UserRegistrationRequest registrationRequest);

    LoginResponse loginUser(LoginRequestDto loginRequestDto);


}
