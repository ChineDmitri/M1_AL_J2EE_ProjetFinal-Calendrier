package com.esgi.calendar.mapper;

import com.esgi.calendar.business.Emoji;
import com.esgi.calendar.business.Reaction;
import com.esgi.calendar.dto.res.EmojiDto;
import com.esgi.calendar.dto.res.ReactionUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EmojiMapper {
    Emoji toEntity(EmojiDto emojiDto);

    EmojiDto toDto(Emoji reaction);
}
