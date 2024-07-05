package com.esgi.calendar.business;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emoji_id", nullable = false)
    private Emoji emoji;

    @ManyToOne
    @JoinColumn(name = "user_customer_id", nullable = false)
    private UserCustomer userCustomer;

    @ManyToOne
    @JoinColumn(name = "gif_of_day_id")
    private GifOfDay gifOfDay;


    // Add a constructor with emoji and userCustomer
    /*public Reaction(Emoji emoji, UserCustomer userCustomer) {
        this.emoji = emoji;
        this.userCustomer = userCustomer;
    }*/
}
