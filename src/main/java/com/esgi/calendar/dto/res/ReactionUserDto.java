package com.esgi.calendar.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReactionUserDto {

    private Long id;

    private String emojiUnicode;

    @JsonIgnore
    private String userLastName;

    @JsonIgnore
    private String userFirstName;

}
