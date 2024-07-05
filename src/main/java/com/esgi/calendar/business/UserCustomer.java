package com.esgi.calendar.business;

import jakarta.persistence.*;
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

    private String firstName;

    private String lastName;

    private int totalPoints;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne()
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

}
