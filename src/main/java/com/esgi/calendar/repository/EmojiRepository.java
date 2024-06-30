package com.esgi.calendar.repository;

import com.esgi.calendar.business.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
}
