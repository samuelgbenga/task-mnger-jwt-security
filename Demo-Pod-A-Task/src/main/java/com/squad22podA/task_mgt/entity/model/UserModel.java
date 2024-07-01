package com.squad22podA.task_mgt.entity.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel extends BaseClass{

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private  String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword; // this field won't be persisted in the database,  it's used transiently for validation purposes only.

    private String phoneNumber;

    private boolean enabled = false;

    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
    private List<Task> taskList = new ArrayList<>();

}
