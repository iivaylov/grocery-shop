package com.groceryshop.groceryshop.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    @NotBlank(message = "First name cannot be blank.")
    @Size(min = 3, message = "First name should be at least 3 symbols.")
    private String firstname;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(min = 3, message = "Last name should be at least 3 symbols.")
    private String lastname;

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 5, message = "Username should be at least 5 symbols.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, message = "Password should be at least 8 symbols.")
    private String password;

    @NotBlank(message = "Confirm password cannot be blank.")
    @Size(min = 8, message = "Confirm password should be at least 8 symbols.")
    private String confirmPassword;
}
