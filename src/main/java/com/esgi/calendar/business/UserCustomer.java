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
    Long id;

    String firstName;

    String lastName;

    @Column(unique = true)
    String email;

    String password;

    @OneToOne()
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

}
