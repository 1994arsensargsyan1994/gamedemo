package com.example.gamedemo.dto;

import com.example.gamedemo.entity.Gender;
import com.example.gamedemo.entity.Role;
import com.example.gamedemo.validator.annotaitons.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserDto {


    private Long id;


    @JsonView(value = Views.Username.class)
    @NotNull(message = "Username cannot be missing or empty.")
    @NotBlank(message = "UserName cannot be missing or empty.")
    @Size(min = 2, message = "UserName must not be less then 2 characters.")
    private String username;


    @NotNull(message = "email must not be less then 2 characters.")
    @Email
    private String email;


    @NotNull(message = "password cannot be missing or empty.")
    @NotBlank(message = "password cannot be missing or empty.")
    @ValidPassword
    private String password;


    private boolean active;

    @JsonView(value = Views.Username.class)
    @NotNull(message = "password cannot be missing or empty.")
    private Gender gender;

    @NotNull(message = "password cannot be missing or empty.")
    private Role role;

    private LocalDateTime lastVisit;
}
