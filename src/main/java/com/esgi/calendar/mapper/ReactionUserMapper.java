package com.esgi.calendar.mapper;

import com.esgi.calendar.business.Reaction;
import com.esgi.calendar.dto.res.ReactionUserDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReactionUserMapper {
    Reaction toEntity(ReactionUserDto reactionUserDto);

    @Mappings(
            {
                    @Mapping(target = "id", source = "emoji.id"),
                    @Mapping(target = "emojiUnicode", source = "emoji.unicode"),
                    @Mapping(target = "userLastName", source = "userCustomer.lastName"),
                    @Mapping(target = "userFirstName", source = "userCustomer.firstName")
            }
    )
    ReactionUserDto toDto(Reaction reaction);
}