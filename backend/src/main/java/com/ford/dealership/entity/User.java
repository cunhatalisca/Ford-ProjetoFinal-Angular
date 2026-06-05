package com.ford.dealership.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    /** Hash BCrypt da senha. Nunca serializado nas respostas. */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    /** "admin" ou "usuario" (mantido como String para casar com o contrato do front). */
    @Column(nullable = false)
    private String role;

    private Boolean checkboxTermos;

    private Boolean checkboxNewsLetter;
}
