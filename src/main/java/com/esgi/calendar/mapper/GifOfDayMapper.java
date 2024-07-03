package com.esgi.calendar.mapper;

import com.esgi.calendar.business.GifOfDay;
import com.esgi.calendar.dto.res.GifOfDayDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReactionMapper.class)
public interface GifOfDayMapper {

    @Mapping(target = "userOwnerFirstName", source = "userOwner.firstName")
    @Mapping(target = "userOwnerLastName", source = "userOwner.lastName")
    GifOfDayDto toDto(GifOfDay gifOfDay);

    @Mapping(target = "userOwner", ignore = true)
    @Mapping(target = "dayOfActualMonth", ignore = true)
    GifOfDay toEntity(GifOfDayDto gifOfDayDto);
}
