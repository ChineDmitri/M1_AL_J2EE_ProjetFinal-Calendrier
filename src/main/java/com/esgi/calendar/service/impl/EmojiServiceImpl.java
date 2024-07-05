package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.Emoji;
import com.esgi.calendar.dto.res.EmojiDto;
import com.esgi.calendar.mapper.EmojiMapper;
import com.esgi.calendar.repository.EmojiRepository;
import com.esgi.calendar.service.IEmojiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmojiServiceImpl implements IEmojiService {

    private final EmojiRepository emojiRepository;
    private final EmojiMapper     emojiMapper;

    @Override
    public List<EmojiDto> getAllEmoji() {
        return Optional.ofNullable(this.emojiRepository)
                       .map(repo -> repo.findAll())
                       .map(list -> list.stream()
                                        .map(emojiMapper::toDto)
                                        .toList())
                       .orElse(Collections.emptyList());
    }
}
