package com.esgi.calendar.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationFormDto {
    @NotBlank(message = "Le prénom d'utilisateur est obligatoire")
    @NotEmpty(message = "Le prénom d'utilisateur ne peut pas etre vide")
    @NotNull(message = "Le prénom d'utilisateur ne peut etre null")
    private String firstName;
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas etre vide")
    @NotNull(message = "Le nom d'utilisateur ne peut etre null")
    private String lastName;
    @NotBlank(message = "L'email est obligatoire")
    @NotBlank(message = "L'email d'utilisateur est obligatoire")
    @NotEmpty(message = "L'email d'utilisateur ne peut pas etre vide")
    @NotNull(message = "L'email d'utilisateur ne peut etre null")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @NotEmpty(message = "Le mot de passe d'utilisateur ne peut pas etre vide")
    @NotNull(message = "Le mot de passe d'utilisateur ne peut etre null")
    private String password;

    private Long   theme;
}
