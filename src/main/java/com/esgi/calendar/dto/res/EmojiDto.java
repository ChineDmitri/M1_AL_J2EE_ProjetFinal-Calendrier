package com.esgi.calendar.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapping;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmojiDto {

    private Long id;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String unicode;

}
