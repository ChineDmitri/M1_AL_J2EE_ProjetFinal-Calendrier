package com.esgi.calendar.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationFormDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long   theme;
}
