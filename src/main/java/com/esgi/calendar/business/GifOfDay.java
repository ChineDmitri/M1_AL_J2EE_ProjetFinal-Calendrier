package com.esgi.calendar.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserCustomer userOwner;

    @OneToOne(mappedBy = "gifOfDay")
    private DayOfActualMonth dayOfActualMonth;

    @OneToMany(mappedBy = "gifOfDay", cascade = CascadeType.ALL)
    private List<Reaction> reactions = new ArrayList<>();
}