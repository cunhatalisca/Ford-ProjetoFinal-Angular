package com.ford.dealership.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String phone;

    @NotBlank
    private String password;

    /** Apenas validado no front; ignorado na persistencia. */
    private String repeatPassword;

    private Boolean checkboxTermos;

    private Boolean checkboxNewsLetter;
}
