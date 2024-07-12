package com.esgi.calendar.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GifOfDayDto {

    @JsonIgnore
    private Long id;

    private String url;

    private String legende;


    @JsonIgnore
    private String userOwnerFirstName;

    @JsonIgnore
    private String userOwnerLastName;

    private List<ReactionUserDto> reactions;

}
