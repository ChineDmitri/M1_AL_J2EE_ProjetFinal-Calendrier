package com.esgi.calendar.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GifOfDayDto {

    private Long id;

    private String url;

    private String userOwnerFirstName;

    private String userOwnerLastName;

    private Set<ReactionDto> reactions;

}
