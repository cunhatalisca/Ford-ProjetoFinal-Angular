package com.ford.dealership.dto;

import com.ford.dealership.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representacao publica do usuario (sem senha) devolvida nas respostas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private Boolean checkboxTermos;
    private Boolean checkboxNewsLetter;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .checkboxTermos(user.getCheckboxTermos())
                .checkboxNewsLetter(user.getCheckboxNewsLetter())
                .build();
    }
}
