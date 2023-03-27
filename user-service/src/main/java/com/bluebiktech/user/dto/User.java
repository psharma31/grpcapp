package com.bluebiktech.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {



    @NotBlank(message = "userName is mandatory")
    private String userName;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotNull(message = "age is mandatory.")
    private int age;
    @NotBlank(message = "phone is mandatory")
    private String phone;

    public User(String userName, String firstName, String lastName, String email, String phone,int age) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }


}
