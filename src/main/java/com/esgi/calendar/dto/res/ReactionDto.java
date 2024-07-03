package com.esgi.calendar.dto.res;

import com.esgi.calendar.business.UserCustomer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReactionDto {

        private Long id;

        private String userLastName;

        private String userFirstName;

        private String emojiUnicode;
}
