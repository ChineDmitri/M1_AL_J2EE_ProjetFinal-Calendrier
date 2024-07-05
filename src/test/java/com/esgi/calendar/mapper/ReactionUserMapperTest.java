package com.esgi.calendar.mapper;

import com.esgi.calendar.business.Emoji;
import com.esgi.calendar.business.Reaction;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.ReactionUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class ReactionUserMapperTest {

    private Reaction           reaction;
    private ReactionUserDto    reactionUserDto;
    private ReactionUserMapper reactionUserMapper;

    @BeforeEach
    public void setUp() {
        reactionUserMapper = Mappers.getMapper(ReactionUserMapper.class);

        reaction = new Reaction();
        reaction.setId(1L);
        reaction.setEmoji(new Emoji(1L, "NAME", "UNICODE"));
        UserCustomer user = new UserCustomer(
                1L,
                "John",
                "Doe",
                500,
                "jd@test.com",
                "password",
                null
        );
        reaction.setUserCustomer(user);

    }

    @Test
    public void testEntity_ToDto() {
        // Given
        this.reactionUserDto = reactionUserMapper.toDto(this.reaction);

        System.out.println(this.reactionUserDto);

        // Then
        assertEquals(this.reaction.getId(), reactionUserDto.getId());
        assertEquals(this.reaction.getUserCustomer()
                                  .getFirstName(), reactionUserDto.getUserFirstName());
        assertEquals(this.reaction.getUserCustomer()
                                  .getLastName(), reactionUserDto.getUserLastName());
        assertEquals(this.reaction.getEmoji()
                                  .getUnicode(), reactionUserDto.getEmojiUnicode());
    }

}
