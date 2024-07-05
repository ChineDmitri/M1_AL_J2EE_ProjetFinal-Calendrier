package com.esgi.calendar.service;

import com.esgi.calendar.business.Emoji;
import com.esgi.calendar.dto.res.EmojiDto;
import com.esgi.calendar.repository.EmojiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IEmojiService {

    public List<EmojiDto> getAllEmoji();

}
