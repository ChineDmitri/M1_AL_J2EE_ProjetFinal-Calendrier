package com.esgi.calendar.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DayOfActualMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    // In H2, you need to use the java.sql.Date type instead of LocalDate => Date.valueOf() method.
    @Column(name = "date", nullable = false)
    private Date date;
}
