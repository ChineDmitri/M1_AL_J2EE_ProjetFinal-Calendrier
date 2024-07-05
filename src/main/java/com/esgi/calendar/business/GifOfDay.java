package com.esgi.calendar.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class GifOfDay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "legende")
    private String legende;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserCustomer user;

    @OneToOne(mappedBy = "gifOfDay")
    private DayOfActualMonth dayOfActualMonth;

    @OneToMany(mappedBy = "gifOfDay", cascade = CascadeType.ALL)
    private Set<Reaction> reactions = new HashSet<>();
}