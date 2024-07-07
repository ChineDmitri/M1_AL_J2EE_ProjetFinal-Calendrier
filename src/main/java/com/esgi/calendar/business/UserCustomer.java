package com.esgi.calendar.business;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCustomer {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(
            min = 2,
            max = 255,
            message = "Le prénom doit être compris entre 1 et 255 caractères."
    )
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(
            min = 2,
            max = 255,
            message = "Le nom doit être compris entre 1 et 255 caractères."
    )
    private String lastName;

    private int totalPoints = 500;

    @NotNull
    @NotEmpty
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@esgi\\.fr$")
    @Column(unique = true)
    @Size(
            min = 5,
            max = 255,
            message = "L'email doit être compris entre 2 et 255 caractères."
    )
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

    public void removePoints(int pointsToRemove) {
        if (this.totalPoints - pointsToRemove < 0) {
            throw new IllegalArgumentException(
                    "Vous n'avez pas assez de points pour effectuer cette action.");
        }
        this.totalPoints -= pointsToRemove;
    }

}
