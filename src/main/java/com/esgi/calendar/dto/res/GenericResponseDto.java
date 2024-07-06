package com.esgi.calendar.dto.res;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponseDto {
    private HttpStatus status;
    private String     message;
}
