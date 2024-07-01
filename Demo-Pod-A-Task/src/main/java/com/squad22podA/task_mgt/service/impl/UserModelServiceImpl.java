package com.squad22podA.task_mgt.service.impl;

import com.squad22podA.task_mgt.entity.model.ConfirmationToken;
import com.squad22podA.task_mgt.entity.model.UserModel;
import com.squad22podA.task_mgt.exception.EmailAlreadyExistException;
import com.squad22podA.task_mgt.payload.request.EmailDetails;
import com.squad22podA.task_mgt.payload.request.UserRegistrationRequest;
import com.squad22podA.task_mgt.repository.ConfirmationTokenRepository;
import com.squad22podA.task_mgt.repository.UserModelRepository;
import com.squad22podA.task_mgt.service.EmailService;
import com.squad22podA.task_mgt.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserModelServiceImpl implements UserModelService {


    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;


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
                                        .password(passwordEncoder.encode(registrationRequest.getPassword())).build();

        UserModel savedUser = userModelRepository.save(newUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenRepository.save(confirmationToken);

        String confirmationUrl = "http://localhost:8080/api/auth/confirm?token=" + confirmationToken.getToken();

        //send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                                    .recipient(savedUser.getEmail())
                                    .subject("ACCOUNT CREATION")
                                    .messageBody("CONGRATULATIONS!!! Your User Account Has Been Successfully Created.\n"
                                    + "Your Account Details: \n" + "Account FullName: " + savedUser.getFirstName() + " \n"
                                     + "Confirm your email " +
                                            "Please click the link to confirm your registration: " + confirmationUrl)
                                    .build();

        emailService.sendEmailAlert(emailDetails);

    }
}
