package com.esgi.calendar.repository;

import com.esgi.calendar.business.GifOfDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifOfDayRepository extends JpaRepository<GifOfDay, Long> {
}