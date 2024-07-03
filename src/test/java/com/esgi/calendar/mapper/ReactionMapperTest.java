package com.esgi.calendar.mapper;

import com.esgi.calendar.business.Emoji;
import com.esgi.calendar.business.Reaction;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.res.ReactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class ReactionMapperTest {

    //    @Mock
    private Reaction reaction;

    //    @Mock
    private ReactionDto reactionDto;

    //    @InjectMocks
    private ReactionMapper reactionMapper;

    @BeforeEach
    public void setUp() {
        reactionMapper = Mappers.getMapper(ReactionMapper.class);

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
        //        when(reaction.getId()).thenReturn(1L);
        //        when(reaction.getEmoji().getUnicode()).thenReturn("UNICODE");
        //        when(reaction.getUserCustomer().getFirstName()).thenReturn("First Name");
        //        when(reaction.getUserCustomer().getLastName()).thenReturn("Last Name");
        //
        //        when(reactionDto.getId()).thenReturn(1L);
        //        when(reactionDto.getContent()).thenReturn("Test Reaction");
        //        when(reactionDto.getUserId()).thenReturn(100L);

    }

    @Test
    public void testEntity_ToDto() {
        // Given
        this.reactionDto = reactionMapper.toDto(this.reaction);

        System.out.println(this.reactionDto);

        // Then
        assertEquals(this.reaction.getId(), reactionDto.getId());
        assertEquals(this.reaction.getUserCustomer()
                                  .getFirstName(), reactionDto.getUserFirstName());
        assertEquals(this.reaction.getUserCustomer()
                                  .getLastName(), reactionDto.getUserLastName());
        assertEquals(this.reaction.getEmoji()
                                  .getUnicode(), reactionDto.getEmojiUnicode());
    }

}
