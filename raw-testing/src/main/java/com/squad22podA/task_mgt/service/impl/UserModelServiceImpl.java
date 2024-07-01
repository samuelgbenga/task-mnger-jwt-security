package com.squad22podA.task_mgt.service.impl;

import com.squad22podA.task_mgt.entity.model.UserModel;
import com.squad22podA.task_mgt.exception.EmailAlreadyExistException;
import com.squad22podA.task_mgt.payload.request.UserRegistrationRequest;
import com.squad22podA.task_mgt.repository.UserModelRepository;
import com.squad22podA.task_mgt.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserModelServiceImpl implements UserModelService {


    private final UserModelRepository userModelRepository;


    @Override
    public void registerUser(UserRegistrationRequest registrationRequest) {

        if(!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())){
            throw new IllegalArgumentException("Passwords do not match!");
        }

        Optional<UserModel> existingUser = userModelRepository.findByEmail(registrationRequest.getEmail());

        if(existingUser.isPresent()){
            throw new EmailAlreadyExistException("Email already exists. Login to your account");
        }


        UserModel newUser = UserModel.builder().firstName(registrationRequest.getFirstName())
                                        .lastName(registrationRequest.getLastName())
                                        .email(registrationRequest.getEmail())
                                        .phoneNumber(registrationRequest.getPhoneNumber())
                                        .password(registrationRequest.getPassword()).build();

        UserModel savedUser = userModelRepository.save(newUser);


        userModelRepository.save(savedUser);


    }
}
