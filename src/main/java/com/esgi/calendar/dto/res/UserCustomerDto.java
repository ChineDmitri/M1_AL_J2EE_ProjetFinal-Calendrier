package com.esgi.calendar.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCustomerDto {

    private Long id;

    private String firstName;

    private String lastName;

    private int totalPoints;

}
