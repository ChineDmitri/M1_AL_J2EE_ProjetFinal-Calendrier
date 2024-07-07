package com.esgi.calendar.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private UserCustomer userOwner;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gifOfDay")
    private DayOfActualMonth dayOfActualMonth;

    @OneToMany(mappedBy = "gifOfDay", cascade = CascadeType.ALL)
    private List<Reaction> reactions = new ArrayList<>();

    public void addOrReplaceIfExistReaction(Reaction reaction) {
        if (reaction != null) {
            Reaction existingReaction = this.reactions.stream()
                                                      .filter(r -> r.getUserCustomer()
                                                                    .getId()
                                                                    .equals(reaction.getUserCustomer()
                                                                                    .getId()))
                                                      .findFirst()
                                                      .orElse(null);

            if (existingReaction != null) {
                existingReaction.setEmoji(reaction.getEmoji());
            } else {
                this.reactions.add(reaction);
            }
        }
    }

}