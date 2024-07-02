package com.esgi.calendar.business;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "gif_of_day_id", referencedColumnName = "id")
    private GifOfDay gifOfDay;

    @Column(name = "cost_gif")
    private int costGif;
}
